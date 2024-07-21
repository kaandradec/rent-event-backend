package com.rentevent.repository;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.servicio.Servicio;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    Optional<Servicio> findByCodigo(String codigo);

    @NotNull
    List<Servicio> findAll();

    @Modifying() // Se usa para indicar que la consulta modificará datos
    @Query("update Servicio s set s.nombre=:nombre, s.tipo=:tipo, s.costo=:costo, s.descripcion=:descripcion where s.codigo =:codigo")
    void updateServicio(
            @Param(value = "nombre") String nombre, @Param(value = "tipo") String tipo,
            @Param(value = "costo") BigDecimal costo, @Param(value = "descripcion") String descripcion,
            @Param(value = "codigo") String codigo);

}
