package com.rentevent.dto.response;

import com.rentevent.model.enums.TipoServicio;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.proveedor.Proveedor;
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
public class ServicioResponse {
    private Integer id;
    private String codigo;
    private String nombre;
    private String estado;
    private String tipo;
    private BigDecimal costo;
    private String descripcion;
    private List<Imagen> imagenes;
    private List<EventoServicio> eventos;
    private String proveedor;
}
