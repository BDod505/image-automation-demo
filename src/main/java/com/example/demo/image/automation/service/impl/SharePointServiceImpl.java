package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.service.SharePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
public class SharePointServiceImpl implements SharePointService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void uploadImagesToSharePoint(List<File> images) {
        for (File image : images) {
            try {
                String uploadUrl = "";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                byte[] fileContent = Files.readAllBytes(image.toPath());
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileContent, headers);
                //ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);
                System.out.print("Uploaded Image" + image.getName()); //+ " Response :" + response.getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
