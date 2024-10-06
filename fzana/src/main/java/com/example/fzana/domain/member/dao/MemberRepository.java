package com.example.fzana.domain.member.dao;

import com.example.fzana.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.nickName = :nickNameOrEmail OR m.email = :nickNameOrEmail")
    List<Member> findByNicknameOrEmail(String nickNameOrEmail);
}
