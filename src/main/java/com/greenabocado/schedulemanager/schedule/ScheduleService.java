package com.greenabocado.schedulemanager.schedule;

import com.greenabocado.schedulemanager.common.CustomException;
import com.greenabocado.schedulemanager.schedule.dto.ScheduleCreateRequest;
import com.greenabocado.schedulemanager.schedule.dto.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.greenabocado.schedulemanager.common.ErrorCode.SCHEDULE_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest dto) {
        Schedule schedule = scheduleRepository.save(dto.toEntity());
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
}