package ru.psu.fvt.moipevm.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "boards")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_of_user")
    private Integer idOfUser;
    @Column(name = "board_name")
    private String name;
    @Column(name = "description")
    private String description;

}
