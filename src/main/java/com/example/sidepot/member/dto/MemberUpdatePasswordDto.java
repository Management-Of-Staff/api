package com.example.sidepot.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdatePasswordDto {


    private String oldPassword;
    private String newPassword;
}
