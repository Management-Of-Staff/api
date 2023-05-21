package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RequestedStaffId {

    private Long requestedStaffId;
    private String requestedStaffName;
    private String uuid;

    public RequestedStaffId(Long requestedStaffId, String requestedStaffName, String uuid) {
        this.requestedStaffId = requestedStaffId;
        this.requestedStaffName = requestedStaffName;
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestedStaffId that = (RequestedStaffId) o;
        return Objects.equals(requestedStaffId, that.requestedStaffId)
                && Objects.equals(requestedStaffName, that.requestedStaffName)
                && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestedStaffId, requestedStaffName, uuid);
    }
}
