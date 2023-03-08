package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Access(AccessType.FIELD)
@Embeddable
public class StaffId {

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_phone_number")
    private String staffPhoneNumber;

    protected StaffId() {}

    public StaffId(Long staffId, String staffPhoneNumber) {
        this.staffId = staffId;
        this.staffPhoneNumber = staffPhoneNumber;
    }

    public static StaffId from(Long id, String phoneNum){return new StaffId(id, phoneNum);}

    public Long getStaffId() {
        return staffId;
    }

    public String getStaffPhoneNumber() {
        return staffPhoneNumber;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        StaffId staffId = (StaffId) obj;
        return Objects.equals(this.staffId, staffId.staffId)
                && Objects.equals(this.staffPhoneNumber, staffId.staffPhoneNumber);
    }
}
