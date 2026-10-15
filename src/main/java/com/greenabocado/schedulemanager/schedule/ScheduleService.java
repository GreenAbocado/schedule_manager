package com.greenabocado.schedulemanager.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.greenabocado.schedulemanager.comment.*;
import com.greenabocado.schedulemanager.common.CustomException;
import com.greenabocado.schedulemanager.schedule.dto.*;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    /* 도메인 패키지 간의 순환 참조 발생 */
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest requestDto) {
        Schedule schedule = scheduleRepository.save(requestDto.toEntity());
        log.info("[일정 생성] id: {}", schedule.getId());
        return ScheduleResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> getSchedules(String creator) {
        List<Schedule> schedules = creator == null ? scheduleRepository.findAllByOrderByUpdatedAtDesc()
                : scheduleRepository.findByCreatorOrderByUpdatedAtDesc(creator);  // creator 존재하지 않아도 빈 리스트 반환

        log.debug("[일정 전체 조회] 조건:{} 개수:{}", creator, schedules.size());
        return schedules.stream().map(ScheduleResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ScheduleDetailResponse getSchedule(Long id) {
        Schedule schedule = findById(id);

        /* comment 직접 의존 */
        List<Comment> comments = commentRepository.findAllByScheduleId(id);
        log.debug("[일정(+댓글) 단건 조회] id:{}", id);
        return ScheduleDetailResponse.from(schedule, comments);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest requestDto) {
        Schedule schedule = findById(id);

        verifyPassword(schedule, requestDto.password());
        schedule.updateInfo(requestDto.title(), requestDto.creator());

        log.info("[일정 변경] id:{}", id);
        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, ScheduleDeleteRequest request) {
        Schedule schedule = findById(id);
        verifyPassword(schedule, request.password());
        /* comment에 대한 삭제 결정 책임 */
        commentRepository.deleteAllByScheduleId(id);
        scheduleRepository.delete(schedule);
        log.info("[일정 삭제] id:{}", id);
    }

    private Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(()-> new CustomException(SCHEDULE_NOT_EXIST));
    }

    private void verifyPassword(Schedule schedule, String password) {
        if (!schedule.getPassword().equals(password)) {
            throw new CustomException(SCHEDULE_AUTHENTICATION_FAIL);
        }
    }
}