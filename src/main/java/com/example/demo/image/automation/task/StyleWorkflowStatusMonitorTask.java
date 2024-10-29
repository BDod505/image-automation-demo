package com.example.demo.image.automation.task;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.service.StyleWorkflowService;
import com.example.demo.image.automation.service.impl.CloSetServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class StyleWorkflowStatusMonitorTask {
    @Autowired
    private CloSetServiceImpl cloSetService;
    @Autowired
    private StyleWorkflowService styleWorkflowService;


    @Scheduled(fixedRate = 60000)
    public void monitorStyles() throws IOException {
        String traceID = UUID.randomUUID().toString();
        MDC.put("X-Transaction-Id", traceID);
        List<Style> styles = cloSetService.fetchAllStyles();
        styleWorkflowService.checkAndTriggerEvent(styles);
    }
}
