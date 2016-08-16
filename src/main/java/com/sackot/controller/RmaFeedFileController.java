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
    public ResponseEntity<String> generateRmaFeed() {
        logger.info("Inside Controller generateRmaFeed method");
        String dirPath = rmaFeedFileService.generateFile();
        return new ResponseEntity<String>("File is generated in "+dirPath, HttpStatus.OK);
    }
}
