package com.example.sidepot.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberUpdateProfileDto {

    private Date birthDate;
    private String Email;
}
