package com.nouks.devotion.domain.services;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.requests.UpdateLocationDto;
import com.nouks.devotion.domain.dtos.requests.UpdateNameDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import com.nouks.devotion.domain.models.Congregation;
import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.models.demographs.City;
import com.nouks.devotion.domain.models.demographs.LocationAddress;
import com.nouks.devotion.domain.models.pivots.CongregationUser;
import com.nouks.devotion.domain.repositories.CityRepository;
import com.nouks.devotion.domain.repositories.CongregationRepository;
import com.nouks.devotion.domain.repositories.CongregationUserRepository;
import com.nouks.devotion.domain.repositories.LocationAddressRepository;
import com.nouks.devotion.domain.services.interfaces.CongregationService;
import com.nouks.devotion.domain.services.interfaces.UserService;
import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CongregationServiceImpl implements CongregationService {
    private CongregationRepository congregationRepository;
    private UserService userService;
    private CongregationUserRepository congregationUserRepository;
    private CityRepository cityRepository;
    private LocationAddressRepository locationAddressRepository;

    @Autowired
    public CongregationServiceImpl(CongregationRepository congregationRepository) {
        this.congregationRepository = congregationRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCongregationUserRepository(CongregationUserRepository congregationUserRepository) {
        this.congregationUserRepository = congregationUserRepository;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setLocationAddressRepository(LocationAddressRepository locationAddressRepository) {
        this.locationAddressRepository = locationAddressRepository;
    }

    @Override
    public CongregationResponseDTO createCongregation(CreateCongregationDTO dto) {
        User minister = userService.getCurrentAuthUser();
        Congregation congregation = new Congregation();
        congregation.setName(dto.getName());
        congregation.setLocationAddress(minister.getLocationAddress());
        // persist congregation
        congregationRepository.save(congregation);
        // add minister to congregation
        congregation.setCongregationUsers(Collections.singletonList(
                congregationUserRepository.save(new CongregationUser(congregation, minister, true))
        ));
        return new CongregationResponseDTO(congregation);
    }

    @Override
    public CongregationResponseDTO getCongregationById(Long id) {
        return new CongregationResponseDTO(findCongregationById(id));
    }

    @Override
    public List<CongregationResponseDTO> getAvailableCongregations() {
        return congregationRepository.findAll()
                .stream().map(CongregationResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CongregationResponseDTO updateCongregationLocation(Long id, UpdateLocationDto dto) {
        Congregation congregation = findCongregationById(id);
        // ensure only minister can update congregation name
        validateMinister(congregation);
        LocationAddress locationAddress = congregation.getLocationAddress();
        if (locationAddress == null) locationAddress = new LocationAddress();
        City city = findCityById(dto.getCityId());
        locationAddress.setCity(city);
        locationAddress.setAddress(dto.getAddress());
        locationAddress.setLatitude(dto.getLatitude());
        locationAddress.setLongitude(dto.getLongitude());
        locationAddressRepository.save(locationAddress);
        congregation.setLocationAddress(locationAddress);
        congregationRepository.save(congregation);
        return new CongregationResponseDTO(congregation);
    }

    @Override
    public CongregationResponseDTO updateCongregationName(Long id, UpdateNameDTO dto) {
        Congregation congregation = findCongregationById(id);
        // ensure only minister can update congregation name
        validateMinister(congregation);
        congregation.setName(dto.getName());
        congregationRepository.save(congregation);
        return new CongregationResponseDTO(congregation);
    }

    private Congregation findCongregationById(Long id) {
        Optional<Congregation> optional = congregationRepository.findById(id);
        if (!optional.isPresent())
            throw new ApiException("Congregation: " + ErrorCodes.RESOURCE_NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND,
                    ErrorCodes.RESOURCE_NOT_FOUND.toString());
        return optional.get();
    }

    private Boolean validateMinister(Congregation congregation) {
        User user = userService.getCurrentAuthUser();
        CongregationUser minister = getMinister(congregation.getId());
        if (minister == null ||
                minister.getUser() == null ||
                !minister.getUser().getId().equals(user.getId())) {
            throw new ApiException(
                    ErrorCodes.UNAUTHORIZED_ACTION.getMessage(),
                    HttpStatus.FORBIDDEN,
                    ErrorCodes.UNAUTHORIZED_ACTION.toString()
            );
        }
        return true;
    }

    private City findCityById(Long id) {
        Optional<City> optional = cityRepository.findById(id);
        if (!optional.isPresent())
            throw new ApiException("City: " + ErrorCodes.RESOURCE_NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND,
                    ErrorCodes.RESOURCE_NOT_FOUND.toString());
        return optional.get();
    }

    private CongregationUser getMinister(Long congregationId) {
        Optional<CongregationUser> optional = congregationUserRepository.findFirstByCongregation_IdAndMinister(
                congregationId, true
        );
        return optional.orElse(null);
    }
}
