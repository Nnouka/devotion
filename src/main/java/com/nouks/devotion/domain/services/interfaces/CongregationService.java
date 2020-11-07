package com.nouks.devotion.domain.services.interfaces;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.requests.UpdateLocationDto;
import com.nouks.devotion.domain.dtos.requests.UpdateNameDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CongregationService {
    CongregationResponseDTO createCongregation(CreateCongregationDTO dto);
    CongregationResponseDTO getCongregationById(Long id);
    List<CongregationResponseDTO> getAvailableCongregations();
    CongregationResponseDTO updateCongregationLocation(Long id, UpdateLocationDto dto);
    CongregationResponseDTO updateCongregationName(Long id, UpdateNameDTO dto);
}
