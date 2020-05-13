package com.nouks.devotion.domain.models.embeds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class LocationAddress {
    private String country;
    private String region;
    private String city;
    private String address;

    public LocationAddress(String country, String region, String city, String address) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.address = address;
    }
    public LocationAddress(){

    }
}
