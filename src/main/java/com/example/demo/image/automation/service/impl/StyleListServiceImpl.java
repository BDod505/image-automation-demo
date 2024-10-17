package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.repository.StyleRepository;
import com.example.demo.image.automation.service.StyleListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class StyleListServiceImpl implements StyleListService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StyleRepository styleRepository;
    @Value("${clo-set.group-id}")
    private String groupId;

    @Value("${clo-set.host}")
    private String host;

    @Override
    public List<Style> fetchAndStoreStyles() throws JsonProcessingException {
        String url = host + "styles";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("groupId", groupId);

        Style[] styles = restTemplate.getForObject(builder.toUriString(), Style[].class);
        //if(styles!=null){
        //    styleRepository.saveAll(Arrays.asList(styles));
        //}
        return Arrays.asList(styles);
    }
}
