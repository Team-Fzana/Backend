package com.example.fzana.service;

import com.example.fzana.domain.Schedule;
import com.example.fzana.domain.User;
import com.example.fzana.dto.ScheduleForm;
import com.example.fzana.repository.ScheduleRepository;
import com.example.fzana.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;        // 수연이가 해줄거임


    /*
     * todo-list, 일정 리스트 조회
     */
    public List<ScheduleForm> scheduleList(Long userId) {
        // 1. 일정 & todo-list 조회
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        // 2. 엔티티 -> DTO 변환
        List<ScheduleForm> dtos = new ArrayList<ScheduleForm>();
        for (int i = 0; i < schedules.size(); i++){ // 1. 조회한 일정 엔티티 수 만큼 반복하기
            Schedule s = schedules.get(i);          // 2. 조회한 댓글 엔티티 하나씩 가져오기
            ScheduleForm dto = ScheduleForm.createSchedule(s); // 3. 엔티티를 DTO로 변환
            dtos.add(dto);                          // 4. 변환한 DTO를 dtos 리스트에 삽입
        }

        return dtos;
    }

    /*
     * 일정 추가
     */
    public ScheduleForm createSchedule(Long userId, ScheduleForm scheduleForm) {
        // 1. 사용자 id 조회 및 예외 처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("일정 & todo-list 추가 실패! " +
                        "해당 사용자가 없습니다."));

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
