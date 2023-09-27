package com.example.team1_be.domain.Group;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "Group_tb")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(length = 50, nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @Column(length = 13, nullable = false)
    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    private String address;

    @Builder
    public Group(int id, String name, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
