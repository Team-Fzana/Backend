package com.example.fzana.service;

import com.example.fzana.domain.States;
import com.example.fzana.domain.User;
import com.example.fzana.dto.UserForm;
import com.example.fzana.exception.InvalidUserException;
import com.example.fzana.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;

    }

    /*
     * 회원가입
     */
    @Transactional
    public User signUp(String email, String password, String nickName,
                       String introduce) {
        validateDuplicateEmail(email); //이메일 중복 확인

        User user= new User(email,password,nickName,introduce);
        return userRepository.save(user); //DB 저장

    }
    /*
     * 이메일 중복 확인
     */
    private void validateDuplicateEmail(String email) {
        Optional<User> findUser=userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new InvalidUserException("이미 가입된 이메일입니다.");
        }
    }

}
