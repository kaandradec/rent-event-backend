package com.rentevent.model.incidencia;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.usuario.Usuario;
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
@Table(name = "incidencia")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_incidencia")
    @SequenceGenerator(name = "seq_incidencia", sequenceName = "seq_incidencia", allocationSize = 1)
    @Column(name = "inci_id")
    private Integer id;

    @Column(name = "inci_descripcion")
    private String descripcion;
//
//    @Column(name = "inci_estado")
//    private String estado;

    @OneToOne(mappedBy = "incidencia")
    private Evento evento;
//    @ManyToOne
//    @JoinColumn(name = "usua_id")
//    private Usuario usuario;
}