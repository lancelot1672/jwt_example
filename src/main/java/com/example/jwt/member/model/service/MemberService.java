package com.example.jwt.member.model.service;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


public interface MemberService {
    public void join(MemberDTO memberDTO) throws SQLException;
    public MemberDTO findOne(String userId) throws SQLException;

}
