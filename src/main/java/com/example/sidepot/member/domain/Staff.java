package com.example.sidepot.member.domain;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.work.domain.WorkTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "memberId", column = @Column(name = "staff_id"))
})
@Entity
@Table(name = "staff")
public class Staff extends Member {
    @OneToMany(mappedBy = "staff")
    private Set<Employment> employmentList = new HashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<WorkTime> workTimeList = new HashSet<>();


    private Staff(String name, String password, String phoneNum, Role role, LocalDateTime createDate) {
        super(name, password, phoneNum, role, createDate);
    }

    public static Staff registerStaff(String name, String password, String phoneNum, LocalDateTime createDate){
        return new Staff(name, password, phoneNum, Role.STAFF, createDate);
    }
}
