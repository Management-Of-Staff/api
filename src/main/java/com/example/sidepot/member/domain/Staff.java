package com.example.sidepot.member.domain;

import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "memberId", column = @Column(name = "staff_id"))
})
@Entity
@Table(name = "staff")
public class Staff extends Member {

    @OneToMany(mappedBy = "staff")
    private List<Employment> employments = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<WeekWorkTime> weekWorkTimeList = new ArrayList<>();


    private Staff(String name, String password, String phoneNum,  Role role, LocalDateTime createDate) {
        super(name, password, phoneNum, role, createDate);
    }

    public static Staff registerStaff(String name, String password, String phoneNum, LocalDateTime createDate){
        return new Staff(name, password, phoneNum, Role.STAFF, createDate);}


}
