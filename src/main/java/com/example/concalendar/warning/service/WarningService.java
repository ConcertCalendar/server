package com.example.concalendar.warning.service;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.comment.service.CommentService;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.reply.service.ReplyService;
import com.example.concalendar.warning.entity.Warning;
import com.example.concalendar.warning.entity.WarningTypeEnum;
import com.example.concalendar.warning.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarningService {

    private final WarningRepository warningRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;

    @Transactional
    public void reportPostWarn(Long postId, WarningTypeEnum warningTypeEnum) {
        Post post = postService.findPostByPostId(postId);

        Warning warning = Warning.builder()
                .warningTypeEnum(warningTypeEnum)
                .post(post)
                .build();

        post.updateWarningCnt();

        warningRepository.save(warning);
    }

    @Transactional
    public void reportCommentWarn(Long commentId, WarningTypeEnum warningTypeEnum) {
        Comment comment = commentService.findByCommentId(commentId);

        Warning warning = Warning.builder()
                .warningTypeEnum(warningTypeEnum)
                .comment(comment)
                .build();
        comment.updateCommentWarningCnt();

        warningRepository.save(warning);
    }

    @Transactional
    public void reportReplyWarn(Long replyId, WarningTypeEnum warningTypeEnum) {
        Reply reply = replyService.findByReplyId(replyId);

        Warning warning = Warning.builder()
                .warningTypeEnum(warningTypeEnum)
                .reply(reply)
                .build();

        reply.updateReplyWarningCnt();

        warningRepository.save(warning);
    }

}
