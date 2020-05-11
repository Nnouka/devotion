package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "prayer_points")
public class PrayerPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issue;
    private String answer;
    @ManyToMany
    @JoinTable(
            name = "pulse_prayer_point",
            joinColumns = @JoinColumn(name = "prayer_point_id"),
            inverseJoinColumns = @JoinColumn(name = "pulse_id")
    )
    private List<Pulse> pulses;
}
