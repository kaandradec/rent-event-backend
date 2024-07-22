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

    /**
     * Sube un archivo a Cloudinary.
     * Este método maneja la carga de archivos a Cloudinary utilizando el identificador único (fileName) proporcionado.
     * El archivo se sube con un identificador público personalizado para facilitar su recuperación y gestión.
     * En caso de éxito, devuelve una respuesta que incluye el URL seguro y el ID público del archivo subido.
     * Si ocurre un error durante la carga, se lanza una excepción personalizada indicando el problema.
     *
     * @param file     El archivo a subir.
     * @param fileName El nombre único para el archivo, que se usa para crear el identificador público en Cloudinary.
     * @return CloudinaryResponse que contiene el URL seguro y el ID público del archivo subido.
     * @throws FuncErrorException Si ocurre un error durante la carga del archivo.
     */
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

    /**
     * Elimina un archivo de Cloudinary.
     * Este método maneja la eliminación de archivos en Cloudinary utilizando el ID público del archivo.
     * Si la eliminación es exitosa, devuelve el resultado de la operación.
     * En caso de error durante la eliminación, se lanza una excepción personalizada.
     *
     * @param publicId El ID público del archivo en Cloudinary a eliminar.
     * @return String que indica el resultado de la operación de eliminación.
     * @throws FuncErrorException Si ocurre un error durante la eliminación del archivo.
     */
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
