package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "devotionals")
public class Devotional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "duration_in_days")
    private Integer durationInDays;
    @ManyToMany
    @JoinTable(
            name = "devotion_reference",
            joinColumns = @JoinColumn(name = "devotion_id"),
            inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    private List<BibleReference> bibleReferences;
    @ManyToMany
    @JoinTable(
            name = "devotion_pulse",
            joinColumns = @JoinColumn(name = "devotion_id"),
            inverseJoinColumns = @JoinColumn(name = "pulse_id")
    )
    private List<Pulse> pulses;
}
