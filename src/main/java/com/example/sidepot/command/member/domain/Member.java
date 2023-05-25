package com.example.sidepot.command.member.domain;

import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.command.member.dto.MemberUpdateDto.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@MappedSuperclass
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "email")
    private String email;
    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_phone_num", nullable = false, unique = true)
    private String memberPhoneNum;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Embedded
    private BaseFilePath profileImage;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "withdrawal_date")
    private LocalDateTime withdrawalDate;

    private Member(Long memberId){
        this.memberId = memberId;
    }

    protected Member(String name, String password, String phoneNum, Role role, LocalDateTime registerDate) {
        this.memberName = name;
        this.password = password;
        this.memberPhoneNum = phoneNum;
        this.role = role;
        this.registerDate = registerDate;
    }

    public void updateMemberProfile(MemberUpdateProfileRequestDto memberUpdateProfileRequestDto,
                                    BaseFilePath baseFilePath){
        this.birthDate = LocalDate.parse(memberUpdateProfileRequestDto.getBirthDate());
        this.email = memberUpdateProfileRequestDto.getEmail();
        this.profileImage = baseFilePath;
    }

    public void updateMemberPhone(MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto){
        this.memberPhoneNum = memberUpdatePhoneRequestDto.getPhoneNum();
    }

    public void updateMemberPassword(String newPassword){
        this.password = newPassword;
    }

    public void updateMemberWithdrawalDate(LocalDateTime withdrawalDate){
        this.withdrawalDate = withdrawalDate;
    }
}
