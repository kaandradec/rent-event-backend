package com.rentevent.service;

import com.rentevent.model.enums.TipoTarjeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTarjetaService {
    public List<TipoTarjeta> getTipoTarjeta(){
        return Arrays.stream(TipoTarjeta.values()).toList();
    }

}
