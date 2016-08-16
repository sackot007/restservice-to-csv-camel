package com.sackot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.Calendar;

/**
 * Created by tkmaw80 on 7/29/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonIgnoreProperties({"_id", "battery_status", "result", "diagnostic_by", "owner", "updated_at"})

@CsvRecord(separator=",", generateHeaderColumns=true)
public class RmaFeed {
    @JsonProperty("esign_id")
    @DataField(pos = 1, columnName = "ESignID")
    String esignId;


    @JsonProperty("failure_code")
    @DataField(pos = 2, columnName = "Failure_CD")
    String failCode;

    @JsonProperty("diagnostic_at")
    @DataField(pos = 3, columnName = "BornOnDate")
    String bornOnDate;

    @JsonIgnore
    @DataField(pos = 4, columnName = "ShipDate")
    String shipDate;

    public RmaFeed() {
        shipDate = Calendar.getInstance().getTime().toString();
    }

    public String getEsignId() {
        return esignId;
    }

    public void setEsignId(String esignId) {
        this.esignId = esignId;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getBornOnDate() {
        return bornOnDate;
    }

    public void setBornOnDate(String bornOnDate) {
        this.bornOnDate = bornOnDate;
    }

    public String getShipDate() {
        return shipDate;
    }

    @Override
    public String toString() {
        return "RmaFeed{" +
                "esignId='" + esignId + '\'' +
                ", failCode='" + failCode + '\'' +
                ", bornOnDate='" + bornOnDate + '\'' +
                ", shipDate='" + shipDate + '\'' +
                '}';
    }
}
