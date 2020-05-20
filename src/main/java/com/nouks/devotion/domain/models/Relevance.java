package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "relevance")
@Data
public class Relevance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @Column(name = "featured_image")
    private String featuredImage;
}
