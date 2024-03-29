package com.example.fzana;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ddbdb {
    @Id @GeneratedValue
    private Long id;
    @Column
    private String name;
}
