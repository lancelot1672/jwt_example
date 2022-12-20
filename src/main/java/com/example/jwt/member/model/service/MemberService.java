package com.example.jwt.member.model.service;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void join(MemberDTO memberDTO) throws SQLException{
        //비밀번호 암호화
        String originalPassword = memberDTO.getPassword();
        String newPassowrd = passwordEncoder.encode(originalPassword);
        memberDTO.setPassword(newPassowrd);

        //join request
        memberRepository.insert(memberDTO);
    }
    public MemberDTO findOne(String userId) throws SQLException{
        return memberRepository.findByUserId(userId);
    }

}
