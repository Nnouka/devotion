package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;

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
}
