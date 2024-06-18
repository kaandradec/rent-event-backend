package com.rentevent.model.servicio;

import com.rentevent.model.TipoServicio;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.proveedor.Proveedor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private String codigo;
    private String nombre;
    private String estado;
    private TipoServicio tipo;
    private BigDecimal costo;
    private String descripcion;
    private List<Imagen> imagenes;
    private List<EventoServicio> eventos;
    private Proveedor proveedor;
}
