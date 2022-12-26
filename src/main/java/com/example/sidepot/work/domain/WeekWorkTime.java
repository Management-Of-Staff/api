package com.example.sidepot.work.domain;

import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "week_work_time")
@NoArgsConstructor
public class WeekWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String day;

    private LocalDate startTime;

    private LocalDate endTime;

    @CreationTimestamp
    @Column(name = "create_time",
            updatable = false,
            nullable = false)
    private LocalDateTime createTime;

}
