package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.City;
import com.nouks.devotion.domain.models.Region;

import java.util.List;
import java.util.stream.Collectors;

public class RegionWithCitiesDTO {
    private Long id;
    private String name;
    private List<CityDTO> cities;
    public RegionWithCitiesDTO(Region region) {
        if (region != null) {
            this.id = region.getId();
            this.name = region.getName();
            this.cities = region.getCities().stream().map(
                    CityDTO::new
            ).collect(Collectors.toList());
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

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }
}
