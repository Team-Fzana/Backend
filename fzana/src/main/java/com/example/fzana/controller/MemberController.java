package com.example.fzana.controller;

import com.example.fzana.domain.Member;
import com.example.fzana.dto.*;
import com.example.fzana.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/member")
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
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam String email) {
        memberService.validateDuplicateEmail(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }


    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인 합니다.")
    public ResponseEntity<Member> signIn(@RequestParam String email, @RequestParam String password) {
        Member member = memberService.signIn(email,password);
        return ResponseEntity.ok(member);
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
    public ResponseEntity<MemberInfoResponse> memberInfo(@PathVariable Long memberId){
        MemberInfoResponse infos = memberService.bringInfo(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(infos);
    }

    // 사용자 닉네임 수정
    @PostMapping("/members/{memberId}/nickname")
    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    public ResponseEntity<NicknameResponse> submitNickname(@PathVariable Long memberId,
                                                           @RequestBody NicknameRequest nicknameRequest){
        NicknameResponse updated = memberService.submitNickname(memberId, nicknameRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 사용자 소개글 입력 & 수정
    @PostMapping("/members/{memberId}/introduce")
    @Operation(summary = "사용자 소개글 수정", description = "사용자의 소개글을 수정합니다.")
    public ResponseEntity<IntroduceResponse> submitNickname(@PathVariable Long memberId,
                                                            @RequestBody IntroduceRequest introduceRequest){
        IntroduceResponse updated = memberService.submitIntroduce(memberId, introduceRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 사용자 프로필 입력 & 수정
    @PostMapping(value = "/members/{memberId}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "사용자 프로필 사진 수정", description = "사용자의 프로필 사진을.")
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
