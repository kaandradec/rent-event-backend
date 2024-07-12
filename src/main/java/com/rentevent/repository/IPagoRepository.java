package com.rentevent.repository;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.factura.Factura;
import com.rentevent.model.pago.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IPagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByEvento(Evento evento);

}
