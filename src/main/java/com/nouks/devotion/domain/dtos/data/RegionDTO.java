package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.Region;

import java.util.List;
import java.util.stream.Collectors;

public class RegionDTO {
    private Long id;
    private String name;

    public RegionDTO() {
    }

    public RegionDTO(Region region) {
        if (region != null) {
            this.id = region.getId();
            this.name = region.getName();
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
