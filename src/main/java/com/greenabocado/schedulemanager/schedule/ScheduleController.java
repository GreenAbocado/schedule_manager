package com.greenabocado.schedulemanager.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.greenabocado.schedulemanager.common.ApiResponse;
import com.greenabocado.schedulemanager.schedule.dto.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(
            @RequestBody ScheduleCreateRequest requestDto) {

        ScheduleResponse response = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getSchedules(
            @RequestParam(required=false) String creator) {

        List<ScheduleResponse> response = scheduleService.getSchedules(creator);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleDetailResponse>> getSchedule(@PathVariable Long id) {
        ScheduleDetailResponse response = scheduleService.getSchedule(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(
            @PathVariable Long id, @RequestBody ScheduleUpdateRequest requestDto) {

        ScheduleResponse response = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id, @RequestBody ScheduleDeleteRequest requestDto) {
        scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}