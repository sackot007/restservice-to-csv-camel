package com.sackot.routeconfig;

import com.sackot.model.RmaFeed;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

/**
 * Created by tkmaw80 on 7/26/16.
 */
public class RmaFeedRouteBuilder extends RouteBuilder {
    private String rmafeedServiceURL;

    private String rmafeedServiceAppKey;

    private String rmaFileDirLoc;

    public RmaFeedRouteBuilder(String rmafeedServiceURL, String rmafeedServiceAppKey, String rmaFileDirLoc) {
        super();
        this.rmafeedServiceURL = rmafeedServiceURL;
        this.rmafeedServiceAppKey = rmafeedServiceAppKey;
        this.rmaFileDirLoc = rmaFileDirLoc;
    }


    @Override
    public void configure() throws Exception {

        JacksonDataFormat listJacksonDataFormat = new JacksonDataFormat();
        listJacksonDataFormat.setUnmarshalType(RmaFeed.class);
        listJacksonDataFormat.setUseList(true);

        DataFormat bindyCsvDataFormat = new BindyCsvDataFormat(RmaFeed.class);

//        <bean id="httpAuth" class="org.apache.camel.component.http.HttpConfiguration">
//        <property name="authMethod" value="Basic"/>
//        <property name="authUsername" value="user"/>
//        <property name="authPassword" value="password"/>
//        </bean>
//
//        <bean id="http" class="org.apache.camel.component.http.HttpComponent">
//        <property name="camelContext" ref="camel"/>
//        <property name="httpConfiguration" ref="httpAuth"/>
//        </bean>
//        HttpConfiguration httpConfiguration = new HttpConfiguration();
//        httpConfiguration.setAuthMethod("BASIC");
//        httpConfiguration.setAuthUsername();
//        httpConfiguration.setAuthPassword();
//
//        HttpComponent httpComponent = new HttpComponent();
//        httpComponent.setHttpConfiguration(httpConfiguration);
//        httpComponent.setCamelContext()


        from("direct:start")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI, simple(rmafeedServiceURL))
                .setHeader("Authorization", simple(rmafeedServiceAppKey))
                .to("https://test.assettracking.sackot.com")
                .unmarshal(listJacksonDataFormat)
//                        .process(new MyProcessor())
                .marshal(bindyCsvDataFormat)
                .to(rmaFileDirLoc+"?fileName=RMAFeed-${date:now:yyyyMMddHHmmssSSS}.csv&noop=true");
    }


}
