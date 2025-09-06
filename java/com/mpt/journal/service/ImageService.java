package com.mpt.journal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    public String convertToBase64(byte[] imageData, String contentType) {
        if (imageData == null || imageData.length == 0) {
            return null;
        }
        return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(imageData);
    }

    public byte[] convertFromBase64(String base64String) {
        if (base64String == null || !base64String.contains(",")) {
            return null;
        }
        String base64Image = base64String.split(",")[1];
        return Base64.getDecoder().decode(base64Image);
    }

    public String getContentTypeFromBase64(String base64String) {
        if (base64String == null || !base64String.contains(",")) {
            return null;
        }
        String metaData = base64String.split(",")[0];
        return metaData.replace("data:", "").replace(";base64", "");
    }

    public void validateImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return;
        }

        // Проверка размера файла (максимум 10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IOException("Размер файла не должен превышать 10MB");
        }

        // Проверка типа файла
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Файл должен быть изображением");
        }
    }
}