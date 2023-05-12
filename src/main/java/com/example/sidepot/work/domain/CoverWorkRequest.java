package com.example.sidepot.work.domain;

import com.example.sidepot.notification.work.domain.CoverNotice;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "cover_work_Request")
public class CoverWorkRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    private WorkTime workTime;

    @ManyToOne
    @JoinColumn(name = "cover_Notice")
    private CoverNotice coverNotice;

    public CoverWorkRequest(WorkTime workTime, CoverNotice coverNotice) {
        this.workTime = workTime;
        this.coverNotice = coverNotice;
    }
}
