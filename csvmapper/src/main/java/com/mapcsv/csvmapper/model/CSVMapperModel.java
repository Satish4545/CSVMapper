package com.mapcsv.csvmapper.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CSVMapperModel {

    private String street;
    private String city;
    private String zip;
    private String state;
    private String beds;
    private String baths;
    private String sq_feet;
    private String type;
    private String sale_date;
    private String price;
    private String latitude;
    private String longitude;

    public CSVMapperModel(String street,String city,String zip,String state,String beds,String baths,String sq_feet,String type,String sale_date,String price,String latitude,String longitude){
        this.street=street;
        this.city=city;
        this.zip=zip;
        this.state=state;
        this.beds=beds;
        this.baths=baths;
        this.sq_feet=sq_feet;
        this.type=type;
        this.sale_date=sale_date;
        this.price=price;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
