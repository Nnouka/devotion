package com.nouks.devotion.domain.models.pivots;

import com.nouks.devotion.domain.models.Comment;
import com.nouks.devotion.domain.models.Pulse;
import com.nouks.devotion.domain.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_commented_pulse")
@Data
public class UserCommentedPulse {
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
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Column(name = "commented_at")
    private LocalDateTime commentedAt;

    public UserCommentedPulse() {
        this.commentedAt = LocalDateTime.now();
    }
}
