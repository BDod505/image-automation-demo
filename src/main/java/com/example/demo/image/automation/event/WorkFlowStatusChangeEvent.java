package com.example.demo.image.automation.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WorkFlowStatusChangeEvent extends ApplicationEvent {
    private final String downloadUrl;
    private final String directoryPathString;

    public WorkFlowStatusChangeEvent(Object source, String downloadUrl, String directoryPathString) {
        super(source);
        this.downloadUrl = downloadUrl;
        this.directoryPathString = directoryPathString;
    }
}
