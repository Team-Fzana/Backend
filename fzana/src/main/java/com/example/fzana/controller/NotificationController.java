package com.example.fzana.controller;
import com.example.fzana.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //알림 구독
    @GetMapping("/subscribe/{memberId}")
    @Operation(summary = "알림 구독", description = "특정 회원이 알림을 구독합니다.")
    public SseEmitter subscribe(@PathVariable Long memberId) {
        return notificationService.subscribe(memberId);
    }

    //알림 전송
    @PostMapping("/notify/{memberId}")
    @Operation(summary = "알림 전송", description = "특정 회원에게 알림을 전송합니다.")
    public void notifyFollower(@PathVariable Long memberId, @RequestParam String message) {
        notificationService.sendNotification(memberId, message);
    }
}
