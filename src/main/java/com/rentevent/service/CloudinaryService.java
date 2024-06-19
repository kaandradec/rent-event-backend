package com.rentevent.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rentevent.dto.response.CloudinaryResponse;
import com.rentevent.exception.FuncErrorException;
import com.rentevent.repository.IServicioRepository;
import com.rentevent.util.FileUploadUtil;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = this.cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", "rentevent/servicio/" + fileName));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (Exception e) {
            throw new FuncErrorException("Error uploading file");
        }
    }

    @Transactional
    public String deleteFile(String publicId) {
        try {
            final Map result = this.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return (String) result.get("result");
        } catch (Exception e) {
            throw new FuncErrorException(">>>>>Error deleting file");
        }
    }

}
