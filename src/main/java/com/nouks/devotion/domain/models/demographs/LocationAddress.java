package com.nouks.devotion.domain.models.demographs;

import com.nouks.devotion.domain.models.Congregation;
import com.nouks.devotion.domain.models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "location_addresses")
public class LocationAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private String address;
    private double latitude;
    private double longitude;
    @OneToMany(mappedBy = "locationAddress")
    private List<User> users;
    @OneToMany(mappedBy = "locationAddress")
    private List<Congregation> congregations;
}
