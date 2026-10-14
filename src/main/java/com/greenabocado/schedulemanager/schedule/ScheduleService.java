package com.greenabocado.schedulemanager.schedule;

import com.greenabocado.schedulemanager.common.CustomException;
import com.greenabocado.schedulemanager.schedule.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

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
    public ScheduleResponse getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(()-> new CustomException(SCHEDULE_NOT_EXIST));

        log.debug("[일정 단건 조회] id:{}", id);
        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(()-> new CustomException(SCHEDULE_NOT_EXIST));

        verifyPassword(schedule, requestDto.password());
        schedule.updateInfo(requestDto.title(), requestDto.creator());

        log.info("[일정 변경] id:{}", id);
        return ScheduleResponse.from(schedule);
    }

    private void verifyPassword(Schedule schedule, String password) {
        if (!schedule.getPassword().equals(password)) {
            throw new CustomException(SCHEDULE_AUTHENTICATION_FAIL);
        }
    }
}