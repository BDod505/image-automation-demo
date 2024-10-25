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
@Document("Render")
public class StyleRender {
    @JsonProperty("styleId")
    public String styleId;
    @JsonProperty("renderSeq")
    public String renderSeq;
    @JsonProperty("renderStatus")
    public String renderStatus;
}
