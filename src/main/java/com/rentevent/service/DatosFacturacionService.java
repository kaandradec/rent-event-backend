package com.rentevent.service;

import com.rentevent.repository.IDatosFacuturacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatosFacturacionService {
    @Autowired
    IDatosFacuturacionRepository iDatosFacuturacionRepository;


}
