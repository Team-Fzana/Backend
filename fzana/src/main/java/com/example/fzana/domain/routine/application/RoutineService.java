package com.example.fzana.domain.routine.application;

import com.example.fzana.domain.member.exception.MemberNotFoundException;
import com.example.fzana.domain.member.domain.Member;
import com.example.fzana.domain.member.dao.MemberRepository;
import com.example.fzana.domain.routine.exception.RoutineNotFoundException;
import com.example.fzana.domain.routine.dto.RoutineRequest;
import com.example.fzana.domain.routine.dto.RoutineResponse;
import com.example.fzana.domain.routine.dao.RoutineRepository;
import com.example.fzana.domain.routine.domain.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
