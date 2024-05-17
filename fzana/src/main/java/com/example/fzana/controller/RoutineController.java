package com.example.fzana.controller;

import com.example.fzana.dto.RoutineRequest;
import com.example.fzana.dto.RoutineResponse;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoutineController {

    private RoutineService routineService;

    // 사용자의 루틴 리스트 조회
    @GetMapping("/routines/{memberId}")
    @Operation(summary = "사용자 루틴 리스트 불러오기", description = "사용자id를 사용해 루틴 리스트를 불러옵니다.")
    public ResponseEntity<List<RoutineResponse>> getRoutineList(@PathVariable Long memberId){
        try{
            List<RoutineResponse> routineList = routineService.routineList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(routineList);
        }catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자의 루틴 생성
    @PostMapping("/member/{memberId}/routines")
    @Operation(summary = "사용자 루틴 추가", description = "사용자 id를 사용해 사용자 루틴 생성")
    public ResponseEntity<RoutineResponse> createRoutine (@PathVariable Long memberId,
                                                          @RequestBody RoutineRequest routineRequest){
        try{
            RoutineResponse routine = routineService.createRoutine(memberId, routineRequest);
            return ResponseEntity.status(HttpStatus.OK).body(routine);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    // 사용자 루틴 수정
//    @PutMapping("/routines/{routineId}")
//    @Operation(summary = "사용자 루틴 수정", description = "루틴 id를 사용해 사용자 루틴 수정")

//    // 사용자 루틴 삭제
//    @PutMapping("/routines/{routineId}")
//    @Operation(summary = "사용자 루틴 삭제", description = "루틴 id를 사용해 사용자 루틴 삭제")



}
