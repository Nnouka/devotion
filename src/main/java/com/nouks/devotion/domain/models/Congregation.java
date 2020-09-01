package com.nouks.devotion.domain.models;

import com.nouks.devotion.domain.models.demographs.LocationAddress;
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
    @Column(name = "display_pic")
    private String displayPic;
    @ManyToOne
    @JoinColumn(name = "location_address_id")
    private LocationAddress locationAddress;
    @OneToMany(mappedBy = "congregation", cascade = CascadeType.ALL)
    private List<CongregationUser> congregationUsers;
}
