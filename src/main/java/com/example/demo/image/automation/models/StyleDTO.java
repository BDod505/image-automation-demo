package com.example.demo.image.automation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StyleDTO {
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
    /**
    @JsonProperty("")
    public boolean isS3ImagePath;
    @JsonProperty("")
    public String creatorName;
    @JsonProperty("")
    public String creatorEmail;
    @JsonProperty("")
    public int creatorId;
    @JsonProperty("")
    public Date updateDate;
    @JsonProperty("")
    public int favoriteSeq;
    @JsonProperty("")
    public boolean isCreator;
    @JsonProperty("")
    public int processing;
    @JsonProperty("")
    public int techpackProcessing;
    @JsonProperty("")
    public String colorCode;
    @JsonProperty("")
    public String workflowName;
    @JsonProperty("")
    public int workflowNum;
    @JsonProperty("")
    public int workflowSeq;
    @JsonProperty("")
    public Object customSort;
    @JsonProperty("")
    public Object externalStyleId;
    @JsonProperty("")
    public Object navigation;
    @JsonProperty("")
    public int companyId;
    @JsonProperty("")
    public String groupId;
    @JsonProperty("")
    public String turntableThumbnailPath;
    @JsonProperty("")
    public boolean hasAvatarThumbnail;
    @JsonProperty("")
    public int colorwayIndexNo;
    @JsonProperty("")
    public String colorwayName;
    @JsonProperty("")
    public String colorwayFilePath;
    @JsonProperty("")
    public String colorwayFileName;
    @JsonProperty("")
    public boolean use2dViewer;
    @JsonProperty("")
    public int currentColorwayIndex;
    **/
}
