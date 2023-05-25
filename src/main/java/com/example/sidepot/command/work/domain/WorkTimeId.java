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
public class WorkTimeId {

    @Column(name = "work_time_id")
    private Long workTimeId;

    public WorkTimeId(Long workTimeId) {
        this.workTimeId = Objects.requireNonNull(workTimeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkTimeId that = (WorkTimeId) o;
        return Objects.equals(workTimeId, that.workTimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workTimeId);
    }
}
