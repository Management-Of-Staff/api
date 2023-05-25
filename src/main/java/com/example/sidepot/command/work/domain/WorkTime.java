package com.example.sidepot.command.work.domain;


import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.store.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "work_time")
@NoArgsConstructor
public class WorkTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_time_id")
    private Long workTimeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalTime endTime;

    @NotNull
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "is_deleted")
    private Boolean isDeleted; //삭제처리

    @Builder //mock 빌더
    public WorkTime(Long workTimeId, Staff staff,Store store, boolean isDeleted,
                   LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        this.workTimeId = workTimeId;
        this.staff = staff;
        this.store = store;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.isDeleted = isDeleted;
    }

    public WorkTime(Staff staff, Store store, LocalTime startTime,
                    LocalTime endTime, DayOfWeek dayOfWeek) {
        this.staff = staff;
        this.store = store;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.isDeleted = false;
    }

    public boolean isOverlappedWithCover(CoverDateTime coverDateTime){
        return (this.dayOfWeek.equals(coverDateTime.getCoverDate().getDayOfWeek()))
                && (this.startTime.isBefore(coverDateTime.getEndTime()))
                && ((this.endTime.isAfter(coverDateTime.getStartTime())));
    }


    public void delete(){
        this.isDeleted = true;
    }
}
