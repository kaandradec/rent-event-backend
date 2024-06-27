package com.rentevent.dto.request;

import com.rentevent.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String correo;
    String contrasenia;
    String nombre;
    String apellido;
    Genero genero;
    String prefijo;
    String telefono;
    String pais;
    String ciudad;


}
