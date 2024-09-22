package com.example.fzana.service;

import com.example.fzana.domain.Member;
import com.example.fzana.domain.Routine;
import com.example.fzana.domain.Schedule;
import com.example.fzana.dto.RoutineRequest;
import com.example.fzana.dto.RoutineResponse;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.exception.RoutineNotFoundException;
import com.example.fzana.exception.ScheduleNotFoundException;
import com.example.fzana.repository.MemberRepository;
import com.example.fzana.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;

    /*
    * 루틴 리스트 가져오기
    */
    public List<RoutineResponse> routineList(Long memberId) {
        // 사용자 조회 및 예외 처리
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));
//
//        // 스케줄 조회
//        List<Routine> routines = routineRepository.findRoutinesByMemberId(memberId);
//
//        // 엔티티 -> DTO 변환
//        List<RoutineResponse> dtos = new ArrayList<RoutineResponse>();
//        for (int i = 0; i < routines.size(); i++){ // 1. 조회한 일정 엔티티 수 만큼 반복하기
//            Routine s = routines.get(i);          // 2. 조회한 일정 엔티티 하나씩 가져오기
//            RoutineResponse dto = RoutineResponse.createRoutine(s); // 3. 엔티티를 DTO로 변환
//            dtos.add(dto);                          // 4. 변환한 DTO를 dtos 리스트에 삽입
//        }
//
//        return dtos;

        return routineRepository.findRoutinesByMemberId(memberId).stream()
                .map(RoutineResponse :: createRoutine)
                .collect(Collectors.toList());
    }

    /*
    * 루틴 생성
    */
    public RoutineResponse createRoutine(Long memberId, RoutineRequest routineRequest) {
        // 사용자 조회 및 예외 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));

        // DTO 엔티티로 변환
        Routine routine = Routine.createRoutine(member, routineRequest);

        // DB에 저장
        Routine created = routineRepository.save(routine);

        // DTO로 변환해서 반환
        return RoutineResponse.createRoutine(created);


    }

    /*
    *  루틴 수정
    */
    public RoutineResponse updateRoutine(Long routineId, RoutineRequest routineRequest) {
        // 루틴 조회 및 예외 처리
        Routine target = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineNotFoundException("RoutineId with ID " + routineId + " not found."));

        // 루틴 정보 수정
        target.patch(routineRequest);

        // DB 저장
        Routine routine = routineRepository.save(target);

        // 엔티티 -> DTO로 변환 후 반환
        return RoutineResponse.createRoutine(routine);
    }

    public RoutineResponse deleteRoutine(Long routineId) {
        // 루틴 조회 및 예외 처리
        Routine target = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineNotFoundException("Routine with ID " + routineId + " not found."));

        // 삭제
        routineRepository.delete(target);

        // 삭제한 데이터 DTO로 변환 후 반환
        return RoutineResponse.createRoutine(target);
    }
}
