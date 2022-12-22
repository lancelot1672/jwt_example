package com.example.jwt.member.model.service;


import com.example.jwt.member.model.entity.MemberDTO;

public interface JwtService {
    public String createAccessToken(MemberDTO memberDTO);
    public String createRefreshToken(MemberDTO memberDTO);
    public <T> String createToken(String key, T data, String subject, long expire_time);

    public String checkToken(String token);
}
