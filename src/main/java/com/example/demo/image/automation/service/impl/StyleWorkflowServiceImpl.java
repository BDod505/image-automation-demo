package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.entity.Navigation;
import com.example.demo.image.automation.entity.Room;
import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.event.WorkFlowStatusChangeEvent;
import com.example.demo.image.automation.service.CloSetService;
import com.example.demo.image.automation.service.StyleWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
public class StyleWorkflowServiceImpl implements StyleWorkflowService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CloSetService cloSetService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${clo-set.host}")
    private String host;

    @Value("${clo-set-group-name}")
    private String groupName;


    @Override
    public void checkAndTriggerEvent(List<Style> styles) throws UnsupportedEncodingException {
        log.info("Validating for Event Trigger");
        for (Style style : styles) {
            MDC.put("Style-Id", style.styleId);
            log.info("Validating event for Style : "+style.styleId);
            if ("render".equalsIgnoreCase(style.getWorkflowName())) {
                String renderSeq = cloSetService.getStyleRenderSeq(style.getStyleId(), style.getVersion());
                if (renderSeq != null) {
                    log.info("Publishing event for download");
                    String downloadUrl = cloSetService.getRenderDownloadUrl(style.getStyleId(),renderSeq);
                    String dirPathURI = generateDirPathURI(style);
                    eventPublisher.publishEvent(new WorkFlowStatusChangeEvent(this, downloadUrl,dirPathURI));
                }
            }
        }
    }

    private String generateDirPathURI(Style style) {
        String roomId = cloSetService.getStyleDetails(style.getStyleId(),style.getVersion()).getRoomId();
        Room room = cloSetService.getRoomDetails(roomId);
        return "/"+groupName+room.getDirURI()+"/"+style.getName();
    }
}
