package com.rentevent.dto.response;


import com.rentevent.model.servicio.ServicioResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {
    private String codigo;
    private String estado; // ACTIVO CANCELADO COMPLETADO
    private String callePrincipal;
    private String calleSecundaria;
    private String referenciaDireccion;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal iva;
    private String nombre;
    private String pais;
    private String region;
    private BigDecimal precio;
    private List<PagoResponse> pagos;
    private List<ServicioResponse> servicios;
}
