package com.example.fzana.controller;

import com.example.fzana.domain.User;
import com.example.fzana.dto.*;
import com.example.fzana.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService=userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody UserForm userForm) {
        User user=userService.signUp(
                userForm.getEmail(),
                userForm.getPassword(),
                userForm.getNickName(),
                userForm.getIntroduce()
        );
        return ResponseEntity.ok(user);
    }

    // 이메일 중복 확인
    @GetMapping("/valid")
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam String email) {
        userService.validateDuplicateEmail(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> signIn(@RequestParam String email, @RequestParam String password) {
        User user = userService.signIn(email,password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> signOut() {
        userService.signOut();
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 사용자 정보 불러오기
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserinfoResponse> userInfo(@PathVariable Long userId){
        UserinfoResponse infos = userService.bringInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(infos);
    }

    // 사용자 닉네임 입력 & 수정
    @PostMapping("/users/{userId}/nickname")
    public ResponseEntity<NicknameResponse> submitNickname(@PathVariable Long userId,
                                                           @RequestBody NicknameRequest nicknameRequest){
        NicknameResponse updated = userService.submitNickname(userId, nicknameRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 사용자 소개글 입력 & 수정
    @PostMapping("/users/{userId}/introduce")
    public ResponseEntity<IntroduceResponse> submitNickname(@PathVariable Long userId,
                                                            @RequestBody IntroduceRequest introduceRequest){
        IntroduceResponse updated = userService.submitIntroduce(userId, introduceRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
