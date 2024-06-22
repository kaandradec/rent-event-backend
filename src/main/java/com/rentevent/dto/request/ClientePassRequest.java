package com.rentevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientePassRequest {
    String correo;
    String contraseniaActual;
    String contraseniaNueva;

}
