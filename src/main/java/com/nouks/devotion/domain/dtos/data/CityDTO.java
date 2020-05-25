package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.City;

public class CityDTO {
    private Long id;
    private String name;

    public CityDTO(City city) {
        if (city != null) {
            this.id = city.getId();
            this.name = city.getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
