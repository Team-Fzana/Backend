package com.example.fzana.controller;

import com.example.fzana.dto.FollowForm;
import com.example.fzana.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{userId}/following/{targetUserId}")
    public ResponseEntity<?> createFollow(@PathVariable Long userId, @PathVariable Long targetUserId) {
        FollowForm followForm = new FollowForm(userId, targetUserId);
        try {
            String message = followService.createFollow(followForm);
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
