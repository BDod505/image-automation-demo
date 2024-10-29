package com.example.demo.image.automation.service;

import java.io.File;
import java.util.List;

public interface SharePointService {
    String checkAndCreateFolder(String dirPathString);
    public String createFolder(String folderName,String folderId);
    void uploadImagesToSharePoint(List<File> images, String metaDat);
}
