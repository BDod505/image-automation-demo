package com.example.demo.image.automation.task;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.service.StyleWorkflowService;
import com.example.demo.image.automation.service.impl.StyleListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StyleWorkflowStatusMonitorTask {
    @Autowired
    private StyleListServiceImpl styleListService;
    @Autowired
    private StyleWorkflowService styleWorkflowService;


    @Scheduled(fixedRate = 30000)
    public void monitorStyles() throws IOException {
        List<Style> styles = styleListService.fetchAndStoreStyles();
        System.out.print(styles.get(0).styleId + "STYLE_ID");
        styleWorkflowService.checkAndTriggerEvent(styles);
    }
}
