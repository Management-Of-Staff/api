package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AcceptedStaffId {

    private Long acceptedStaffId;
    private String acceptedStaffName;

    public AcceptedStaffId(Long acceptedStaffId, String acceptedStaffName) {
        this.acceptedStaffId = acceptedStaffId;
        this.acceptedStaffName = acceptedStaffName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcceptedStaffId that = (AcceptedStaffId) o;
        return Objects.equals(acceptedStaffId, that.acceptedStaffId) && Objects.equals(acceptedStaffName, that.acceptedStaffName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceptedStaffId, acceptedStaffName);
    }
}
