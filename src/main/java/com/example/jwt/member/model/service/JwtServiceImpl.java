package com.example.jwt.member.model.service;

import com.example.jwt.member.model.entity.MemberDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService{
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
    private static final String SALT = "Ssalgorithm";
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1; // 분단위
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2; // 주단위

    @Override
    public String createAccessToken(MemberDTO memberDTO) {
        //1000ms * 30(m) * 60(s)
        return createToken("userId", memberDTO.getUserId(), "Access-Token", 1000 * 60 * 30 * ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public String createRefreshToken(MemberDTO memberDTO) {
        //1000ms * 60(m) * 60(s) * 24(H) * 7(week) = 2 weeks
        return createToken("userId", memberDTO.getUserId(), "Refresh-Token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public <T> String createToken(String key, T data, String subject, long expire_time) {
        String jwt = Jwts.builder()
                // Header 설정
                .setHeaderParam("typ","JWT")
                .setHeaderParam("regDate",System.currentTimeMillis())

                // Payload 설정
                .setExpiration(new Date(System.currentTimeMillis() + expire_time))
                .setSubject(subject)
                .claim(key, data)

                //Signature 설정 : secret Key를 활용한 암호화
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact(); //직렬화 처리.

        return jwt;
    }

    @Override
    public String checkToken(String token) {
        //JSON Web Signature (JWS)
        // set SigningKey : JWS 서명 검증을 위한 secret key 세팅
        // parseClaimsJws : 파싱하여 원본 Jws 만들기
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
            logger.info("claims : {}", claims.toString());
            return claims.toString();
        }catch (Exception e){
            return null;
        }
    }


    private byte[] generateKey(){
        byte[] key = null;
        try{
            key =SALT.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return key;
    }
}
