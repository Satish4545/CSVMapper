package com.mapcsv.csvmapper.service;
import com.mapcsv.csvmapper.model.CSVMapperModel;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterOutputStream;

@Service
public class CSVParserImpl implements CSVParser{


    @Override
    public List<CSVMapperModel> parseCSV(String filePath) {
        try {
            List<CSVMapperModel> list = downloadCSVFile(filePath);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CSVMapperModel> downloadCSVFile(String filepath) throws IOException {
        RestTemplate restTemplate= null;
        try {
            restTemplate = restTemplate();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
       // ResponseEntity<byte[]> response = restTemplate.exchange(filepath, HttpMethod.GET, entity, byte[].class);
       /* String str = new String(response.getBody());
        System.out.println(str);
        Files.write(Paths.get("C:\\Users\\Satish\\Downloads\\document\\abc.csv"), response.getBody());
        List<CSVMapperModel> lisData=getAllTheData("");*/
        File file = restTemplate.execute(filepath, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        List<CSVMapperModel> lisData=getAllTheData(file.getPath());
        //System.out.println(file);
        return lisData;
    }
    public List<CSVMapperModel> getAllTheData(String Filepath){
        try {
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Satish\\Downloads\\document\\abc.csv"));
            String Line=null;
            List<CSVMapperModel> lisData=new ArrayList<>();
            while((Line=br.readLine())!=null){
                String[] Data=Line.split(",",-1);
                StringBuilder strbuil=new StringBuilder();
                lisData.add(new CSVMapperModel(Data[0],Data[1],Data[2],Data[3],Data[4],Data[5],Data[6],Data[7],Data[8],Data[9],Data[10],Data[11]));
            }
            return lisData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy=new TrustStrategy(){
            @Override
            public boolean isTrusted(X509Certificate[] chain,String authType){
                return true;
            }
        };
        SSLContext sslContext=new SSLContextBuilder().useProtocol("TLSv1.2").loadTrustMaterial(null,acceptingTrustStrategy).build();
        SSLSocketFactory factory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        HttpClient httpClient= HttpClients.custom().setSSLSocketFactory(factory).build();
        HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);

        return new RestTemplate(requestFactory);
    }
}
