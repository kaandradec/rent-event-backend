package com.rentevent.service;

import com.rentevent.model.enums.Genero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {
    /**
     * Obtiene todos los géneros disponibles.
     * Este método utiliza la enumeración Genero para obtener todos sus valores y los convierte en una lista.
     *
     * @return Una lista de {@link Genero} que representa todos los géneros disponibles.
     */
    public List<Genero> getGeneros() {
        return Arrays.stream(Genero.values()).toList();
    }

}
