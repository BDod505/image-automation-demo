package com.example.demo.image.automation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @JsonProperty("roomId")
    public String roomId;
    @JsonProperty("name")
    public String roomName;
    @JsonProperty("spaceInfo")
    public String dirURI;

    @JsonSetter("spaceInfo")
    public void setdirURI(SpaceInfo spaceInfo){
        StringBuilder builder = new StringBuilder("/");
        for(Navigation navigation:spaceInfo.getNavigations()){
            builder.append(navigation.getName()).append("/");
        }
        builder.append(this.roomName);
        this.dirURI = builder.toString();
    }
}


