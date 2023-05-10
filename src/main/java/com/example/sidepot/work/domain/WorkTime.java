package com.example.sidepot.work.domain;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.app.WorkReadService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "work_time")
@NoArgsConstructor
public class WorkTime extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_time_id")
    private Long workTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "workTime", cascade = CascadeType.ALL)
    private List<CoverWork> coverWorkList = new ArrayList<>();

    @Builder
    private WorkTime(Long workTimeId, Staff staff, Store store,
                    LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        this.workTimeId = workTimeId;
        this.staff = staff;
        this.store = store;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    public List<WorkReadService.CoveredWork> getCoverWorkBetween(LocalDate startDate, LocalDate endDate){
        List<WorkReadService.CoveredWork> coverWorkList = new ArrayList<>();
        for(CoverWork coverWork : this.coverWorkList){
            if(coverWork.getCoverDate().isAfter(startDate) && coverWork.getCoverDate().isBefore(endDate.plusDays(1))){
                coverWorkList.add(new WorkReadService.CoveredWork(coverWork));
            }
        }
        return coverWorkList;
    }
}
