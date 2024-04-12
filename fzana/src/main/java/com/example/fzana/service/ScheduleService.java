package com.example.fzana.service;

import com.example.fzana.domain.Schedule;
import com.example.fzana.domain.User;
import com.example.fzana.dto.ScheduleForm;
import com.example.fzana.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;        // 수연이가 해줄거임

    /*
     * 일정 추가
     */
    public ScheduleForm createSchedule(Long userId, ScheduleForm scheduleForm) {
        // 1. 사용자 id 조회 및 예외 처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " +
                "대상 게시글이 없습니다."));

        // 2. 일정 엔티티 생성
        Schedule schedule = Schedule.createSchedule(user, scheduleForm);

        // 3. 일정 엔티티를 DB에 저장
        Schedule created = scheduleRepository.save(schedule);

        // 4. DTO로 변환해 반환
        return ScheduleForm.createSchedule(created);
    }

    /*
    * 일정 수정
    * */
    public ScheduleForm updateSchedule(Long todolistId, ScheduleForm scheduleForm) {
        // 1. 일정 조회 및 예외 처리
        Schedule target = scheduleRepository.findById(todolistId)
                .orElseThrow(() -> new IllegalArgumentException("일정 수정 실패!"+
                        "대상 일정이 없습니다."));

        // 2. 일정 정보 수정
        target.patch(scheduleForm);

        // 3. DB 저장
        Schedule updated = scheduleRepository.save(target);

        // 4. 일정 엔티티를 DTO로 변환 및 반환
        return ScheduleForm.createSchedule(updated);
    }

    /*
     * 일정 삭제
     * */
    public ScheduleForm delete(Long todolistId) {
        // 1. 일정 조회 및 예외 처리
        Schedule target = scheduleRepository.findById(todolistId)
                .orElseThrow(() -> new IllegalArgumentException("일정 삭제 실패!"+
                        "대상 일정이 없습니다."));

        // 2. 일정 삭제
        scheduleRepository.delete(target);

        // 3. 삭제 일정을 DTO로 변환 후 반환
        return ScheduleForm.createSchedule(target);

    }

}
