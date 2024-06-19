package com.rentevent.dto.request;

import com.rentevent.model.EstadoServicio;
import com.rentevent.model.TipoServicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicioRequest {
    private String nombre;
    private BigDecimal costo;
    private String descripcion;
    private EstadoServicio estado;
    private TipoServicio tipo;
    private String proveedor;
    private String idPublica;
}
