package com.example.demo.image.automation.service;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.models.StyleDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StyleListService {
    public List<Style>  fetchAndStoreStyles(String groupId) throws JsonProcessingException;
}
