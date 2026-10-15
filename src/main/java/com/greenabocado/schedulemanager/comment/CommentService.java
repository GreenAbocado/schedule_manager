package com.greenabocado.schedulemanager.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.greenabocado.schedulemanager.comment.dto.*;
import com.greenabocado.schedulemanager.common.CustomException;
import com.greenabocado.schedulemanager.schedule.ScheduleRepository;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private static final int MAX_COMMENTS_PER_SCHEDULE = 10;

    @Transactional
    public CommentResponse createComment(Long scheduleId, CommentCreateRequest request) {
        /* schedule 관련 예외를 comment에서 터뜨려서 schedule의 응집도 저하 (개선 방안 고민) */
        if (!scheduleRepository.existsById(scheduleId)) { throw new CustomException(SCHEDULE_NOT_EXIST); }
        if (commentRepository.countByScheduleId(scheduleId) >= MAX_COMMENTS_PER_SCHEDULE) {
            throw new CustomException(COMMENT_OVER_LIMIT);
        }
        Comment comment = request.toEntity(scheduleId);
        commentRepository.save(comment);
        log.info("[댓글 생성] scheduleId:{} id:{}", scheduleId, comment.getId());
        return CommentResponse.from(comment);
    }
}