package com.example.team1_be.domain.Group;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private int id;
    @Column(length = 30, nullable = false)
    @Size(min = 2, max = 30)
    private String name;
    @Column(length = 13, nullable = false)
    @Size(min = 13, max = 13)
    private String telNumber;
    @Column(length = 100)
    private String address;

}
