package com.rentevent.repository;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.servicio.Servicio;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEventoRepository extends JpaRepository<Evento, Integer> {
    Optional<Evento> findByNombreAndCliente(String nombre, Cliente cliente);

    @NotNull
    List<Evento> findByClienteOrderByIdDesc(Cliente cliente);

    List<Evento> getAllByCliente(Cliente cliente);
    List<Evento> getAllByClienteOrderByFechaDesc(Cliente cliente);

    Optional<Evento> findByCodigo(String codigo);

}