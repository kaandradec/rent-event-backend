package com.rentevent.repository;

import com.rentevent.model.imagen.Imagen;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByOrderById();

    void deleteByIdPublica(String idPublica);

}
