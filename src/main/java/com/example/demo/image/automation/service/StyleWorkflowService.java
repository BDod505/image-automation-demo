package com.example.demo.image.automation.service;

import com.example.demo.image.automation.entity.Style;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface StyleWorkflowService {
    String getStyleRenderSeq(String styleId, String version);

    String getRenderDownloadUrl(String styleId, String renderSeq);

    void checkAndTriggerEvent(List<Style> styles);

    File downloadZipFile(String downloadUrl) throws IOException;

    List<File> extractImageFromZip(File zipFile) throws IOException;
}
