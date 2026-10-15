package com.greenabocado.schedulemanager.comment;

import com.greenabocado.schedulemanager.comment.dto.CommentCreateRequest;
import com.greenabocado.schedulemanager.comment.dto.CommentResponse;
import com.greenabocado.schedulemanager.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
