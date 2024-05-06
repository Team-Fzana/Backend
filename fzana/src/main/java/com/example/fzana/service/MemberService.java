package com.example.fzana.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fzana.domain.Member;
import com.example.fzana.dto.*;
import com.example.fzana.exception.InvalidMemberException;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class MemberService {

    // s3 버킷 클라이언트
    private final AmazonS3Client s3Client;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, AmazonS3Client s3Client) {
        this.memberRepository = memberRepository;
        this.s3Client = s3Client;
    }

    /*
     * 회원가입
     */
    @Transactional
    public Member signUp(String email, String password, String nickName,
                         String introduce) {
        validateDuplicateEmail(email); //이메일 중복 확인

        Member member = new Member(email,password,nickName,introduce);
        return memberRepository.save(member); //DB 저장

    }

    /*
     * 로그인
     */
    @Transactional
    public Member signIn(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidMemberException("가입되지 않은 이메일입니다."));

        if (!member.getPassword().equals(password)) {
            throw new InvalidMemberException("비밀번호가 올바르지 않습니다.");
        }

        return member;
    }

    /*
     * 이메일 중복 확인
     */
    public void validateDuplicateEmail(String email) {
        Optional<Member> findMember= memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new InvalidMemberException("이미 가입된 이메일입니다.");
        }
    }

    /*
     * 로그아웃
     */
    public void signOut() {

    }

    /*
     * 닉네임 등록
     */
    public NicknameResponse submitNickname(Long memberId, NicknameRequest nicknameRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("올바르지 않은 사용자"));

        member.updateNickname(nicknameRequest);

        Member updated = memberRepository.save(member);

        return NicknameResponse.createNicknameDto(updated);
    }

    /*
     * 소개글 등록
     */
    public IntroduceResponse submitIntroduce(Long memberId, IntroduceRequest introduceRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("올바르지 않은 사용자"));

        member.updateIntroduce(introduceRequest);

        Member updated = memberRepository.save(member);

        return IntroduceResponse.createIntroduceDto(updated);
    }

    /*
     * 사용자 정보 가져오기 (일단 닉네임, 소개글)
     */
    public MemberInfoResponse bringInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("올바르지 않은 사용자"));

        return MemberInfoResponse.createMemberinfoDto(member.getNickName(), member.getIntroduce());

    }


    /*
     * 사용자 프로필 사진 등록 로직 (s3)
     */
    public String uploadFileAndSaveUrl(String bucketName, MultipartFile multipartFile, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("올바르지 않은 사용자"));

        String fileUrl = uploadFileToS3Bucket(bucketName, multipartFile);

        member.updateProfile(fileUrl);

        memberRepository.save(member);

        return fileUrl;
    }

    // S3에 업로드 하기
    private String uploadFileToS3Bucket(String bucketName, MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete(); // 임시 파일 삭제

        return s3Client.getResourceUrl(bucketName, fileName); // s3에 저장된 파일의 Url 받기
    }

    // MultiPart 를 파일로 변환 (임시 파일 생성)
    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);

        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }
}
