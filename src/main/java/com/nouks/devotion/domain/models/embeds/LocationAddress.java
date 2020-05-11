package com.nouks.devotion.domain.models.embeds;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
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
}
