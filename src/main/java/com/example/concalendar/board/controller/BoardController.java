package com.example.concalendar.board.controller;

import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity getAllPosts(@PathVariable Long id){
        List<Post> postList = postService.findAllPostsByBoardId(id);
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post posts : postList){

            PostDto postDto = PostDto.entityToGetPostDto(posts);

            postDtoList.add(postDto);

        }

        Message message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("boardId="+id+"에 해당하는 게시물 리스트 전송 성공");
        message.setData(postDtoList);

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

}
