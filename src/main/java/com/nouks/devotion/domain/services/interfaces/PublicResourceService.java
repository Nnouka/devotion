package com.nouks.devotion.domain.services.interfaces;

import com.nouks.devotion.domain.dtos.data.SimpleCountryInfoDTO;

import java.util.List;

public interface PublicResourceService {
    List<SimpleCountryInfoDTO> listAllCountries();
}
