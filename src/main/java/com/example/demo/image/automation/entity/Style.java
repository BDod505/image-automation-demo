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
    @JsonProperty("contentType")
    public int contentType;
    @JsonProperty("contentId")
    public String contentId;
    @JsonProperty("styleId")
    public String styleId;
    @JsonProperty("version")
    public int version;
    @JsonProperty("styleNumber")
    public String styleNumber;
    @JsonProperty("fileType")
    public int fileType;
    @JsonProperty("fileSize")
    public int fileSize;
    @JsonProperty("fileName")
    public String fileName;
    @JsonProperty("filePath")
    public String filePath;
    @JsonProperty("isNew")
    public boolean isNew;
    @JsonProperty("thumbnailPath")
    public String thumbnailPath;
    @JsonProperty("thumbnailName")
    public String thumbnailName;
}
