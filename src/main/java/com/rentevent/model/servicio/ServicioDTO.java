package com.rentevent.model.servicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private String codigo;
    private String nombre;
    private String tipo;
    private BigDecimal costo;
    private String descripcion;
}
