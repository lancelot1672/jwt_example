package com.example.jwt.member.model.service;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDTO memberDTO) throws SQLException{
        //비밀번호 암호화

        memberRepository.insert(memberDTO);
    }
}
