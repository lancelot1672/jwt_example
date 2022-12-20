package com.example.jwt.member.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private String userId;
    private String password;
    private String userName;
    private String userEmail;
    private String joinDate;
    private String token;

}
