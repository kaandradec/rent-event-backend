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
public class EventoRequest {
    private String correo;
    private String nombreTargeta;
    private String numeroTarjeta;
    private String fecha;
    private String direccionFactura;
    private String nombreFactura;
    private String pais;
    private String ciudad;
    private String numeroCedula;
    private String nombreEvento;
    private String descripcionEvento;
    private String callePrincipal;
    private String calleSecundaria;
    private String referencia;
    private String asistentes;
    private CarritoRequest[] cart;
    private BigDecimal pago;
    private BigDecimal total;
}
