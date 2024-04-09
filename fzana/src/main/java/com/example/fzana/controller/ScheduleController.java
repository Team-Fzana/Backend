package com.example.fzana.controller;

import com.example.fzana.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 1. todo-list 추가
    // 2. todo-list 수정
    // 3. todo-list 삭제
    // 4. todo-list 진행 체크(변경)
    // 5. calendar 조회



}
