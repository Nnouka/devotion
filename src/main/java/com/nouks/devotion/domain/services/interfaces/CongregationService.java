package com.nouks.devotion.domain.services.interfaces;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CongregationService {
    CongregationResponseDTO createCongregation(CreateCongregationDTO dto);
    CongregationResponseDTO getCongregationById(Long id);
}
