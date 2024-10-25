package com.example.demo.image.automation.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

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

@Slf4j
public class FileUtil {

    public File downloadZipFile(String downloadUrl) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(downloadUrl))
                    .GET()
                    .build();
            log.info("Downloading the ZIP file");
            HttpResponse<InputStream> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofInputStream()
            );
            if (response.statusCode() == 200) {
                log.info("Zip file download successfully");
                File zipFile = new File("downloaded.zip");
                try (FileOutputStream fos = new FileOutputStream(zipFile)) {
                    fos.write(response.body().readAllBytes());
                    log.info("TADA:File Saved Successfully");
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

    public List<File> extractImageFromZip(File zipFile) throws IOException {
        log.info("Extracting the zip file");
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
            log.info("All the images extracted successfully");
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
