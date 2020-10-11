package com.mapcsv.csvmapper.service;

import com.mapcsv.csvmapper.model.CSVMapperModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CSVParser {
    public List<CSVMapperModel> parseCSV(String filePath);
}
