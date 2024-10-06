package com.example.fzana.domain.schedule.application;

import com.example.fzana.domain.exception.MemberNotFoundException;
import com.example.fzana.domain.member.domain.Member;
import com.example.fzana.domain.member.dao.MemberRepository;
import com.example.fzana.domain.exception.ScheduleNotFoundException;
import com.example.fzana.domain.schedule.dto.ScheduleRequest;
import com.example.fzana.domain.schedule.dto.ScheduleResponse;
import com.example.fzana.domain.schedule.dao.ScheduleRepository;
import com.example.fzana.domain.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    /*
     * todo-list, 일정 리스트 조회
     */
    public List<ScheduleResponse> scheduleList(Long memberId) {
        // 사용자 조회 및 예외 처리
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));

        return scheduleRepository.findByMemberId(memberId).stream()
                .map(ScheduleResponse :: createSchedule)
                .collect(Collectors.toList());
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

    public List<ScheduleResponse> getFriendCalendarForDate(Long userId, Long targetCalendarId, LocalDate date) {
        List<Schedule> schedules = scheduleRepository.findByMemberIdAndDate(targetCalendarId, date);

        return schedules.stream()
                .map(ScheduleResponse::createSchedule)
                .collect(Collectors.toList());
    }

    public List<ScheduleResponse> getScheduleForDate(Long memberId, LocalDate date) {
        // 사용자 조회 및 예외 처리
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberId + " not found."));

        // 특정 날짜에 해당하는 스케줄 조회
        List<Schedule> schedules = scheduleRepository.findByMemberIdAndDate(memberId, date);

        // 조회한 스케줄을 DTO로 변환하여 반환
        return schedules.stream()
                .map(ScheduleResponse::createSchedule)
                .collect(Collectors.toList());
    }

}
