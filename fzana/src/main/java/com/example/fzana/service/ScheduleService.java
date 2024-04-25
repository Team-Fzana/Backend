package com.example.fzana.service;

import com.example.fzana.domain.Schedule;
import com.example.fzana.domain.Member;
import com.example.fzana.dto.ScheduleRequest;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.exception.ScheduleNotFoundException;
import com.example.fzana.repository.ScheduleRepository;
import com.example.fzana.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;        // 수연이가 해줄거임

    /*
     * todo-list, 일정 리스트 조회
     */
    public List<ScheduleResponse> scheduleList(Long memberId) {
        // 사용자 조회 및 예외 처리
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));
        // 1. 일정 & todo-list 조회
        List<Schedule> schedules = scheduleRepository.findByMemberId(memberId);

        // 2. 엔티티 -> DTO 변환
        List<ScheduleResponse> dtos = new ArrayList<ScheduleResponse>();
        for (int i = 0; i < schedules.size(); i++){ // 1. 조회한 일정 엔티티 수 만큼 반복하기
            Schedule s = schedules.get(i);          // 2. 조회한 댓글 엔티티 하나씩 가져오기
            ScheduleResponse dto = ScheduleResponse.createSchedule(s); // 3. 엔티티를 DTO로 변환
            dtos.add(dto);                          // 4. 변환한 DTO를 dtos 리스트에 삽입
        }

        return dtos;
    }

    /*
     * 일정 추가
     */
    public ScheduleResponse createSchedule(Long memberId, ScheduleRequest scheduleRequest) {
        // 1. 사용자 id 조회 및 예외 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));


        // 2. 일정 엔티티 생성
        Schedule schedule = Schedule.createSchedule(member, scheduleRequest);

        // 3. 일정 엔티티를 DB에 저장
        Schedule created = scheduleRepository.save(schedule);

        // 4. DTO로 변환해 반환
        return ScheduleResponse.createSchedule(created);

    }

    /*
    * 일정 수정
    * */
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleRequest scheduleRequest) {
        // 1. 일정 조회 및 예외 처리
        Schedule target = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with ID " + scheduleId + " not found."));

        // 2. 일정 정보 수정
        target.patch(scheduleRequest);

        // 3. DB 저장
        Schedule updated = scheduleRepository.save(target);

        // 4. 일정 엔티티를 DTO로 변환 및 반환
        return ScheduleResponse.createSchedule(updated);
    }

    /*
     * 일정 삭제
     * */
    public ScheduleResponse delete(Long scheduleId) {
        // 1. 일정 조회 및 예외 처리
        Schedule target = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with ID " + scheduleId + " not found."));

        // 2. 일정 삭제
        scheduleRepository.delete(target);

        // 3. 삭제 일정을 DTO로 변환 후 반환
        return ScheduleResponse.createSchedule(target);

    }

}
