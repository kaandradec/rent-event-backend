package com.rentevent.service;

import com.rentevent.dto.request.ServicioRequest;
import com.rentevent.dto.response.CloudinaryResponse;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.repository.IProveedorRepository;
import com.rentevent.repository.IServicioRepository;
import com.rentevent.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
public class ServicioService {
    private final IServicioRepository servicioRepository;
    private final IProveedorRepository proveedorRepository;
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

    public ServicioResponse getService(String codigo) {
        Servicio servicio = servicioRepository.findByCodigo(codigo).orElse(null);

        if (servicio != null) {
            ServicioResponse servicioDTO = ServicioResponse.builder()
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

    public List<Servicio> obtenerServicios() {
        return this.servicioRepository.findAll();
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
    public void guardarServicio(ServicioRequest servicioRequest, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());

        Proveedor proveedor = this.proveedorRepository.findByNombre(servicioRequest.getProveedor()).orElseThrow(() -> new NotFoundException("Proveedor no encontrado"));

        Imagen imagen = subirImagenACloudinary(file);
        imagen.setEtiqueta("SERVICIO");

        Servicio servicio = Servicio.builder()
                .codigo("SERV-" + uuid.toString().substring(0, 8))
                .nombre(servicioRequest.getNombre())
                .tipo(servicioRequest.getTipo())
                .costo(servicioRequest.getCosto())
                .estado(servicioRequest.getEstado())
                .descripcion(servicioRequest.getDescripcion())
                .proveedor(proveedor)
                .imagenes(List.of(imagen))
                .build();

        imagen.setServicio(servicio);
        proveedor.getServicios().add(servicio);

        this.servicioRepository.save(servicio);
    }

    @Transactional
    public Imagen subirImagenACloudinary(final MultipartFile file) {
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);

        ;
        return Imagen.builder()
                .url(response.getUrl())
                .nombre(fileName)
                .idPublica(response.getPublicId())
                .build();
    }

    @Transactional
    public void subirImagenParaServicio(final Integer idServicio, final MultipartFile file) {
        final Servicio servicio = this.servicioRepository.findById(idServicio).orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
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
