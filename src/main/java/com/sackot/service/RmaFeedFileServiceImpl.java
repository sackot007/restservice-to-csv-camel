package com.sackot.service;

import com.sackot.routeconfig.RmaFeedRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tkmaw80 on 7/31/16.
 */
@Component
public class RmaFeedFileServiceImpl implements RmaFeedFileService{

    @Value("${rmafeed.service.url}")
    private String rmafeedServiceURL;

    @Value("${rmafeed.service.appkey}")
    private String rmafeedServiceAppKey;

    @Value("${rmafeed.file.dir.location}")
    private String rmaFileDirLoc;


    @Override
    public String generateFile() throws Exception {
        String generatedFilePath = null;
        RmaFeedRouteBuilder rmaFeedRouteBuilder = new RmaFeedRouteBuilder(rmafeedServiceURL, rmafeedServiceAppKey, rmaFileDirLoc);
        CamelContext camelContext = new DefaultCamelContext();
        try {

            camelContext.addRoutes(rmaFeedRouteBuilder);

            camelContext.start();

            ProducerTemplate template = camelContext.createProducerTemplate();
            Exchange exchange = template.request("direct:start", null);

            Object filePathObj = exchange.getIn().getHeader("CamelFileNameProduced");
            generatedFilePath = filePathObj!=null?filePathObj.toString():null;
            logger.info("File produced and file path is "+generatedFilePath);

            Thread.sleep(1000);
            camelContext.stop();
        } catch (Exception e) {
            logger.error("Error while executing the route",e);
            throw e;
        }

        return rmaFileDirLoc;
    }
}
