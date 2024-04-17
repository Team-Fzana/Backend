package com.example.fzana.dto;

import java.time.LocalDateTime;

public class FollowForm {
    private Long userId;
    private Long targetUserId;
    private LocalDateTime timestamp;

    public FollowForm(Long userId, Long targetUserId) {
        validateId(userId, "사용자");
        validateId(targetUserId, "대상 사용자");
        this.userId = userId;
        this.targetUserId = targetUserId;
        this.timestamp = LocalDateTime.now();
    }

    private void validateId(Long id, String description) {
        if (id == null) {
            throw new IllegalArgumentException(description + " ID는 null이어서는 안 됩니다.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException(description + " ID는 양수이어야 합니다.");
        }
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public static class FollowingDTO {
        private Long followingId;
        private String name;

        public FollowingDTO(Long followingId, String name) {
            this.followingId = followingId;
            this.name = name;
        }

        public Long getFollowingId() {
            return followingId;
        }

        public void setFollowingId(Long followingId) {
            this.followingId = followingId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
