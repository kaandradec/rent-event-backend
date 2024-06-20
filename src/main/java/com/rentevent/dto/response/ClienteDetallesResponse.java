package com.rentevent.dto.response;

import com.rentevent.model.enums.Genero;
import com.rentevent.model.enums.Pais;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetallesResponse {

    Pais pais;
    String prefijo;
    String telefono;
    Genero genero;
}
