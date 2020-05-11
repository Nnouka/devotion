package com.nouks.devotion.domain.models;

import com.nouks.devotion.domain.models.embeds.LocationAddress;
import com.nouks.devotion.domain.models.pivots.CongregationUser;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "congregations")
public class Congregation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private LocationAddress locationAddress;
    @OneToMany(mappedBy = "congregation", cascade = CascadeType.ALL)
    private List<CongregationUser> congregationUsers;
}
