package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.service.SharePointService;
import com.example.demo.image.automation.service.StyleWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SharePointServiceImpl implements SharePointService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StyleWorkflowService styleWorkflowService;

    @Override
    public boolean checkFolderPathExists(String dirPathString) throws URISyntaxException {
        log.info("Checking Sharepoint folders");
        log.info(dirPathString);
        try {
            URI uri = new URI(dirPathString);
            log.info(uri.toASCIIString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    @Override
    public void uploadImagesToSharePoint(List<File> images, String metaData) {
        for (File image : images) {
            try {
                String uploadUrl = "";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                byte[] fileContent = Files.readAllBytes(image.toPath());
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileContent, headers);
                //ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);
                System.out.print("Uploaded Image" + image.getName());
                //+ " Response :" + response.getStatusCode());
                List<String> styleIds = new ArrayList<>();
                styleIds.add(metaData);
                //String styleId = styleWorkflowService.changeWorkflowSeq("1203201", "2064954", styleIds);
                //System.out.println("Status chnaged successfully for style Id: " + styleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
