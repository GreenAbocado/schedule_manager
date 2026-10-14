package com.greenabocado.schedulemanager.schedule;

import com.greenabocado.schedulemanager.common.ApiResponse;
import com.greenabocado.schedulemanager.schedule.dto.ScheduleCreateRequest;
import com.greenabocado.schedulemanager.schedule.dto.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(
            @RequestBody ScheduleCreateRequest requestDto) {

        ScheduleResponse responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDto));
    }
}
