package com.rentevent.service;

import com.rentevent.dto.request.ServicioRequest;
import com.rentevent.dto.request.ServiciosEventoRequest;
import com.rentevent.dto.response.CloudinaryResponse;
import com.rentevent.dto.response.ServicioFacturaResponse;
import com.rentevent.dto.response.ServicioResponse;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.repository.*;
import com.rentevent.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
public class ServicioService {
    @Autowired
    private final IServicioRepository servicioRepository;
    @Autowired
    private final IProveedorRepository proveedorRepository;
    @Autowired
    private final IImagenRepository imagenRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private IEventoRepository iEventoRepository;
    @Autowired
    private IEventoServicioRepository iEventoServicioRepository;

    /**
     * Obtiene los detalles de un servicio específico por su código.
     * Este método busca un servicio por su código en la base de datos. Si el servicio se encuentra,
     * se construye y devuelve una respuesta que incluye los detalles del servicio, como el código, nombre,
     * tipo, costo, estado, descripción, imágenes asociadas y el nombre del proveedor.
     * Si el servicio no se encuentra, devuelve null.
     *
     * @param codigo El código del servicio a buscar.
     * @return ServicioResponse que contiene los detalles del servicio encontrado o null si el servicio no se encuentra.
     */
    public ServicioResponse obtenerServicioPorCodigo(String codigo) {
        Servicio servicio = servicioRepository.findByCodigo(codigo).orElse(null);

        if (servicio != null) {
            ServicioResponse servicioResponse = ServicioResponse.builder()
                    .codigo(servicio.getCodigo())
                    .nombre(servicio.getNombre())
                    .tipo(servicio.getTipo())
                    .costo(servicio.getCosto())
                    .estado(servicio.getEstado())
                    .descripcion(servicio.getDescripcion())
                    .imagenes(servicio.getImagenes())
                    .proveedor(servicio.getProveedor().getNombre())
                    .build();
            return servicioResponse;
        }
        return null;
    }

    /**
     * Recupera todos los servicios disponibles en la base de datos y los devuelve en forma de una lista de objetos de respuesta.
     * Este método consulta todos los servicios almacenados, convirtiendo cada uno en un {@link ServicioResponse} que incluye
     * todos los detalles relevantes del servicio, como su ID, código, nombre, tipo, costo, descripción, imágenes asociadas,
     * el nombre del proveedor y el estado actual del servicio.
     *
     * @return Una lista de {@link ServicioResponse} que representa todos los servicios disponibles.
     */
    public List<ServicioResponse> obtenerServicios() {
        List<Servicio> servicios = this.servicioRepository.findAll();

        List<ServicioResponse> listaServicioResponse = new ArrayList<>();

        servicios.forEach(servicio -> {
            listaServicioResponse.add(
                    ServicioResponse.builder()
                            .id(servicio.getId())
                            .codigo(servicio.getCodigo())
                            .nombre(servicio.getNombre())
                            .tipo(servicio.getTipo())
                            .costo(servicio.getCosto())
                            .descripcion(servicio.getDescripcion())
                            .imagenes(servicio.getImagenes())
                            .proveedor(servicio.getProveedor().getNombre())
                            .estado(servicio.getEstado())

                            .build()
            );
        });

        return listaServicioResponse;
    }

    /**
     * Obtiene los detalles de un servicio específico por su ID.
     * Este método busca un servicio por su ID en la base de datos. Si el servicio se encuentra,
     * se construye y devuelve una respuesta que incluye los detalles del servicio, como el nombre,
     * tipo, costo, descripción, imágenes asociadas, eventos relacionados y el proveedor del servicio.
     * Si el servicio no se encuentra, se lanza una excepción {@link NotFoundException}.
     *
     * @param id El ID del servicio a buscar.
     * @return ServicioResponse que contiene los detalles del servicio encontrado.
     * @throws NotFoundException Si el servicio con el ID especificado no se encuentra.
     */
    public ServicioResponse obtenerServicioPorId(Integer id) {
        Servicio servicio = this.servicioRepository.findById(id).orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        return ServicioResponse.builder()
                .id(servicio.getId())
                .codigo(servicio.getCodigo())
                .nombre(servicio.getNombre())
                .tipo(servicio.getTipo())
                .costo(servicio.getCosto())
                .descripcion(servicio.getDescripcion())
                .imagenes(servicio.getImagenes())
                .eventos(servicio.getEventos())
                .proveedor(servicio.getProveedor().getNombre())
                .build();
    }

    /**
     * Crea y guarda un nuevo servicio en la base de datos, incluyendo la subida de una imagen asociada a este servicio.
     * Este método genera un código único para el servicio, busca el proveedor basado en el nombre proporcionado en la solicitud,
     * sube la imagen proporcionada a Cloudinary, y finalmente guarda el servicio con su imagen asociada en la base de datos.
     *
     * @param servicioRequest Los detalles del servicio a guardar, incluyendo nombre, tipo, costo, estado, descripción y el nombre del proveedor.
     * @param file            El archivo de imagen del servicio a subir a Cloudinary.
     * @throws NotFoundException Si el proveedor especificado en la solicitud no se encuentra en la base de datos.
     */
    @Transactional
    public void guardarServicio(ServicioRequest servicioRequest, MultipartFile file) {
        UUID uuid = UUID.randomUUID();

        Proveedor proveedor = this.proveedorRepository.findByNombre(servicioRequest.getProveedor())
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado"));

        String fileName = getFileName(file);
        final CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);

        Imagen imagen = Imagen.builder()
                .url(response.getUrl())
                .nombre(fileName)
                .idPublica(response.getPublicId())
                .etiqueta("SERVICIO")
                .build();

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
    public void actualizarServicio(String codigo, ServicioRequest servicioRequest, MultipartFile file) {
        Servicio servicio = this.servicioRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        System.out.println("Servicio: " + servicio.getNombre());
        Proveedor proveedor = this.proveedorRepository.findByNombre(servicioRequest.getProveedor()).orElseThrow(() -> new NotFoundException("Proveedor no encontrado"));
        System.out.println("Proveedor: " + proveedor.getNombre());

        String originalFileName = file.getOriginalFilename();

        System.out.println("File NAME: " + originalFileName);
        if (originalFileName.equals("blob")) {

            servicio.setNombre(servicioRequest.getNombre());
            servicio.setTipo(servicioRequest.getTipo());
            servicio.setCosto(servicioRequest.getCosto());
            servicio.setEstado(servicioRequest.getEstado());
            servicio.setDescripcion(servicioRequest.getDescripcion());
            servicio.setProveedor(proveedor);

            this.servicioRepository.save(servicio);

            return;
        }
        String fileName = getFileName(file);

        // Eliminar imagen anterior de Cloudinary
        String idPublica = servicio.getImagenes().get(0).getIdPublica();
        servicio.getImagenes().clear();

        this.imagenRepository.deleteByIdPublica(idPublica);
        this.cloudinaryService.deleteFile(idPublica);

        CloudinaryResponse responseCloudinary = this.cloudinaryService.uploadFile(file, fileName);

        Imagen imagen = Imagen.builder()
                .url(responseCloudinary.getUrl())
                .nombre(fileName)
                .idPublica(responseCloudinary.getPublicId())
                .etiqueta("SERVICIO")
                .build();

        System.out.println("Imagen: " + imagen);
        servicio.setNombre(servicioRequest.getNombre());
        servicio.setTipo(servicioRequest.getTipo());
        servicio.setCosto(servicioRequest.getCosto());
        servicio.setEstado(servicioRequest.getEstado());
        servicio.setDescripcion(servicioRequest.getDescripcion());
        servicio.setProveedor(proveedor);
        servicio.getImagenes().add(imagen);

        imagen.setServicio(servicio);

        this.servicioRepository.save(servicio);

    }

    public String getFileName(MultipartFile file) {
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        return FileUploadUtil.getFileName(file.getOriginalFilename());
    }

    /**
     * Sube una imagen para un servicio específico, asociándola con el servicio en la base de datos.
     * Este método maneja la carga de la imagen a Cloudinary y actualiza la información del servicio
     * con la nueva imagen.
     *
     * @param idServicio el ID del servicio al cual se le va a subir la imagen.
     * @param file       el archivo de imagen que se va a subir.
     * @throws NotFoundException si el servicio con el ID especificado no se encuentra.
     */
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

    public List<ServicioFacturaResponse> obtenerServiciosPorEvento(ServiciosEventoRequest request) {
        Evento evento = iEventoRepository.findByCodigo(request.getCodEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        List<EventoServicio> eventoServicios = iEventoServicioRepository.findByEvento(evento);

        List<Servicio> servicioList = eventoServicios.stream()
                .map(EventoServicio::getServicio)
                .collect(Collectors.toList());
        List<ServicioFacturaResponse> servicioFacturaResponses = new ArrayList<>();
        servicioList.forEach(servicio -> servicioFacturaResponses.add(
                ServicioFacturaResponse.builder().precio(servicio.getCosto())
                        .descripcion(servicio.getDescripcion())
                        .nombre(servicio.getNombre())
                        .build()
        ));

        return servicioFacturaResponses;
    }
}