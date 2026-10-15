package com.greenabocado.schedulemanager.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.greenabocado.schedulemanager.comment.dto.*;
import com.greenabocado.schedulemanager.common.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long scheduleId, @RequestBody CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(scheduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}
