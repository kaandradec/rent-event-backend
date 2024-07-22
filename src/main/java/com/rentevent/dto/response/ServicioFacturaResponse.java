package com.rentevent.dto.response;

import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.imagen.Imagen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioFacturaResponse {

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
}
