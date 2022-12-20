package com.example.jwt.member;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void join() throws Exception{
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("test1234");
        memberDTO.setPassword("1234");
        memberDTO.setUserEmail("test1234@naver.com");
        memberDTO.setUserName("김동률");

        memberService.join(memberDTO);  // 회원가입

        MemberDTO findMember = memberService.findOne("test1234");

        Assertions.assertThat(memberDTO).isEqualTo(memberDTO);

    }
}
