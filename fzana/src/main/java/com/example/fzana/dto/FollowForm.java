package com.example.fzana.dto;

import java.time.LocalDateTime;

public class FollowForm {
    private Long memberId;
    private Long targetMemberId;
    private LocalDateTime timestamp;

    public FollowForm(Long memberId, Long targetMemberId) {
        validateId(memberId, "사용자");
        validateId(targetMemberId, "대상 사용자");
        this.memberId = memberId;
        this.targetMemberId = targetMemberId;
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

    public Long getmemberId() {
        return memberId;
    }

    public Long getTargetMemberId() {
        return targetMemberId;
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
