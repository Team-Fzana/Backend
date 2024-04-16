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
}
