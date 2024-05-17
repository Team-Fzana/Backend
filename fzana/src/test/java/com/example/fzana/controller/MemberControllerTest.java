package com.example.fzana.controller;

import com.example.fzana.controller.MemberController;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void updateState_Active_Test() throws Exception {
        Long memberId = 1L;
        Mockito.when(memberService.updateState(memberId)).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/{memberId}/avtive", memberId))
                .andExpect(status().isOk())
                .andExpect(content().string("활성화!"));
    }

    @Test
    public void updateState_Inactive_Test() throws Exception {
        Long memberId = 1L;
        Mockito.when(memberService.updateState(memberId)).thenReturn(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/{memberId}/avtive", memberId))
                .andExpect(status().isOk())
                .andExpect(content().string("비활성화!"));
    }

    @Test
    public void updateState_NotFound_Test() throws Exception {
        Long memberId = 1L;
        Mockito.when(memberService.updateState(memberId)).thenThrow(new MemberNotFoundException("Member not found."));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/{memberId}/avtive", memberId))
                .andExpect(status().isNotFound());
    }
}
