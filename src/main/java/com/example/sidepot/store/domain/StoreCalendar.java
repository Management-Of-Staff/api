package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Table(name = "store_calendar")
@NoArgsConstructor
@Entity
public class StoreCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_calendar_id")
    private Long storeCalendarId;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "schedule_date_time")
    private LocalDateTime scheduleDateTime;

}
