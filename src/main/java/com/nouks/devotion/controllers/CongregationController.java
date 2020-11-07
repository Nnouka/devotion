package com.nouks.devotion.controllers;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.requests.UpdateLocationDto;
import com.nouks.devotion.domain.dtos.requests.UpdateNameDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import com.nouks.devotion.domain.services.interfaces.CongregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/protected/congregations")
public class CongregationController {
    private CongregationService congregationService;

    @Autowired
    public CongregationController(CongregationService congregationService) {
        this.congregationService = congregationService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CongregationResponseDTO> createCongregation(@Valid @RequestBody CreateCongregationDTO dto) {
        return ResponseEntity.ok(congregationService.createCongregation(dto));
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CongregationResponseDTO> getCongregationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(congregationService.getCongregationById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<List<CongregationResponseDTO>> listCongregations() {
        return ResponseEntity.ok(congregationService.getAvailableCongregations());
    }

    @PostMapping("/{id}/location/update")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CongregationResponseDTO> updateCongregationLocation(@PathVariable("id") Long id,
                                                                       @RequestBody @Valid UpdateLocationDto dto) {
        return ResponseEntity.ok(congregationService.updateCongregationLocation(id, dto));
    }

    @PostMapping("/{id}/name/update")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CongregationResponseDTO> updateCongregationName(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateNameDTO dto
            ) {
        return ResponseEntity.ok(congregationService.updateCongregationName(id, dto));
    }
}
