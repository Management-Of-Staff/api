package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "manager")
@NoArgsConstructor
public class ScheduleManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_mangaer_id")
    private Long scheduleManagerId;

    @Column(name = "name")
    private String name;
}
