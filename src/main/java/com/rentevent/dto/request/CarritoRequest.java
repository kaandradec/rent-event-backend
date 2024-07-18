package com.rentevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarritoRequest {
    private Integer id;
    private String codigo;
    private String nombre;
    private String tipo;
    private String estado;
    private String descripcion;
    private String imagen;
    private BigDecimal costo;
    private Integer quantity;
}
