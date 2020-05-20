package com.nouks.devotion.domain.models;

import com.nouks.devotion.domain.models.pivots.PulseRelevantToUser;
import com.nouks.devotion.domain.models.pivots.UserCheckedPulse;
import com.nouks.devotion.domain.models.pivots.UserCommentedPulse;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Table(name = "pulses")
public class Pulse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String exhortation;
    @ManyToMany
    @JoinTable(
        name = "pulse_reference",
        joinColumns = @JoinColumn(name = "pulse_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    private List<BibleReference> bibleReferences;
    @ManyToMany
    @JoinTable(
            name = "pulse_prayer_point",
            joinColumns = @JoinColumn(name = "pulse_id"),
            inverseJoinColumns = @JoinColumn(name = "prayer_point_id")
    )
    private List<PrayerPoint> prayerPoints;
    @ManyToMany
    @JoinTable(
            name = "devotion_pulse",
            joinColumns = @JoinColumn(name = "pulse_id"),
            inverseJoinColumns = @JoinColumn(name = "devotion_id")
    )
    private List<Devotional> devotionals;
    @OneToMany(mappedBy = "pulse", cascade = CascadeType.ALL)
    private List<UserCheckedPulse> userCheckedPulses;
    @OneToMany(mappedBy = "pulse", cascade = CascadeType.ALL)
    private List<UserCommentedPulse> userCommentedPulses;
    @OneToMany(mappedBy = "pulse", cascade = CascadeType.ALL)
    private List<PulseRelevantToUser> pulseRelevantToUsers;
}
