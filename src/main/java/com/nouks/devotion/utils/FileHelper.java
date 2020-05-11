package com.nouks.devotion.utils;

import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileHelper {
    private Path fileStorageLocation;
    private Logger logger = LoggerFactory.getLogger(FileHelper.class);
    private FileHelperConfig fileHelperConfig;

    @Autowired
    public FileHelper(FileHelperConfig fileHelperConfig) {
        try {
            this.fileStorageLocation = Paths.get(fileHelperConfig.getLogoFolder())
                    .toAbsolutePath().normalize();
            logger.info(fileStorageLocation.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFileStorageLocation(String folder) {
        folder = resolveFolder(folder);
        this.fileStorageLocation = Paths.get(folder)
                .toAbsolutePath().normalize();
        logger.info(fileStorageLocation.toString());
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            logger.info("file path: {}", filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ApiException(ErrorCodes.FILE_NOT_FOUND.getMessage() + fileName, HttpStatus.NOT_FOUND,
                        ErrorCodes.FILE_NOT_FOUND.toString(), "");
            }
        } catch (MalformedURLException ex) {
          throw new ApiException(ErrorCodes.FILE_NOT_FOUND.getMessage() + fileName, HttpStatus.NOT_FOUND,
            ErrorCodes.FILE_NOT_FOUND.toString(), "");
        }
    }
    public String resolveFolder(String folder) {
        folder = folder.trim();
        return folder.startsWith("/") ? fileHelperConfig.getRootFolder() + folder : fileHelperConfig.getRootFolder() + "/" + folder;
    }
}
