package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @OneToMany(mappedBy = "city")
    private List<LocationAddress> locationAddresses;
}
