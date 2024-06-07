package com.rentevent.repository;

import com.rentevent.model.servicio.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    Optional<Servicio> findByCodigo(String codigo);
    @Modifying() // Se usa para indicar que la consulta modificará datos
    @Query("update Servicio s set s.nombre=:nombre, s.tipo=:tipo, s.precio=:precio, s.descripcion=:descripcion," +
            " s.personalizacion=:personalizacion, s.imagen=:imagen where s.codigo =:codigo")
    void updateServicio(
            @Param(value = "nombre") String nombre, @Param(value = "tipo") String tipo ,
            @Param(value = "precio") BigDecimal precio, @Param(value = "descripcion") String descripcion,
            @Param(value = "personalizacion") String personalizacion, @Param(value = "imagen") String imagen,
            @Param(value = "codigo") String codigo );
}
