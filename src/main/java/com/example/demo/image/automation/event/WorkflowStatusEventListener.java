package com.example.demo.image.automation.event;

import com.example.demo.image.automation.service.SharePointService;
import com.example.demo.image.automation.service.StyleWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class WorkflowStatusEventListener {
    @Autowired
    private StyleWorkflowService styleWorkflowService;

    @Autowired
    private SharePointService sharePointService;

    @EventListener
    public void onWorkflowStatusChange(WorkFlowStatusChangeEvent event) throws IOException {
        String renderSeq = styleWorkflowService.getStyleRenderSeq(event.getStyleId(), event.getVersion());
        String downloadUrl = styleWorkflowService.getRenderDownloadUrl(event.getStyleId(), renderSeq);
        File downloadedZip = styleWorkflowService.downloadZipFile(downloadUrl);
        List<File> images = styleWorkflowService.extractImageFromZip(downloadedZip);
        sharePointService.uploadImagesToSharePoint(images);
        downloadedZip.delete();
    }
}
