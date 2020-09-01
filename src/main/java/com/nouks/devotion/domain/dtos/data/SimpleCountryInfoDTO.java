package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.demographs.Country;

public class SimpleCountryInfoDTO {
    private Long id;
    private String name;
    private String lang;
    private String locale;

    public SimpleCountryInfoDTO(Country country) {
        if (country != null) {
            this.id = country.getId();
            this.name = country.getName();
            this.lang = country.getLang();
            this.locale = country.getLocale();
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
}
