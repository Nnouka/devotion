package com.nouks.devotion.controllers;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.dtos.responses.CongregationResponseDTO;
import com.nouks.devotion.domain.services.interfaces.CongregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
