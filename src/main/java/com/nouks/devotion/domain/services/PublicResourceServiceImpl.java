package com.nouks.devotion.domain.services;

import com.nouks.devotion.domain.dtos.data.SimpleCountryInfoDTO;
import com.nouks.devotion.domain.repositories.CountryRepository;
import com.nouks.devotion.domain.services.interfaces.PublicResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicResourceServiceImpl implements PublicResourceService {
    private CountryRepository countryRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<SimpleCountryInfoDTO> listAllCountries() {
        return countryRepository.findAll().stream().map(
                country -> new SimpleCountryInfoDTO(country.getId(), country.getName(),
                        country.getLang(), country.getLocale())
        ).collect(Collectors.toList());
    }
}
