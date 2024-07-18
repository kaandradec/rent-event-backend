package com.rentevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaRequest {
    String correo;
    String nombre;
    String numero;
    String codSeguridad;
    String mes;
    String anio;

}
