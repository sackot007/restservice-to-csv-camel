package com.sackot.controller;

import com.sackot.service.RmaFeedFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tkmaw80 on 7/31/16.
 */
@RestController
public class RmaFeedFileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RmaFeedFileService rmaFeedFileService;

    @RequestMapping(value = "/generateRmaFeed", method = RequestMethod.GET)
    public ResponseEntity generateRmaFeed() {
        logger.info("Inside Controller generateRmaFeed method");
        String dirPath = null;
        try {
            dirPath = rmaFeedFileService.generateFile();
        } catch (Exception e) {
            logger.error("Error while generating file",e);
            return new ResponseEntity<String>("Error while generating file.", HttpStatus.BAD_REQUEST);
        }
        if (dirPath == null || "".equals(dirPath.trim())) {
            logger.error("dirPath is empty"+dirPath);
            return new ResponseEntity<String>("Error while generating file. File Name is null or empty", HttpStatus.BAD_REQUEST);
        }
        FileSystemResource fileSystemResource = new FileSystemResource(dirPath);
        try {
            return ResponseEntity
                    .ok()
                    .contentLength(fileSystemResource.contentLength())
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(new InputStreamResource(fileSystemResource.getInputStream()));
        } catch (IOException e) {
            logger.error("Error while downloading file. File Name is "+dirPath,e);
            return new ResponseEntity<String>("Error while downloading file from "+dirPath, HttpStatus.BAD_REQUEST);
        }

    }
}
