package com.example.fzana.domain.goal.application;

import com.example.fzana.domain.member.exception.MemberNotFoundException;
import com.example.fzana.domain.goal.dto.GoalRequest;
import com.example.fzana.domain.goal.dto.GoalResponse;
import com.example.fzana.domain.goal.dao.GoalRepository;
import com.example.fzana.domain.goal.domain.Goal;
import com.example.fzana.domain.member.domain.Member;
import com.example.fzana.domain.member.dao.MemberRepository;
import com.example.fzana.domain.schedule.exception.ScheduleNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    /*
     * 목표 조회
     */
    public List<GoalResponse> goalList(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(()->new MemberNotFoundException("Member with ID " + memberId + " not found."));
        List<Goal> goals=goalRepository.findByMemberId(memberId);

        //Goal을 GoalResponse로 변환
        List<GoalResponse> goalResponses=goals.stream()
                .map(goal-> new GoalResponse(goal.getId(), goal.getTitle(), goal.getProgress()))
                .collect(Collectors.toList());

        return goalResponses;

    }

    /*
     * 목표 추가
     */
    public GoalResponse createGoal(Long memberId, GoalRequest goalRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new MemberNotFoundException("Member with ID " + memberId + " not found."));

        Goal goal=Goal.createGoal(member,goalRequest);

        Goal createdGoal=goalRepository.save(goal);

        return GoalResponse.createGoal(createdGoal);

    }

    /*
     * 목표 수정
     */
    public GoalResponse update(final Long id, final GoalRequest goalRequest) {
        Goal goal=goalRepository.findById(id)
                .orElseThrow(()-> new ScheduleNotFoundException("Goal with ID " + id + " not found."));

        goal.change(goalRequest.getTitle(), goalRequest.getProgress());

        Goal updatedGoal=goalRepository.save(goal);

        return GoalResponse.createGoal(updatedGoal);
    }

    /*
     * 목표 삭제
     */
    @Transactional
    public void delete(final Long id) {
        Goal goal=goalRepository.findById(id)
                .orElseThrow(()->new ScheduleNotFoundException("Goal with ID " + id + " not found."));

        goalRepository.delete(goal);

    }


}
