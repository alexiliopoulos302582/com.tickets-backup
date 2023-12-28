package com.tickets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    public Role(String name) {
        super();
        this.name = name;

    }
}
