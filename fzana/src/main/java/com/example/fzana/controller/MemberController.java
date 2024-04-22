package com.example.fzana.controller;

import com.example.fzana.domain.Member;
import com.example.fzana.dto.*;
import com.example.fzana.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam String email) {
        memberService.validateDuplicateEmail(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Member> signIn(@RequestParam String email, @RequestParam String password) {
        Member member = memberService.signIn(email,password);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> signOut() {
        memberService.signOut();
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 사용자 정보 불러오기
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberInfoResponse> memberInfo(@PathVariable Long memberId){
        MemberInfoResponse infos = memberService.bringInfo(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(infos);
    }

    // 사용자 닉네임 입력 & 수정
    @PostMapping("/members/{memberId}/nickname")
    public ResponseEntity<NicknameResponse> submitNickname(@PathVariable Long memberId,
                                                           @RequestBody NicknameRequest nicknameRequest){
        NicknameResponse updated = memberService.submitNickname(memberId, nicknameRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 사용자 소개글 입력 & 수정
    @PostMapping("/members/{memberId}/introduce")
    public ResponseEntity<IntroduceResponse> submitNickname(@PathVariable Long memberId,
                                                            @RequestBody IntroduceRequest introduceRequest){
        IntroduceResponse updated = memberService.submitIntroduce(memberId, introduceRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 사용자 프로필 입력 & 수정
    @PostMapping(value = "/members/{memberId}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long memberId) {
        try {
            String bucketName = "fzana"; // S3 버킷 이름 설정
            String fileUrl = memberService.uploadFileAndSaveUrl(bucketName, file, memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

}
