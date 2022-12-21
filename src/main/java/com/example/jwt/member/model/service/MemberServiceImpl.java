package com.example.jwt.member.model.service;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void join(MemberDTO memberDTO) throws SQLException {
        //비밀번호 암호화
        String originalPassword = memberDTO.getPassword();
        String newPassowrd = passwordEncoder.encode(originalPassword);
        memberDTO.setPassword(newPassowrd);

        //join request
        memberRepository.insert(memberDTO);
    }

    @Override
    public MemberDTO findById(String userId) throws SQLException {
        return memberRepository.findByUserId(userId);
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) throws SQLException {
        //GET member Info
        MemberDTO loginMember = memberRepository.findByUserId(memberDTO.getUserId());

        // 비밀번호 match 확인
        if(passwordEncoder.matches(memberDTO.getPassword(),loginMember.getPassword())){
            return loginMember;
        }
        return null;
    }

    @Override
    public void updateToken(MemberDTO memberDTO) throws SQLException {
        memberRepository.updateToken(memberDTO);
    }
}
