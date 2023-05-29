package com.example.sidepot.command.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RequestedStaff {
    @Column(name = "requested_staff_id")
    private Long id;
    @Column(name = "requested_staff_name")
    private String name;
    @Column(name = "requested_staff_token")
    private String token;

    public RequestedStaff(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RequestedStaff(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestedStaff that = (RequestedStaff) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
