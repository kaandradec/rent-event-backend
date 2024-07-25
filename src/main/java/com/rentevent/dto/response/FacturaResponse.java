package com.rentevent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaResponse {
    private String cedulaCliente;
    private String direccionCliente;
    private String direccionEmpresa;
    private String empresa;
    private Date fechaEmision;
    private BigDecimal iva;
    private String nombreCliente;
    private String numero;
    private String rucEmpresa;
    private BigDecimal montoTotal;
}
