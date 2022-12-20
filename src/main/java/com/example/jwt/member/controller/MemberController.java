package com.example.jwt.member.controller;

import com.example.jwt.member.model.entity.MemberDTO;
import com.example.jwt.member.model.service.MemberService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

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
}
