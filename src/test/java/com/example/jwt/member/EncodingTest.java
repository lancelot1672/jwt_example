package com.example.jwt.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@SpringBootTest
public class EncodingTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 암호화 테스트")
    void encodingTest(){
        String originalPassword = "1234";
        String newPassword = passwordEncoder.encode(originalPassword);

        System.out.println("originalPassword = " + originalPassword);
        System.out.println("newPassword = " + newPassword);

        Assertions.assertTrue(passwordEncoder.matches(originalPassword, newPassword));
    }
}
