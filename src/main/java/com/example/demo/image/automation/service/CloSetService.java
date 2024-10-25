package com.example.demo.image.automation.service;

import com.example.demo.image.automation.entity.Room;
import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.entity.StyleRender;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CloSetService {
    List<Style> fetchAllStyles() throws JsonProcessingException;

    Style getStyleDetails(String styleId, String version);

    Room getRoomDetails(String roomId);

    String changeWorkflowSeq(String roomId, String workflowSeq, List<String> styleIds);
    String getRenderDownloadUrl(String styleId, String renderSeq);
    String getStyleRenderSeq(String styleId, String version);
}
