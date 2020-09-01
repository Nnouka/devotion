package com.nouks.devotion.domain.services;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import com.nouks.devotion.domain.models.Congregation;
import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.models.pivots.CongregationUser;
import com.nouks.devotion.domain.repositories.CongregationRepository;
import com.nouks.devotion.domain.repositories.CongregationUserRepository;
import com.nouks.devotion.domain.services.interfaces.CongregationService;
import com.nouks.devotion.domain.services.interfaces.UserService;
import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CongregationServiceImpl implements CongregationService {
    private CongregationRepository congregationRepository;
    private UserService userService;
    private CongregationUserRepository congregationUserRepository;

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
        Optional<Congregation> optional = congregationRepository.findById(id);
        if (!optional.isPresent())
            throw new ApiException(ErrorCodes.RESOURCE_NOT_FOUND.getMessage(),
                HttpStatus.NOT_FOUND,
                ErrorCodes.RESOURCE_NOT_FOUND.toString());
        return new CongregationResponseDTO(optional.get());
    }
}
