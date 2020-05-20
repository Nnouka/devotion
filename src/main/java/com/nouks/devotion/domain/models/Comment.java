package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // long text
    @Lob
    @Size(max = 65535)
    @Column(name = "text")
    private String text;
}
