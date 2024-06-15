package com.rentevent.service;

import com.rentevent.model.imagen.Imagen;
import com.rentevent.repository.IImagenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImagenService {
    @Autowired
    private IImagenRepository imagenRepository;

    public List<Imagen> listar() {
        return imagenRepository.findByOrderById();
    }

    public Optional<Imagen> obtenerPorId(Integer id) {
        return imagenRepository.findById(id);
    }

    public void guardar(Imagen imagen) {
        imagenRepository.save(imagen);
    }

    public void eliminar(Integer id) {
        imagenRepository.deleteById(id);
    }

    public boolean existe(Integer id) {
        return imagenRepository.existsById(id);
    }
}
