package com.example.concalendar.post.controller;

import com.example.concalendar.board.dto.BoardReturnDto;
import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.dto.PostSearchReturnDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Post controller.
 */
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    private Message message;

    /**
     * Create post response entity.
     *
     * @param postFormDto   the post form dto
     * @param Authorization the authorization
     * @return the response entity
     */
    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostFormDto postFormDto, @RequestHeader String Authorization){
        message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            postService.create(postFormDto, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage("게시글 달기 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 게시글을 등록할 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    /**
     * Get post response entity.
     *
     * @param boardId the board id
     * @param postId  the post id
     * @return the response entity
     */
    @GetMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity getPost(@PathVariable Long boardId, @PathVariable Long postId){
        Post post = postService.getPostByPostId(postId);
        Set<String> postHeartSet = redisTemplate.opsForSet().members("postLike:"+postId);
//        Set<String> commentHeartSet = redisTemplate.opsForSet().members("commentsLikeL"+)

        PostDto postDto = new PostDto(post, postHeartSet);

        message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("postId="+postId+"에 해당하는 게시물 전송 성공");
        message.setData(postDto);

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    @PostMapping("/boards/{boardId}/postsHeart/{postId}")
    public ResponseEntity postHeartClick(@PathVariable Long boardId, @PathVariable Long postId, @RequestHeader String Authorization){
        message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            postService.postHeartClick(postId, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage("좋아요 클릭 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 클릭할 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @GetMapping("/posts/ranking")
    public ResponseEntity postRanking(){
        message = new Message();
        Set<String> postHeartSet;

        message.setMessage("포스트 랭킹을 가져옵니다.");
        message.setStatus(StatusEnum.OK);
        List<Post> postList = postService.getPostRanking();
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : postList){
            postHeartSet = redisTemplate.opsForSet().members("postLike:"+post.getId());

            PostDto postDto = new PostDto(post,postHeartSet);
            postDtoList.add(postDto);
        }

        message.setData(postDtoList);

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @GetMapping("/posts/search")
    public ResponseEntity postSearch(@RequestParam String searchKeyword,  @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageRequest){

        BoardReturnDto searchBoardReturnDto = postService.findSearchPostsBySearchKeyword(searchKeyword, pageRequest);

        message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("검색한 게시물 전달 성공");
        message.setData(searchBoardReturnDto);

        return new ResponseEntity(message, message.getStatus().getHttpStatus());

    }

    @GetMapping("/posts/{post_id}")
    public void getIdPost(@PathVariable Long post_id){

        Post post = postRepository.findById(post_id).orElseThrow();
    }

    @PostMapping("/posts/{post_id}/uploadFile")
    public ResponseEntity uploadFileInPost(@PathVariable Long post_id, @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String s3FileUrl = postService.postUploadImageFile(multipartFile, post_id);

        message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("파일이 S3에 저장됐습니다.");
        message.setData(s3FileUrl);

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
