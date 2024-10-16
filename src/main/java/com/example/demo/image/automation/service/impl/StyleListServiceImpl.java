package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.models.StyleDTO;
import com.example.demo.image.automation.repository.StyleRepository;
import com.example.demo.image.automation.service.StyleListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Style> fetchAndStoreStyles(String groupId) throws JsonProcessingException {
        String url = "https://marksandspencer.clo-set.com/api/styles";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("groupId",groupId);

        Style[] styles = restTemplate.getForObject(builder.toUriString(),Style[].class);
        System.out.print(styles.length+"LYTS");
        if(styles!=null){
            styleRepository.saveAll(Arrays.asList(styles));
        }
        return Arrays.asList(styles);
    }
}
