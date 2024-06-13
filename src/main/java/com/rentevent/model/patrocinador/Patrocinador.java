package com.rentevent.model.patrocinador;

import com.rentevent.model.evento.*;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.servicio.Servicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patrocinador")
public class Patrocinador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_patrocinador")
    @SequenceGenerator(name = "seq_patrocinador", sequenceName = "seq_patrocinador", allocationSize = 1)
    @Column(name = "patr_id")
    private Integer id;

    @Column(name = "patr_nombre", nullable = false)
    private String nombre;

    @Column(name = "patr_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "patr_tipo", nullable = false)
    private String tipo;

    @OneToMany(mappedBy = "patrocinador")
    private List<EventoPatrocinador> eventoPatrocinadores;

}

