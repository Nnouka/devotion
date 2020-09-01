package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.demographs.City;
import com.nouks.devotion.domain.models.demographs.LocationAddress;
import com.nouks.devotion.domain.models.demographs.Region;

public class FullAddressDTO {
    private SimpleCountryInfoDTO country;
    private RegionDTO region;
    private CityDTO city;
    private String address;

    public FullAddressDTO(LocationAddress locationAddress) {
        if (locationAddress != null) {
            City city1 = locationAddress.getCity();
            this.city = new CityDTO(city1);
            if (city1 != null) {
                Region region1 = city1.getRegion();
                this.region = new RegionDTO(region1);
                if (region1 != null) {
                    this.country = new SimpleCountryInfoDTO(region1.getCountry());
                }
            }
            this.address = locationAddress.getAddress();
        }
    }

    public FullAddressDTO() {
    }

    public SimpleCountryInfoDTO getCountry() {
        return country;
    }

    public void setCountry(SimpleCountryInfoDTO country) {
        this.country = country;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
