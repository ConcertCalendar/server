package com.example.concalendar.warning.service;

import com.example.concalendar.warning.entity.Warning;
import com.example.concalendar.warning.entity.WarningTypeEnum;
import com.example.concalendar.warning.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarningService {

    private final WarningRepository warningRepository;
    public void reportPostWarn(Long postId, Long warningTypeEnum) {

    }

    public void reportCommentWarn(Long postId) {

    }

    public void reportReplyWarn(Long postId) {

    }

}
