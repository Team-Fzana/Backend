package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    private Long id;
    private Long followerId;
    private Long followingId;
    private LocalDateTime createdAt;
    private String content;
    private Integer checkStatus;
    private LocalDateTime thisDay;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponse {
        private String message;
    }

    // If there are more fields, add them here

    // Instance of the nested message response
    private MessageResponse messageResponse;

    // Other methods and logic
}
