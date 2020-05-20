package com.nouks.devotion.domain.models.pivots;

import com.nouks.devotion.domain.models.Pulse;
import com.nouks.devotion.domain.models.Relevance;
import com.nouks.devotion.domain.models.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pulse_relevant_to_user")
@Data
public class PulseRelevantToUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pulse_id")
    private Pulse pulse;
    @ManyToOne
    @JoinColumn(name = "relevance_id")
    private Relevance relevance;
    @Column(name = "relevant_at")
    private LocalDateTime relevantAt;

    public PulseRelevantToUser() {
        this.relevantAt = LocalDateTime.now();
    }
}
