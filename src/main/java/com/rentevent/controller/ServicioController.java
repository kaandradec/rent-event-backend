package com.rentevent.controller;

import com.rentevent.dto.request.ServicioRequest;
import com.rentevent.dto.response.EventoResponse;
import com.rentevent.model.enums.EstadoServicio;
import com.rentevent.model.enums.TipoServicio;
import com.rentevent.model.servicio.ServicioResponse;
import com.rentevent.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
// Lombok genera un constructor con los atributos marcados como final, hace que no sea necesario inyectar las dependencias con @Autowired
@CrossOrigin(origins = {"http://localhost:5173"})
// Permite el acceso a los recursos desde el servidor local de React: Vite -> http://localhost:5173
public class ServicioController {
    private final ServicioService servicioService;

    @GetMapping(value = "{codigo}")
    public ResponseEntity<ServicioResponse> getServicio(@PathVariable String codigo) {
        ServicioResponse servicio = servicioService.obtenerServicioPorCodigo(codigo);
        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicio);
    }

    @GetMapping()
    public ResponseEntity<?> getServicios() {
        List<ServicioResponse> lista = this.servicioService.obtenerServicios();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAuthority('USUARIO')")
    @PostMapping("/guardar")
    public ResponseEntity<?> createService(
            @RequestPart("file") MultipartFile file,
            @RequestPart("nombre") String nombre,
            @RequestPart("tipo") String tipo,
            @RequestPart("costo") String costo,
            @RequestPart("descripcion") String descripcion,
            @RequestPart("estado") String estado,
            @RequestPart("proveedor") String proveedor
    ) {
        BigDecimal costoBigDecimal = new BigDecimal(costo);
        ServicioRequest servicioRequest = ServicioRequest.builder()
                .nombre(nombre)
                .costo(costoBigDecimal)
                .descripcion(descripcion)
                .estado(estado)
                .proveedor(proveedor)
                .tipo(tipo)
                .build();
        servicioService.guardarServicio(servicioRequest, file);


        return ResponseEntity.ok("Servicio creado satisfactoriamente");
    }

    @PreAuthorize("hasAuthority('USUARIO')")
    @PutMapping("/actualizar/{codigo}")
    public ResponseEntity<?> updateService(
            @PathVariable String codigo,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart("nombre") String nombre,
            @RequestPart("tipo") String tipo,
            @RequestPart("costo") String costo,
            @RequestPart("descripcion") String descripcion,
            @RequestPart("estado") String estado,
            @RequestPart("proveedor") String proveedor
    ) {

        BigDecimal costoBigDecimal = new BigDecimal(costo);
        ServicioRequest servicioRequest = ServicioRequest.builder()
                .nombre(nombre)
                .costo(costoBigDecimal)
                .descripcion(descripcion)
                .estado(estado)
                .proveedor(proveedor)
                .tipo(tipo)
                .build();
        servicioService.actualizarServicio(codigo, servicioRequest, file);

        return ResponseEntity.ok("Servicio actualizado satisfactoriamente");
    }

    // Probando subir imagenes
    @PostMapping("/imagen/{id}")
    public ResponseEntity<?> subirImagen(@PathVariable Integer id, @RequestPart final MultipartFile file) {
        System.out.println("HOLA");
        this.servicioService.subirImagenParaServicio(id, file);
        System.out.println("ADIOS");
        return ResponseEntity.ok("La imagen se subió satisfactoriamente");
    }
}