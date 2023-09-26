package com.example.team1_be.domain.Group;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
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

    @Builder
    public Group(int id, String name, String telNumber, String address) {
        this.id = id;
        this.name = name;
        this.telNumber = telNumber;
        this.address = address;
    }
}
