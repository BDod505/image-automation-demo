package com.example.demo.image.automation.service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public interface SharePointService {
    boolean checkFolderPathExists(String dirPathString) throws URISyntaxException;

    void uploadImagesToSharePoint(List<File> images, String metaDat);
}
