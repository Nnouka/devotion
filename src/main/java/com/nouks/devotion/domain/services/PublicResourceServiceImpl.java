package com.nouks.devotion.domain.services;

import com.nouks.devotion.domain.dtos.data.CountryWithRegionsDTO;
import com.nouks.devotion.domain.dtos.data.RegionWithCitiesDTO;
import com.nouks.devotion.domain.dtos.data.SimpleCountryInfoDTO;
import com.nouks.devotion.domain.models.demographs.Country;
import com.nouks.devotion.domain.models.demographs.Region;
import com.nouks.devotion.domain.repositories.CountryRepository;
import com.nouks.devotion.domain.repositories.RegionRepository;
import com.nouks.devotion.domain.services.interfaces.PublicResourceService;
import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicResourceServiceImpl implements PublicResourceService {
    private CountryRepository countryRepository;
    private RegionRepository regionRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Autowired
    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<SimpleCountryInfoDTO> listAllCountries() {
        return countryRepository.findAll().stream().map(
                SimpleCountryInfoDTO::new
        ).collect(Collectors.toList());
    }

    @Override
    public CountryWithRegionsDTO getCountryRegions(long id) {
        Optional<Country> optional = countryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ApiException(ErrorCodes.RESOURCE_NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND, ErrorCodes.RESOURCE_NOT_FOUND.toString());
        }
        return new CountryWithRegionsDTO(optional.get());
    }

    @Override
    public RegionWithCitiesDTO getRegionCities(long id) {
        Optional<Region> optional = regionRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ApiException(ErrorCodes.RESOURCE_NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND, ErrorCodes.RESOURCE_NOT_FOUND.toString());
        }
        return new RegionWithCitiesDTO(optional.get());
    }
}
