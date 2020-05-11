package com.nouks.devotion.controllers;

import com.nouks.devotion.utils.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author nouks
 */
@RestController
@RequestMapping("/api/public/resources")
public class PublicResourcesController {
    private Logger logger = LoggerFactory.getLogger(PublicResourcesController.class);
    private FileHelper fileHelper;

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
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
}
