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
public class ServicioRequest {
    private String codigo; // private no es necesario ya que Lombok genera los getters y setters por defecto
    // aunque no se especifique el modificador de acceso, sin embargo es una buena práctica
    private String nombre;
    private String tipo;
    private BigDecimal precio;
    private String descripcion;
    private String personalizacion;
    private String imagen;


}
