package com.example.jwt.member.controller;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.service.JwtService;
import com.example.jwt.member.model.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    private final JwtService jwtService;

    @GetMapping("/")
    public String home(){
        return "Test";
    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO){
        HttpStatus status = HttpStatus.OK;  // Http 응답
        Map<String, Object> resultMap = new HashMap<>();

        logger.info("{}",memberDTO);    //log 찍어보기

        try{
            memberService.join(memberDTO);  // 회원가입 요청
            resultMap.put("memberInfo", memberDTO);
            resultMap.put("message","join success");
            status = HttpStatus.OK; // 성공 로직 200
        }catch (Exception e){
            logger.error(e.getMessage());

            resultMap.put("message",e.getMessage());
            status = HttpStatus.BAD_REQUEST;    // Error 400
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO){
        logger.info("{}",memberDTO);

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        //로그인 정보 가져오기
        try{
            MemberDTO loginMember = memberService.login(memberDTO);
            if(loginMember == null){
                // 로그인 정보가 없으면.
                resultMap.put("message","로그인 정보 Error");
                status = HttpStatus.ACCEPTED;
                return new ResponseEntity<Map<String, Object>>(resultMap, status);
            }
            // 있다면 토큰 발급
            // Access Token 발급
            String access_Token = jwtService.createAccessToken(memberDTO);
            logger.info("Access-Token : {}", access_Token);

            // Refresh Token 재발급
            String refresh_Token = jwtService.createRefreshToken(memberDTO);
            logger.info("Refresh-Token : {}", refresh_Token);

            //DB에 Refresh-Token update
            memberDTO.setToken(refresh_Token);
            memberService.updateToken(memberDTO);

            // resultMap
            resultMap.put("Access-Token", access_Token);
            resultMap.put("Refresh-Token", refresh_Token);
            status = HttpStatus.OK;

        }catch (Exception e){
            //에러 처리
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@RequestParam String token){
        logger.info("token : {}", token);
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        //check Token
        String isValid = jwtService.checkToken(token);

        if(isValid != null){    // 유효한 토큰
            // 토큰
            logger.info("Token is valid");
            try{
                resultMap.put("claims", isValid);
                status = HttpStatus.OK;
            }catch(Exception e){
                resultMap.put("message", "No User");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }else{  // 유효하지 않은 토큰
            // 토큰 재발급
            resultMap.put("message", "fail");
            status = HttpStatus.UNAUTHORIZED;   // 401

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
