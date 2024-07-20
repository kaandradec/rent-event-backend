package com.rentevent.dto.request;

import com.rentevent.model.enums.EstadoServicio;
import com.rentevent.model.enums.TipoServicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicioRequest {
    private String nombre;
    private BigDecimal costo;
    private String descripcion;
    private String estado;
    private String tipo;
    private String proveedor;
    private String idPublica;
}
