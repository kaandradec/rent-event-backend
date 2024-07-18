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
public class EventoRequest {
    private String correo;
    private String nombreTargeta;
    private String numeroTarjeta;
    private String fecha;
    private String direccionFactura;
    private String nombreFactura;
    private String pais;
    private String ciudad;
    private String nombreEvento;
    private String descripcionEvento;
    private String callePrincipal;
    private String calleSecundaria;
    private String referencia;
    private String asistentes;
    private CarritoRequest[] cart;
}
