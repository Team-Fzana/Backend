package com.example.fzana.service;

import com.example.fzana.domain.Schedule;
import com.example.fzana.domain.User;
import com.example.fzana.dto.ScheduleForm;
import com.example.fzana.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;        // 수연이가 해줄거임

    /*
     * 일정 추가
     */
    public Schedule createSchedule(Long userId, ScheduleForm scheduleForm) {

        User user = userRepository.findById(userId).orElse(null);   // 수연이가 해줄거임
        if (user == null)
            return null; // 유저가 존재하지 않을 경우 예외 처리

        Schedule schedule = new Schedule(user, scheduleForm.getContent(), scheduleForm.getThisDay(),
                                        scheduleForm.getCheckStatus(), scheduleForm.getStartTime(),
                                        scheduleForm.getEndTime());

        return scheduleRepository.save(schedule);
    }
}
