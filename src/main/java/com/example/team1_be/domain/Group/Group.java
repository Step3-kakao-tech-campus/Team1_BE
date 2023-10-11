package com.example.team1_be.domain.Group;

import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name="groups")
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    @NotNull
    private String name;

    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Column(unique = true)
    private String businessNumber;

    @Size(max = 100)
    @NotNull
    private String address;

    @Builder
    public Group(Long id, String name, String phoneNumber, String businessNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.businessNumber = businessNumber;
        this.address = address;
    }
}