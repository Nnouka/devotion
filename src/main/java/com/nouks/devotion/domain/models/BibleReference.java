package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "bible_references")
public class BibleReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String book;
    private Long chapter;
    private Long verse;
    @ManyToMany
    @JoinTable(
            name = "devotion_reference",
            joinColumns = @JoinColumn(name = "reference_id"),
            inverseJoinColumns = @JoinColumn(name = "devotion_id")
    )
    private List<Devotional> devotionals;
    @ManyToMany
    @JoinTable(
            name = "pulse_reference",
            joinColumns = @JoinColumn(name = "reference_id"),
            inverseJoinColumns = @JoinColumn(name = "pulse_id")
    )
    private List<Pulse> pulses;
}
