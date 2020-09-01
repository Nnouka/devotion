package com.nouks.devotion.controllers;

import com.nouks.devotion.domain.dtos.data.CountryWithRegionsDTO;
import com.nouks.devotion.domain.dtos.data.RegionWithCitiesDTO;
import com.nouks.devotion.domain.dtos.data.SimpleCountryInfoDTO;
import com.nouks.devotion.domain.services.interfaces.PublicResourceService;
import com.nouks.devotion.utils.FileHelper;
import com.nouks.devotion.utils.FileHelperConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * author nouks
 */
@RestController
@RequestMapping("/api/public/resources")
public class PublicResourcesController {
    private Logger logger = LoggerFactory.getLogger(PublicResourcesController.class);
    private FileHelper fileHelper;
    private PublicResourceService publicResourceService;
    private FileHelperConfig fileHelperConfig;

    @Autowired
    public PublicResourcesController(PublicResourceService publicResourceService) {
        this.publicResourceService = publicResourceService;
    }

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }
    @Autowired
    public void setFileHelperConfig(FileHelperConfig fileHelperConfig) {
        this.fileHelperConfig = fileHelperConfig;
    }

    @GetMapping("/logos/{fileName:.+}")
    public void loadLogo(@PathVariable String fileName, HttpServletResponse response) {
        Resource resource = fileHelper.loadFileAsResource(fileName);
        try{
            StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
        } catch (IOException ex) {
            logger.info("Could not Load File");
        }
    }
    @GetMapping("/widgets/{fileId}")
    public void loadWidget(@PathVariable String fileId, HttpServletResponse response) {
//        logger.info("file helper: {}", fileHelperConfig.getRootFolder());
        fileHelper.setFileStorageLocation("widgets", fileHelperConfig);
        Resource resource = fileHelper.loadFileAsResource(fileId + ".js");
        try{
            StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
        } catch (IOException ex) {
            logger.info("Could not Load Specified Widget");
        }
    }

    @GetMapping("/countries")
    public ResponseEntity<List<SimpleCountryInfoDTO>> listAllCountries() {
        return ResponseEntity.ok(publicResourceService.listAllCountries());
    }
    @GetMapping("/countries/{countryId}/regions")
    public ResponseEntity<CountryWithRegionsDTO> getCountryWithRegions(
            @PathVariable("countryId") Long countryId) {
        return ResponseEntity.ok(publicResourceService.getCountryRegions(countryId));
    }
    @GetMapping("/countries/regions/{regionId}/cities")
    public ResponseEntity<RegionWithCitiesDTO> getRegionWithCities(
            @PathVariable("regionId") Long regionId ) {
        return ResponseEntity.ok(publicResourceService.getRegionCities(regionId));
    }
}
