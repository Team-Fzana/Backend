package com.example.fzana.controller;

import com.example.fzana.domain.Member;
import com.example.fzana.dto.*;
import com.example.fzana.exception.InvalidMemberException;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임, 소개글을 등록하여 회원가입을 합니다.")
    public ResponseEntity<Member> signUp(@RequestBody MemberForm memberForm) {
            Member member = memberService.signUp(
                    memberForm.getEmail(),
                    memberForm.getPassword(),
                    memberForm.getNickName(),
                    memberForm.getIntroduce()
            );
            return ResponseEntity.ok(member);
    }

    // 이메일 중복 확인
    @GetMapping("/valid")
    @Operation(summary = "유효성 검사", description = "이메일 형식, 중복 확인을 합니다.")
    public ResponseEntity<?> checkDuplicateEmail(@RequestParam String email) {
        try{
            memberService.validateDuplicateEmail(email);
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        } catch (InvalidMemberException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "중복된 이메일 입니다."));
        }

    }


    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인 합니다.")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) {
        try{
            Member member = memberService.signIn(email,password);
            return ResponseEntity.ok(member);
        } catch (InvalidMemberException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "아이디 혹은 비밀번호가 올바르지 않습니다."));
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    public ResponseEntity<String> signOut() {
        memberService.signOut();
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 사용자 정보 불러오기
    @GetMapping("/members/{memberId}")
    @Operation(summary = "사용자 정보 불러오기", description = "사용자의 불러옵니다.")
    public ResponseEntity<?> memberInfo(@PathVariable Long memberId){
        try{
            MemberInfoResponse infos = memberService.bringInfo(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(infos);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "올바른 사용자가 아닙니다."));

        }

    }

    // 사용자 닉네임 수정
    @PostMapping("/members/{memberId}/nickname")
    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    public ResponseEntity<?> submitNickname(@PathVariable Long memberId,
                                                           @RequestBody NicknameRequest nicknameRequest){
        try {
            NicknameResponse updated = memberService.submitNickname(memberId, nicknameRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "올바른 사용자가 아닙니다."));

        }
    }

    // 사용자 소개글 입력 & 수정
    @PostMapping("/members/{memberId}/introduce")
    @Operation(summary = "사용자 소개글 수정", description = "사용자의 소개글을 수정합니다.")
    public ResponseEntity<?> submitNickname(@PathVariable Long memberId,
                                                            @RequestBody IntroduceRequest introduceRequest){
        try{
            IntroduceResponse updated = memberService.submitIntroduce(memberId, introduceRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "올바른 사용자가 아닙니다."));
        }
    }

    // 사용자 프로필 입력 & 수정
    @PostMapping(value = "/members/{memberId}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "사용자 프로필 사진 수정", description = "사용자의 프로필 사진을 수정합니다.")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long memberId) {
        try {
            String bucketName = "fzana"; // S3 버킷 이름 설정
            String fileUrl = memberService.uploadFileAndSaveUrl(bucketName, file, memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/members/search")
    @Operation(summary = "사용자 검색", description = "사용자 검색 결과를 가져옵니다.")
    public ResponseEntity<?> searchMember(@RequestParam String nickNameOrEmail) {
            // 서비스에 위임
            List<MemberListResponse> memberList  = memberService.bringMemberList(nickNameOrEmail);
            // 검색한 리스트 결과 반환
            return (memberList != null) ?
                    ResponseEntity.status(HttpStatus.OK).body(memberList) // 리스트 반환
                    : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 없으면 null 값 반환
    }

}
