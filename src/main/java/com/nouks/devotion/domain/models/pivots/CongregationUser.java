package com.nouks.devotion.domain.models.pivots;

import com.nouks.devotion.domain.models.Congregation;
import com.nouks.devotion.domain.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "congregation_user")
@Data
public class CongregationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "congregation_id")
    private Congregation congregation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "is_minister")
    private boolean minister;

    public CongregationUser(Congregation congregation, User user, boolean minister) {
        this.congregation = congregation;
        this.user = user;
        this.minister = minister;
    }
}
