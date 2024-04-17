package com.example.fzana.controller;

import com.example.fzana.domain.User;
import com.example.fzana.dto.UserForm;
import com.example.fzana.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<User> signIn(@RequestBody UserForm userForm) {
        User user = userService.signIn(userForm.getEmail(),userForm.getPassword());
        return ResponseEntity.ok(user);
    }

}
