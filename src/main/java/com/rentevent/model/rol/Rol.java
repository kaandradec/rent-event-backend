package com.rentevent.model.rol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.rentevent.model.usuario.Usuario;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol")
    @SequenceGenerator(name = "seq_rol", sequenceName = "seq_rol", allocationSize = 1)
    @Column(name = "rol_id")
    private Integer id;

    @Column(name = "rol_nombre", nullable = false)
    private String nombre;

    @Column(name = "rol_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "rol_horas_mensuales", nullable = false)
    private Double horasMensuales;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios;
}

