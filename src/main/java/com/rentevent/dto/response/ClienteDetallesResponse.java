package com.rentevent.dto.response;

import com.rentevent.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetallesResponse {

    String pais;
    String prefijo;
    String telefono;
    Genero genero;
}
