package com.example.sidepot.member.domain;

import com.example.sidepot.member.dto.EmploymentUpdateDto.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "employment")
@Entity
public class Employment {

    @Id @GeneratedValue
    @Column(name = "employment_id")
    private Long employmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToMany(mappedBy = "employment")
    private List<WeekWorkTime> weekWorkTimeList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private WorkingStatus workingStatus;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "hourly_wage")
    private Long hourlyWage;

    @Column(name = "rank")
    private Rank rank;

    @Builder
    public Employment(Store store, Staff staff, List<WeekWorkTime> weekWorkTimeList,
                      WorkingStatus workingStatus, String staffName, Long hourlyWage, Rank rank) {
        this.store = store;
        this.staff = staff;
        this.weekWorkTimeList = weekWorkTimeList;
        this.workingStatus = workingStatus;
        this.staffName = staffName;
        this.hourlyWage = hourlyWage;
        this.rank = rank;
    }

    public static Employment of(Store store, Staff staff, String staffName){
        return new Employment().builder()
                .hourlyWage(0L)
                .rank(Rank.ETC)
                .staff(staff)
                .store(store)
                .workingStatus(WorkingStatus.INIT)
                .build();
    }

    public void updateRankAndWage(UpdateRankAndWageRequest updateRankAndWageRequest) {
        this.rank = updateRankAndWageRequest.getRank();
        this.hourlyWage = updateRankAndWageRequest.getHourlyWage();
    }
}
