package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.member.domain.Staff;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AcceptedStaff {
    @Column(name = "accepted_staff_id")
    private Long id;
    @Column(name = "accepted_staff_name")
    private String name;

    @Column(name = "accepted_staff_token")
    private String token; ///안씀 // 검색할 거임

    public AcceptedStaff(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public AcceptedStaff(Staff staff) {
        this.id = staff.getMemberId();
        this.name = staff.getMemberName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcceptedStaff that = (AcceptedStaff) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
