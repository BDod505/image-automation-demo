package com.example.demo.image.automation.task;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.models.StyleDTO;
import com.example.demo.image.automation.service.impl.StyleListServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StyleWorkflowStatusMonitorTask {
    @Autowired
    private StyleListServiceImpl styleListService;

    private String groupId = "F377319ACE";
    @Scheduled(fixedRate = 30000)
    public void monitorStyles() throws JsonProcessingException {
        List<Style> styles = styleListService.fetchAndStoreStyles(groupId);
    }
}
