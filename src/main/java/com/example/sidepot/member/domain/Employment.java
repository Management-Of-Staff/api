package com.example.sidepot.member.domain;

import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "employment")
@Entity
public class Employment {

    @Id @GeneratedValue
    @Column(name = "employment_id")
    private Long employmentId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Enumerated(EnumType.STRING)
    private WorkingStatus workingStatus;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "hourly_wage")
    private Long hourlyWage;

    @Column(name = "rank")
    private Rank rank;

    public Employment(Store store, Staff staff, String staffName) {
        this.store = store;
        this.staff = staff;
        this.staffName = staffName;
    }

    public static Employment of(Store store, Staff staff, String staffName){
       return new Employment(store, staff, staffName);
    }
}
