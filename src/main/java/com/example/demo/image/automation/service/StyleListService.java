package com.example.demo.image.automation.service;

import com.example.demo.image.automation.entity.Style;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StyleListService {
    List<Style> fetchAndStoreStyles() throws JsonProcessingException;
}
