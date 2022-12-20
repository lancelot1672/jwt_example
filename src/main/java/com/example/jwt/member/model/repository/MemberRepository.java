package com.example.jwt.member.model.repository;

import com.example.jwt.member.model.entity.MemberDTO;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface MemberRepository {
    public void insert(MemberDTO memberDTO) throws SQLException;
}
