package com.example.demo.image.automation.service.impl;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.entity.StyleRender;
import com.example.demo.image.automation.event.WorkFlowStatusChangeEvent;
import com.example.demo.image.automation.repository.StyleRenderRepository;
import com.example.demo.image.automation.service.StyleWorkflowService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class StyleWorkflowServiceImpl implements StyleWorkflowService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StyleRenderRepository renderRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${clo-set.host}")
    private String host;

    @Override
    public String getStyleRenderSeq(String styleId, String version) {
        String url = host + "styles/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(styleId, "versions", version, "renders");

        StyleRender[] render = restTemplate.getForObject(builder.toUriString(), StyleRender[].class);
        //If we plan to store render info in DB
        // if(render!=null){
        //     renderRepository.saveAll(Arrays.asList(render));
        // }
        return render[0].getRenderSeq();
    }

    @Override
    public String getRenderDownloadUrl(String styleId, String renderSeq) {
        String url = host + "styles/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(styleId, "renders", renderSeq, "downloadurl");

        String downloadUrl = restTemplate.getForObject(builder.toUriString(), String.class);
        System.out.print(downloadUrl + "POPO");
        return downloadUrl;
    }

    @Override
    public void checkAndTriggerEvent(List<Style> styles) {
        for (Style style : styles) {
            System.out.print(style.styleId + "GOGO");
            if ("publish".equalsIgnoreCase(style.getWorkflowName())) {
                eventPublisher.publishEvent(new WorkFlowStatusChangeEvent(this, style.getStyleId(), style.getVersion()));
            }
        }
    }

    @Override
    public File downloadZipFile(String downloadUrl) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(downloadUrl))
                    .GET()
                    .build();
            HttpResponse<InputStream> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofInputStream()
            );
            System.out.println(response.statusCode());
            if (response.statusCode() == 200) {
                File zipFile = new File("downloaded.zip");
                try (FileOutputStream fos = new FileOutputStream(zipFile)) {
                    fos.write(response.body().readAllBytes());
                    System.out.println("TADA:File Downloaded Successfully");
                    return zipFile;
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new File("");
    }

    @Override
    public List<File> extractImageFromZip(File zipFile) throws IOException {
        List<File> extractedImages = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(FileUtils.openInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (!zipEntry.isDirectory() && isImageFile(zipEntry.getName())) {
                    File extractedImage = new File(zipEntry.getName());
                    try (FileOutputStream fos = new FileOutputStream(extractedImage)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                    extractedImages.add(extractedImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extractedImages;
    }

    private boolean isImageFile(String filename) {
        String lowerCaseName = filename.toLowerCase();
        return lowerCaseName.endsWith(".png");
    }
}
