package com.example.demo.image.automation.service;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.entity.StyleRender;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface StyleWorkflowService {

    void checkAndTriggerEvent(List<Style> styles) throws UnsupportedEncodingException;
}
