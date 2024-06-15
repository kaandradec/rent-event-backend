package com.rentevent.service;

import com.rentevent.dto.response.CloudinaryResponse;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.model.servicio.ServicioDTO;
import com.rentevent.model.servicio.ServicioRequest;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.repository.IServicioRepository;
import com.rentevent.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
public class ServicioService {
    private final IServicioRepository servicioRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

//    @Transactional
//    public ServicioResponse updateService(ServicioRequest servicioRequest) {
//
//        Servicio servicio = Servicio.builder()
//                .codigo(servicioRequest.getCodigo())
//                .nombre(servicioRequest.getNombre())
//                .tipo(servicioRequest.getTipo())
//                .costo(servicioRequest.getPrecio())
//                .descripcion(servicioRequest.getDescripcion())
//                .build();
//
//
//        // servicioRepository.updateUser(servicio.id, servicio.firstname, servicio.lastname, servicio.country);
//        servicioRepository.updateServicio(
//                servicio.getNombre(), servicio.getTipo(), servicio.getCosto(), servicio.getDescripcion(), servicio.getCodigo());
//
//        return new ServicioResponse("El servicio se registró satisfactoriamente");
//    }

    public ServicioDTO getService(String codigo) {
        Servicio servicio = servicioRepository.findByCodigo(codigo).orElse(null);

        if (servicio != null) {
            ServicioDTO servicioDTO = ServicioDTO.builder()
                    .codigo(servicio.getCodigo())
                    .nombre(servicio.getNombre())
                    .tipo(servicio.getTipo())
                    .costo(servicio.getCosto())
                    .descripcion(servicio.getDescripcion())
                    .build();
            return servicioDTO;
        }
        return null;
    }

    public ServicioResponse obtenerServicioPorId(Integer id) {
        Servicio servicio = this.servicioRepository.findById(id).orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        return ServicioResponse.builder()
                .codigo(servicio.getCodigo())
                .nombre(servicio.getNombre())
                .tipo(servicio.getTipo())
                .costo(servicio.getCosto())
                .descripcion(servicio.getDescripcion())
                .imagenes(servicio.getImagenes())
                .eventos(servicio.getEventos())
                .proveedor(servicio.getProveedor())
                .build();
    }

    @Transactional
    public void subirImagen(final Integer id, final MultipartFile file) {
        final Servicio servicio = this.servicioRepository.findById(id).orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        if (servicio == null) {
            throw new RuntimeException("Servicio no encontrado");
        }

        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);

        Imagen imagen = Imagen.builder()
                .url(response.getUrl())
                .nombre(fileName)
                .idPublica(response.getPublicId())
                .servicio(servicio)
                .etiqueta("servicio")
                .build();

        servicio.getImagenes().add(imagen);

        this.servicioRepository.save(servicio);
    }
}
