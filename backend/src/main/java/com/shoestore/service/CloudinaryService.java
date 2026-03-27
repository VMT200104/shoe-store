package com.shoestore.service;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface CloudinaryService {
    Map<String, String> uploadFile(MultipartFile file, String folder) throws IOException;
    void deleteFile(String publicId) throws IOException;
}
