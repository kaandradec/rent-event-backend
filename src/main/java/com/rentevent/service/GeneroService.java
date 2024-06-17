package com.rentevent.service;

import com.rentevent.model.Genero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {
    public List<Genero> getGeneros(){
        return Arrays.stream(Genero.values()).toList();
    }

}
