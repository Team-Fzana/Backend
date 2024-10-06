package com.example.fzana.domain.routine.api;

import com.example.fzana.domain.exception.MemberNotFoundException;
import com.example.fzana.domain.exception.RoutineNotFoundException;
import com.example.fzana.domain.routine.dto.RoutineRequest;
import com.example.fzana.domain.routine.dto.RoutineResponse;
import com.example.fzana.domain.routine.application.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routines")
public class RoutineController {

    private final RoutineService routineService;

    // 루틴 리스트 조회
    @GetMapping("/{memberId}")
    @Operation(summary = "사용자 루틴 리스트 불러오기", description = "사용자id를 사용해 루틴 리스트를 불러옵니다.")
    public ResponseEntity<List<RoutineResponse>> getRoutineList(@PathVariable Long memberId){
        try{
            List<RoutineResponse> routineList = routineService.routineList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(routineList);
        }catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 루틴 생성
    @PostMapping("/{memberId}")
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

    // 루틴 수정
    @PutMapping("/{routineId}")
    @Operation(summary = "사용자 루틴 수정", description = "루틴 id를 사용해 사용자 루틴 수정")
    public ResponseEntity<RoutineResponse> updateRoutine (@PathVariable Long routineId,
                                                          @RequestBody RoutineRequest routineRequest){
        try{
            RoutineResponse routine = routineService.updateRoutine(routineId, routineRequest);
            return ResponseEntity.status(HttpStatus.OK).body(routine);
        }catch (RoutineNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 루틴 삭제
    @DeleteMapping("/{routineId}")
    @Operation(summary = "사용자 루틴 삭제", description = "루틴 id를 사용해 사용자 루틴 삭제")
    public ResponseEntity<RoutineResponse> deleteRoutine (@PathVariable Long routineId){
        try{
            RoutineResponse delete = routineService.deleteRoutine(routineId);
            return ResponseEntity.status(HttpStatus.OK).body(delete);
        }catch (RoutineNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




}
