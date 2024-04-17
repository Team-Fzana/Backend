package com.example.fzana.dto;

public class FollowForm {
    private Long userId;
    private Long targetUserId;

    public FollowForm(Long userId, Long targetUserId) {
        this.userId = userId;
        this.targetUserId = targetUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
