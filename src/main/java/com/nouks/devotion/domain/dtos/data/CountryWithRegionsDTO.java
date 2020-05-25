package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.Country;

import java.util.List;
import java.util.stream.Collectors;

public class CountryWithRegionsDTO {
    private Long id;
    private String name;
    private String lang;
    private String locale;
    private List<RegionDTO> regions;

    public CountryWithRegionsDTO(Country country) {
        if (country != null) {
            this.id = country.getId();
            this.name = country.getName();
            this.lang = country.getLang();
            this.locale = country.getLocale();
            this.regions = country.getRegions().stream().map(
                    RegionDTO::new
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<RegionDTO> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }
}
