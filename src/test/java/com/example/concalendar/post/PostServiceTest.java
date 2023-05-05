package com.example.concalendar.post;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.board.repository.BoardBulkRepository;
import com.example.concalendar.board.repository.BoardRepository;
import com.example.concalendar.board.service.BoardService;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostBulkRepository;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Post service test.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostServiceTest {
    /**
     * The Post service.
     */
//    @Autowired
//    PostService postService;

    /**
     * The Post repository.
     */
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    PostBulkRepository postBulkRepository;

    @Autowired
    BoardBulkRepository boardBulkRepository;

    //        for (int i=1; i<1000; i++) {
//            PostFormDto postFormDto = PostFormDto.builder()
//                    .boardId(1l)
//                    .postTitle("title title title"+i)
//                    .postContent("content content content"+i+1)
//                    .build();

//    @Test
//    @DisplayName("글 등록하기")
//    public void createPostOneTest(){
//
//        long startTime = System.currentTimeMillis();
//
//        for (int i =1; i< 100000; i++){
//            Post post = Post.builder()
//                    .postTitle("title title title"+i)
//                    .postContent("content content content"+i+1)
//                    .build();
//
//            postRepository.save(post);
//        }
//
//        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
//    }
//
//    @Test
//    @DisplayName("글 등록하기")
//    public void createPostTest(){
//
//        long startTime = System.currentTimeMillis();
//
//        List<Post> list = new ArrayList<>();
//
//        for (int i =1; i< 100000; i++){
//            Post post = Post.builder()
//                    .postTitle("title title title"+i)
//                    .postContent("content content content"+i+1)
//                    .build();
//
//            list.add(post);
//        }
//
//        postRepository.saveAll(list);
//        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
//    }
//
    /**
     * Create post test.
     */
    @Order(1)
    @Test
    @DisplayName("게시판 벌크 등록하기")
    public void createBoardBulkTest(){

//        long startTime = System.currentTimeMillis();

        List<Board> boardList = new ArrayList<>();

        for (int i =1; i < 10000; i++){
            Board board = Board.builder()
                    .name("board"+i)
                    .build();

            boardList.add(board);
        }

        boardBulkRepository.saveAll(boardList);
//
//        TimeUnit.SECONDS.sleep(10);



//        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
    }


    /**
     * Create post test.
     */
    @Order(2)
    @Test
    @DisplayName("글 벌크 등록하기")
    public void createPostBulkTest() throws InterruptedException {

//        long startTime = System.currentTimeMillis();

//        List<Board> boardList = new ArrayList<>();
//
//        for (int i =1; i < 10000; i++){
//            Board board = Board.builder()
//                    .name("board"+i)
//                    .build();
//        }
//
//        boardBulkRepository.saveAll(boardList);
//
//        TimeUnit.SECONDS.sleep(10);

        List<Post> list1 = new ArrayList<>();

        for (long i =1; i< 10000; i++){
            Post post = Post.builder()
                    .postTitle("title title title"+i)
                    .postContent("content content content"+i+1)
                    .build();

            list1.add(post);
        }

        postBulkRepository.saveAll(list1);

//        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
    }
//
//    /**
//     * Update post test.
//     */
//    @Test
//    @DisplayName("글 수정하기")
//    public void updatePostTest(){
//        PostFormDto postFormDto = PostFormDto.builder()
//                .postTitle("title22 Changed")
//                .postContent("content22 changed")
//                .build();
//
//        Post post = postService.update(2,postFormDto);
//
//        assertEquals("title22 Changed",post.getPostTitle());
//    }
//
//    /**
//     * Get post test.
//     */
//    @Test
//    @DisplayName("글 찾기")
//    public void getPostTest(){
//        long startTime = System.currentTimeMillis();
//
//        List<Post> postList = postRepository.findPostsByBoardId(1);
//
//        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
//
//    }

    @Order(3)
    @Test
    @Transactional
    @DisplayName("N+1 발생 테스트")
    void nPlusOneTest(){

        long startTime = System.currentTimeMillis();

        System.out.println("-------- 게시물(Post) 전체 조회 요청 ---------");
        List<Post> postList = postRepository.findAll();
        System.out.println("-------- 게시물 전체 조회 완료 [쿼리 1번 발생] ---------");

        System.out.println("-------- 게시물(Post)과 연관된 게시판 조회 요청 [쿼리 발생 X] ---------");
        for (Post post : postList) {
            String boardName = post.getBoard().getName();
            String postTitle = post.getPostTitle();
//            System.out.print("게시판 제목 : [%s], 게시물 제목 : [%s]".formatted(post.getBoard().getName(), post.getPostTitle())+" ");
        }
        System.out.println("-------- 게시물과 연관된 게시판 조회 완료 [쿼리 발생 X] ---------");


        System.out.println("-------- 게시판(Board) 전체 조회 요청 [쿼리 1번 발생] ---------");
        List<Board> boards = boardRepository.findAll();
        System.out.println("-------- 게시판 전체 조회 완료 [쿼리 1번 발생] ---------");


        System.out.println("-------- 게시판에 있는 게시물 조회 요청 [Batch Size 설정으로 쿼리 1번 발생] ---------");

        for(Board board : boards){
            board.getPost().forEach( post -> {
                String boardName = post.getBoard().getName();
                String postTitle = post.getPostTitle();
//                System.out.print("Board 이름 : [%s], Post 제목:[%s]".formatted(post.getBoard().getName(), post.getPostTitle())+" ");
            });
        }


        System.out.println("-------- 게시판에 있는 게시물 전체 조회 완료 [Batch Size 설정으로 쿼리 1번 발생] ---------");

        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");


    }
}
