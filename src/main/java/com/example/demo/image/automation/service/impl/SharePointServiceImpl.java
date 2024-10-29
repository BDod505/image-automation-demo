package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.models.Folder;
import com.example.demo.image.automation.models.response.FolderCreateResponse;
import com.example.demo.image.automation.models.response.FolderListResponse;
import com.example.demo.image.automation.service.SharePointService;
import com.example.demo.image.automation.service.StyleWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Service
public class SharePointServiceImpl implements SharePointService {
    @Autowired
    private RestTemplate restTemplateSharepoint;

    public static final String FOLDER_ID = "{{FOLDER_ID}}";
    public static final String DRIVE_ID = "{{DRIVE_ID}}";

    public static final String FILE_NAME = "{{FILE_NAME}}";

    @Autowired
    private StyleWorkflowService styleWorkflowService;

    @Value("${sharepoint.token}")
    private String token;

    @Value("${sharepoint.folder-list-url}")
    private String listUrl;

    @Value("${sharepoint.folder-create-url}")
    private String createUrl;

    @Value("${sharepoint.folder-base-url}")
    private String folderBaseUrl;

    @Value("${sharepoint.drive-id}")
    private String driveId;

    @Value("${sharepoint.file-upload-url}")
    private String fileUploadUrl;

    @Override
    public String checkAndCreateFolder(String dirPathString){
        log.info(dirPathString);
        dirPathString=URLEncoder.encode(dirPathString, StandardCharsets.UTF_8)
                .replace("+","%20").replace("%2F","/");
        log.info(dirPathString);
        Folder[] folders = getFolderList();
        Map<Boolean,String> folderMap = checkFolderPathExists(dirPathString,folders);
        boolean folderStatus = folderMap.keySet().iterator().next();
        if(folderStatus){
            log.info("Found the existing folder");
            return folderMap.get(folderStatus);
        }else {
            log.info("Creating new folder");
            Map<String,List<String>> createFoldersMap = getFoldersToCreate(folders,dirPathString);
            String initialFolderId = createFoldersMap.keySet().iterator().next();
            String parentFolderId = initialFolderId;
            for(String name:createFoldersMap.get(parentFolderId)){
                name = URLDecoder.decode(name,StandardCharsets.UTF_8);
                parentFolderId=createFolder(name,parentFolderId);
            }
            return parentFolderId;
        }
    }

    public Map<Boolean,String> checkFolderPathExists(String dirPathString,Folder[] folders){
        log.info("Checking Sharepoint folders");
        Map<Boolean,String> folderMap = new HashMap<>();
        for(Folder folder:folders){
            if(folder.pathUrl.contains(dirPathString)){
                folderMap.put(true,folder.getId());
                return folderMap;
            }
        }
        folderMap.put(false,"");
        return folderMap;
    }

    public Map<String,List<String>> getFoldersToCreate(Folder[] folders,String dirPathString){
        String longestExistingPath = "";
        String parentFolderId="";
        for(Folder folder:folders){
            String temp = folder.pathUrl.replace(folderBaseUrl,"/");
            if((dirPathString).startsWith(temp) && temp.length()>longestExistingPath.length()){
                longestExistingPath=temp;
                parentFolderId=folder.getId();
            }
        }
        log.info(longestExistingPath);
        List<String> existingParts = Arrays.asList(longestExistingPath.split("/"));
        List<String> targetParts = Arrays.asList(dirPathString.split("/"));
        List<String> foldersToCreate = targetParts.subList(existingParts.size(),targetParts.size());
        Map<String,List<String>> createFolderMap = new HashMap<>();
        createFolderMap.put(parentFolderId,foldersToCreate);
        return createFolderMap;
    }


    public Folder[] getFolderList(){
        FolderListResponse folderListResponse = restTemplateSharepoint.getForObject(listUrl,FolderListResponse.class);
        log.info("Folder list fetched successfully");
        return folderListResponse.getFolderList();
    }

    @Override
    public String createFolder(String folderName,String folderId){
        String folderCreationRequestBody = "{ \"name\": \"" + folderName
                + "\", \"folder\": {}, \"@microsoft.graph.conflictBehavior\": \"fail\" }";
        String createUrlNew = createUrl.replace(FOLDER_ID, folderId).replace(DRIVE_ID, driveId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(folderCreationRequestBody,headers);
        FolderCreateResponse response = restTemplateSharepoint.postForObject(createUrlNew,requestEntity,FolderCreateResponse.class);
        return response.getFolderId();
    }

    @Override
    public void uploadImagesToSharePoint(List<File> images, String folderId) {
        for (File image : images) {
            try {
                String uploadUrl = fileUploadUrl.replace(DRIVE_ID, driveId).replace(FOLDER_ID, folderId).replace(FILE_NAME,image.getName());
                log.info(uploadUrl);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                byte[] fileContent = Files.readAllBytes(image.toPath());
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileContent, headers);
                ResponseEntity<String> response = restTemplateSharepoint.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);
                System.out.print("Uploaded Image" + image.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
