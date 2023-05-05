package com.example.concalendar.post.service;

import com.example.concalendar.board.service.BoardService;
import com.example.concalendar.board.dto.BoardDto;
import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.board.dto.BoardReturnDto;
import com.example.concalendar.post.dto.PostSearchReturnDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Post service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardService boardService;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Create.
     *
     * @param postFormDto the post form dto
     * @param userEmail   the user email
     */
// PostFormDto와 유저 이메일을 매개변수로 받아서 포스트 테이블에 데이터를 저장하는 메서드
//    @Transactional
//    public void create(PostFormDto postFormDto, String userEmail) {
//        Post post = Post.builder()
//                .postTitle(postFormDto.getPostTitle())
//                .postContent(postFormDto.getPostContent())
//                .createdDate(LocalDateTime.now())
//                .writer(userRepository.findByUserEmail(userEmail).orElseThrow())
//                .board(boardService.findBoardById(postFormDto.getBoardId()))
//                .build();
//        postRepository.save(post);
//    }

    @Transactional
    public void create(PostFormDto postFormDto, String userEmail) {
        Post post = Post.builder()
                .postTitle(postFormDto.getPostTitle())
                .postContent(postFormDto.getPostContent())
                .createdDate(LocalDateTime.now())
                .writer(userRepository.findByUserEmail(userEmail).orElseThrow())
                .board(boardService.findBoardById(postFormDto.getBoardId()))
                .build();
        postRepository.save(post);
    }

    /**
     * Find post by post id post.
     *
     * @param num the num
     * @return the post
     */
// 포스트를 포스트 id를 가지고 찾는 서비스 메서드
    public Post findPostByPostId(long num){
        Post post = postRepository.findByIdFetchJoin(num);

        return post;
    }

    /**
     * Update post.
     *
     * @param postId      the post id
     * @param postFormDto the post form dto
     * @return the post
     */
// PostFromDto와 유저 이메일을 매개변수로 받아서 포스트 테이블 내 데이터를 수정하는 메서드
    @Transactional
    public Post update(long postId, PostFormDto postFormDto){
        Post post = findPostByPostId(postId);

        post.setPostTitle(postFormDto.getPostTitle());
        post.setPostContent(postFormDto.getPostContent());

        return post;

    }

    /**
     * Get post by page board return dto.
     *
     * @param pageRequest the page request
     * @param id          the id
     * @return the board return dto
     */
    public BoardReturnDto getPostByPage(Pageable pageRequest, long id){
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();

        Page<Post> postPageList = postRepository.findAllWithBoardId(pageRequest,id);
        List<Post> postList = new ArrayList<>();
        List<BoardDto> boardDtoList = new ArrayList<>();

        postList = postPageList.getContent();
        long postSizeByBoardId = postRepository.countPostsByBoardId(id);

        for (Post post : postList){

            Set<String> postHeartSet = redisTemplate.opsForSet().members("postLike:"+post.getId());

            int commentSize = post.getCommentList().size();
            int replySize = 0;
            for (Comment comment : post.getCommentList()){
                replySize += comment.getReplyList().size();
            }

            commentSize = commentSize + replySize;

            BoardDto boardDto = BoardDto.entityToGetPostDto(post, postHeartSet, commentSize);

            boardDtoList.add(boardDto);
        }

        BoardReturnDto boardReturnDto = BoardReturnDto
                .builder()
                .boardDtoList(boardDtoList)
                .postEntireSize(postSizeByBoardId)
                .build();

        return boardReturnDto;

    }

    /**
     * Find all posts by board id list.
     *
     * @param id the id
     * @return the list
     */
    public List<Post> findAllPostsByBoardId(Long id) {
        List<Post> postList = postRepository.findAllPostsByBoardId(id);

        return postList;
    }

    /**
     * Gets post by post id.
     *
     * @param postId the post id
     * @return the post by post id
     */
    public Post getPostByPostId(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.BAD_REQUEST,"postId에 해당하는 Post가 DB에 존재하지 않습니다."));

        return post;
    }

    public void postHeartClick(long postId, String memberEmail){
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        if (setOperations.isMember("postLike:"+postId,memberEmail)) {
            setOperations.remove("postLike:"+postId,memberEmail);
            zSetOperations.add("postRanking",String.valueOf(postId),redisTemplate.opsForSet().members("postLike:"+postId).size());
        }
        else{
            setOperations.add("postLike:"+postId,memberEmail);
            zSetOperations.add("postRanking",String.valueOf(postId),redisTemplate.opsForSet().members("postLike:"+postId).size());
        }
    }

    public List<Post> getPostRanking(){
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<String>> rankingSet = zSetOperations.reverseRangeWithScores("postRanking",-5,-1);

        List<Post> postList = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> str : rankingSet){
            Post post = postRepository.findById(Long.valueOf(str.getValue())).orElseThrow(() -> new CustomException(StatusEnum.BAD_REQUEST,"postId에 해당하는 Post가 DB에 존재하지 않습니다."));
            postList.add(post);
        }

        return postList;
    }

    public BoardReturnDto findSearchPostsBySearchKeyword(String searchKeyword, Pageable pageRequest) {
        Page<Post> postPageList = postRepository.findAllWithSearchKeyword(pageRequest,searchKeyword);
        List<Post> postList = new ArrayList<>();
        List<BoardDto> boardDtoList = new ArrayList<>();

        postList = postPageList.getContent();

        long searchedPostsSize = postList.size();

        for (Post post : postList){

            Set<String> postHeartSet = redisTemplate.opsForSet().members("postLike:"+post.getId());

            int commentSize = post.getCommentList().size();
            int replySize = 0;
            for (Comment comment : post.getCommentList()){
                replySize += comment.getReplyList().size();
            }

            commentSize = commentSize + replySize;

            BoardDto boardDto = BoardDto.entityToGetPostDto(post, postHeartSet, commentSize);

            boardDtoList.add(boardDto);
        }

        BoardReturnDto boardReturnDto = BoardReturnDto
                .builder()
                .boardDtoList(boardDtoList)
                .postEntireSize(searchedPostsSize)
                .build();

        return boardReturnDto;

    }
}
