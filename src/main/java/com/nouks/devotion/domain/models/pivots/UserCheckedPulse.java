package com.nouks.devotion.domain.models.pivots;

import com.nouks.devotion.domain.models.Pulse;
import com.nouks.devotion.domain.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_checked_pulse")
@Data
public class UserCheckedPulse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pulse_id")
    private Pulse pulse;
    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    public UserCheckedPulse() {
        this.checkedAt = LocalDateTime.now();
    }

}
