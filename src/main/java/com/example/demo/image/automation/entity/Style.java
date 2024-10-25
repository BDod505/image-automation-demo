package com.example.demo.image.automation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Style")
public class Style {
    @JsonProperty("styleId")
    public String styleId;
    @JsonProperty("version")
    public String version;
    @JsonProperty("workflowName")
    public String workflowName;
    @JsonProperty("roomId")
    public String roomId;
}
