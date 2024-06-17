package com.rentevent.dto.response;

import com.rentevent.model.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private String nombre;
    private String apellido;
    private String telefono;
    private Genero genero;
    private String correo;
}

