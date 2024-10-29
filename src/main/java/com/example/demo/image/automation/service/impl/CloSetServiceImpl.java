package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.entity.Navigation;
import com.example.demo.image.automation.entity.Room;
import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.entity.StyleRender;
import com.example.demo.image.automation.repository.StyleRenderRepository;
import com.example.demo.image.automation.repository.StyleRepository;
import com.example.demo.image.automation.service.CloSetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CloSetServiceImpl implements CloSetService {
    @Autowired
    private RestTemplate restTemplateCloset;

    @Autowired
    private StyleRepository styleRepository;
    @Value("${clo-set.group-id}")
    private String groupId;

    @Value("${clo-set.host}")
    private String host;

    @Autowired
    private StyleRenderRepository renderRepository;

    @Override
    public List<Style> fetchAllStyles() throws JsonProcessingException {
        String url = host + "styles";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("groupId", groupId);
        log.info("Fetching all the styles for GroupId : ", groupId);
        Style[] styles = restTemplateCloset.getForObject(builder.toUriString(), Style[].class);
        //if (styles != null) {
        //    log.info("Saving Styles to DB");
        //    styleRepository.saveAll(Arrays.asList(styles));
        //}
        return Arrays.asList(styles);
    }

    @Override
    public String getStyleRenderSeq(String styleId, String version) {
        String url = host + "styles/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(styleId, "versions", version, "renders");
        log.info("getting render info");
        StyleRender[] render = restTemplateCloset.getForObject(builder.toUriString(), StyleRender[].class);
        //If we plan to store render info in DB
        if(render!=null){
            log.info("Saving render to Database");
            //renderRepository.saveAll(Arrays.asList(render));
        }
        if (render.length == 0) {
            return null;
        }
        return render[0].getRenderStatus().equalsIgnoreCase("0") ? render[0].getRenderSeq() : null;
    }

    @Override
    public String getRenderDownloadUrl(String styleId, String renderSeq) {
        String url = host + "styles/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(styleId, "renders", renderSeq, "downloadurl");
        log.info("Getting the download URL");
        String downloadUrl = restTemplateCloset.getForObject(builder.toUriString(), String.class);
        return downloadUrl;
    }

    @Override
    public Style getStyleDetails(String styleId,String version){
        String url = host + "styles/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(styleId, "versions", version).queryParam("groupId", groupId);
        log.info("Getting Style Details");
        Style style = restTemplateCloset.getForObject(builder.toUriString(), Style.class);
        return style;
    }

    @Override
    public Room getRoomDetails(String roomId){
        String url = host + "rooms/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                 .queryParam("groupId", groupId);
        log.info("Getting All Rooms Details");
        Room[] rooms = restTemplateCloset.getForObject(builder.toUriString(), Room[].class);
        for(Room room:rooms){
            if(room.roomId.equalsIgnoreCase(roomId)){
                log.info("Found matched room : "+room.getRoomName());
                //for(Navigation navigation: room.getNavigations().isEmpty()){
                    log.info(room.getDirURI());
                //}
                return room;
            }
        }
        return null;
    }

    @Override
    public String changeWorkflowSeq(String roomId, String workflowSeq, List<String> styleIds) {
        String url = host + "workflows";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        Map<String, Object> payload = new HashMap<>();
        payload.put("roomId", roomId);
        payload.put("workflowSeq", workflowSeq);
        payload.put("styleIds", styleIds);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload);
        ResponseEntity<String> styleId = restTemplateCloset.exchange(builder.toUriString(), HttpMethod.PUT, requestEntity, String.class);
        return styleId.getBody();
    }
}
