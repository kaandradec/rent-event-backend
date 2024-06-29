package com.rentevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListaPreguntasSegurasRequest {
    String correo;
    String pregunta1;
    String respuesta1;
    String pregunta2;
    String respuesta2;
    String pregunta3;
    String respuesta3;
}
