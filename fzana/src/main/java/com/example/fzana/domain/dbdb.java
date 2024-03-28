package com.example.fzana.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class dbdb {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String name;
}
