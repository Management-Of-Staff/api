package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Table(name = "todo_list_detail")
@NoArgsConstructor
@Entity
public class TodoListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_detail_id")
    private Long todoListDetailId;

    @Column(name = "todo_list_detail")
    private String todoListDetail;
}