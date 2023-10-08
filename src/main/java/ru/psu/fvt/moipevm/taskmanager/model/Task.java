package ru.psu.fvt.moipevm.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_of_cards")
    private Integer idOfCards;

    @Column(name = "task_name")
    private String taskName;
    @Column(name = "description")
    private String description;

    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "status")
    private Integer status;

}
