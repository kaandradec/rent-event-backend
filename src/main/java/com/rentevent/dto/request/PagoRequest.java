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
public class PagoRequest {
    private String evento;
    private String nombreTargeta;
    private String numeroTarjeta;
    private String direccionFactura;
    private String nombreFactura;
    private String numeroCedula;
    private BigDecimal pago;

}
