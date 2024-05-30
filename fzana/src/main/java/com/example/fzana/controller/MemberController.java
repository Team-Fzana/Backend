package com.example.fzana.controller;

import com.example.fzana.domain.Member;
import com.example.fzana.dto.*;
import com.example.fzana.exception.InvalidMemberException;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.service.MemberService;
import com.example.fzana.service.ScheduleService;
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
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;

    @Autowired
    public MemberController(MemberService memberService, ScheduleService scheduleService) {
        this.memberService = memberService;
        this.scheduleService = scheduleService;
    }

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임, 소개글을 등록하여 회원가입을 합니다.")
    public ResponseEntity<Member> signUp(@RequestBody MemberForm memberForm) {
        try {
            Member member = memberService.signUp(
                    memberForm.getEmail(),
                    memberForm.getPassword(),
                    memberForm.getNickName(),
                    memberForm.getIntroduce(),
                    memberForm.getMemberPhoto() // 이미지 URL 전달
            );
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 이메일 중복 확인
    @GetMapping("/valid")
    @Operation(summary = "유효성 검사", description = "이메일 형식, 중복 확인을 합니다.")
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam String email) {
        try{
            memberService.validateDuplicateEmail(email);
            return ResponseEntity.ok("사용 가능한 이메일 입니다.");
        } catch (InvalidMemberException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error : 사용이 불가능한 이메일 입니다.");
        }

    }


    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인 합니다.")
    public ResponseEntity<Member> signIn(@RequestParam String email, @RequestParam String password) {
        try{
            Member member = memberService.signIn(email,password);
            return ResponseEntity.ok(member);
        } catch (InvalidMemberException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    public ResponseEntity<String> signOut() {
        memberService.signOut();
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 사용자 정보 불러오기
    @GetMapping("/{memberId}")
    @Operation(summary = "사용자 정보 불러오기", description = "사용자의 정보를 불러옵니다.")
    public ResponseEntity<MemberInfoResponse> memberInfo(@PathVariable Long memberId){
        try{
            MemberInfoResponse infos = memberService.bringInfo(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(infos);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    // 사용자 닉네임 수정
    @PostMapping("/{memberId}/nickname")
    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    public ResponseEntity<NicknameResponse> submitNickname(@PathVariable Long memberId,
                                                           @RequestBody NicknameRequest nicknameRequest){
        try {
            NicknameResponse updated = memberService.submitNickname(memberId, nicknameRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    // 사용자 소개글 입력 & 수정
    @PostMapping("/{memberId}/introduce")
    @Operation(summary = "사용자 소개글 수정", description = "사용자의 소개글을 수정합니다.")
    public ResponseEntity<IntroduceResponse> submitNickname(@PathVariable Long memberId,
                                                            @RequestBody IntroduceRequest introduceRequest){
        try{
            IntroduceResponse updated = memberService.submitIntroduce(memberId, introduceRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 사용자 프로필 입력 & 수정
    @PostMapping(value = "/{memberId}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/search")
    @Operation(summary = "사용자 검색", description = "사용자 검색 결과를 가져옵니다.")
    public ResponseEntity<List<MemberListResponse>> searchMember(@RequestParam String nickNameOrEmail) {
            // 서비스에 위임
            List<MemberListResponse> memberList  = memberService.bringMemberList(nickNameOrEmail);
            // 검색한 리스트 결과 반환
            return (memberList != null) ?
                    ResponseEntity.status(HttpStatus.OK).body(memberList) // 리스트 반환
                    : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 없으면 null 값 반환
    }

    // 진행 중인 항목이 있을 때 사용자의 활동 상태 활성화, 없으면 비활성
    @PostMapping("/{memberId}/avtive")
    @Operation(summary = "사용자 활동상태 업데이트", description = "사용자의 활동 상태를 업데이트 합니다.")
    public ResponseEntity<String> updateState(@PathVariable Long memberId){
        try{
            Integer result = memberService.updateState(memberId);
            return (result == 1) ?
                    ResponseEntity.status(HttpStatus.OK).body("활성화!")
                    : ResponseEntity.status(HttpStatus.OK).body("비활성화!");
        }catch(MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
