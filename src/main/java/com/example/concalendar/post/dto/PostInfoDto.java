package com.example.concalendar.post.dto;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.comment.dto.CommentDto;
import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class PostInfoDto{

    private Long id;

    private Long boardId;

    private Long writerId;

    private String writerName;

    private String postTitle;

    private String postContent;

    private Set<String> postHeartSet;

    private long postHeartSize;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private List<CommentDto> commentDtoList;

    private Long previousId;

    private String previousTitle;

    private Long nextId;

    private String nextTitle;


    private PostInfoDto(Post post, Set<String> postHeartSet, Post previousPost, Post nextPost){
        this.id = post.getId();
        this.boardId = post.getBoard().getId();
        this.writerId = post.getWriter().getUserId();
        this.writerName = post.getWriter().getUserNickname();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postHeartSet = postHeartSet;
        this.postHeartSize = postHeartSet.size();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.commentDtoList = post.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
        this.previousId = previousPost.getId();
        this.previousTitle = previousPost.getPostTitle();
        this.nextId = nextPost.getId();
        this.nextTitle = nextPost.getPostTitle();
    }
    public static PostInfoDto createFromPreviousPost(Post post, Set<String> postHeartSet, Post previousPost){
        log.info("다음글이 없습니다 정적 페토리 메소드");
        Post nextPost = Post.builder()
                .id(null)
                .postTitle(null)
                .build();
        return new PostInfoDto(post, postHeartSet, previousPost, nextPost);
    }
    public static PostInfoDto createFromNextPost(Post post, Set<String> postHeartSet, Post nextPost){
        log.info("이전글이 없습니다 정적 페토리 메소드");
        Post previousPost = Post.builder()
                .id(null)
                .postTitle(null)
                .build();
        return new PostInfoDto(post, postHeartSet, previousPost, nextPost);
    }
    public static PostInfoDto createPostInfoDto(Post post, Set<String> postHeartSet, Post previousPost, Post nextPost){
        return new PostInfoDto(post, postHeartSet, previousPost, nextPost);
    }
//
//    public PostInfoDto(Post post, Set<String> postHeartSet, Post previousPost){
//        this.id = post.getId();
//        this.boardId = post.getBoard().getId();
//        this.writerId = post.getWriter().getUserId();
//        this.writerName = post.getWriter().getUserNickname();
//        this.postTitle = post.getPostTitle();
//        this.postContent = post.getPostContent();
//        this.postHeartSet = postHeartSet;
//        this.postHeartSize = postHeartSet.size();
//        this.createdDate = post.getCreatedDate();
//        this.modifiedDate = post.getModifiedDate();
//        this.commentDtoList = post.getCommentList().stream()
//                .map(comment -> new CommentDto(comment))
//                .collect(Collectors.toList());
//        if (previousPost == null){
//
//        }
//        this.previousId = previousPost.getId();
//        this.previousTitle = previousPost.getPostTitle();
//    }

//    public PostInfoDto(Post post, Set<String> postHeartSet, Post nextPost){
//        this.id = post.getId();
//        this.boardId = post.getBoard().getId();
//        this.writerId = post.getWriter().getUserId();
//        this.writerName = post.getWriter().getUserNickname();
//        this.postTitle = post.getPostTitle();
//        this.postContent = post.getPostContent();
//        this.postHeartSet = postHeartSet;
//        this.postHeartSize = postHeartSet.size();
//        this.createdDate = post.getCreatedDate();
//        this.modifiedDate = post.getModifiedDate();
//        this.commentDtoList = post.getCommentList().stream()
//                .map(comment -> new CommentDto(comment))
//                .collect(Collectors.toList());
//        this.nextId = nextPost.getId();
//        this.nextTitle = nextPost.getPostTitle();
//    }

}
