package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Table(name = "manager")
@NoArgsConstructor
@Entity
public class ScheduleManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_mangaer_id")
    private Long scheduleManagerId;

    @Column(name = "name")
    private String name;
}
