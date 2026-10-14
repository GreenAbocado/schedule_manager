package com.greenabocado.schedulemanager.schedule;

import com.greenabocado.schedulemanager.schedule.dto.ScheduleCreateRequest;
import com.greenabocado.schedulemanager.schedule.dto.ScheduleResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}