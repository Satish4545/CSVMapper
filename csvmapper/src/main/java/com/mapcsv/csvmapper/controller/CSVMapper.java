package com.mapcsv.csvmapper.controller;

import com.mapcsv.csvmapper.model.CSVMapperModel;
import com.mapcsv.csvmapper.service.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CSVMapper {

    @Autowired
    CSVParser csvParser;

    @GetMapping("/downloadFile")
    public List<CSVMapperModel> getCSVFileDetails(){
        //@RequestParam("filepath") String filepath
        return csvParser.parseCSV("https://enpdev.myhealthfeed.com/resourcestmplocal/static/app/hf/dev/171/files/1602319478878-Sacramentorealestatetransactions.csv");
    }
}
