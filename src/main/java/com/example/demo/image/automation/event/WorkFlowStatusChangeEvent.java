package com.example.demo.image.automation.event;

import org.springframework.context.ApplicationEvent;


public class WorkFlowStatusChangeEvent extends ApplicationEvent {
    private final String styleId;
    private final String version;

    public WorkFlowStatusChangeEvent(Object source, String styleId, String version) {
        super(source);
        this.styleId = styleId;
        this.version = version;
    }

    public String getStyleId() {
        return styleId;
    }

    public String getVersion() {
        return version;
    }
}
