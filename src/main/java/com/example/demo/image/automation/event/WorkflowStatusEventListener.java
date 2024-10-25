package com.example.demo.image.automation.event;

import com.example.demo.image.automation.service.CloSetService;
import com.example.demo.image.automation.service.SharePointService;
import com.example.demo.image.automation.service.StyleWorkflowService;
import com.example.demo.image.automation.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Component
public class WorkflowStatusEventListener {
    @Autowired
    private CloSetService cloSetService;

    @Autowired
    private SharePointService sharePointService;

    FileUtil fileUtil;

    @EventListener
    public void onWorkflowStatusChange(WorkFlowStatusChangeEvent event) throws IOException, URISyntaxException {
        log.info("Consuming Workflow Status Change Event.....");
        boolean folderStatus = sharePointService.checkFolderPathExists(event.getDirectoryPathString());
        //File downloadedZip = fileUtil.downloadZipFile(event.getDownloadUrl());
        //List<File> images = fileUtil.extractImageFromZip(downloadedZip);
        //sharePointService.uploadImagesToSharePoint(images, event.getStyleId());
        //downloadedZip.delete();
        //
    }
}
