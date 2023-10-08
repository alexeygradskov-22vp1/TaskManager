package ru.psu.fvt.moipevm.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "cards")
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_of_board")
    private Integer idOfBoard;
    @Column(name = "card_name")
    private String name;
    @Column(name = "description")
    private String description;

}
