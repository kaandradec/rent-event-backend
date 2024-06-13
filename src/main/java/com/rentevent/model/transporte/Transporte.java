package com.rentevent.model.transporte;

import com.rentevent.model.evento.EventoTransporte;
import com.rentevent.model.patrocinador.Patrocinador;
import com.rentevent.model.proveedor.Proveedor;
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
@Table(name = "transporte")
public class Transporte {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transporte")
    @SequenceGenerator(name = "seq_transporte", sequenceName = "seq_transporte", allocationSize = 1)
    @Column(name = "tran_id")
    private Integer id;

    @Column(name = "tran_matricula", nullable = false)
    private String matricula;

    @Column(name = "tran_marca", nullable = false)
    private String marca;

    @Column(name = "tran_modelo", nullable = false)
    private String modelo;

    @Column(name = "tran_tipo", nullable = false)
    private String tipo;

    @Column(name = "tran_num_plazas", nullable = false)
    private String numPlazas;

    @OneToMany(mappedBy = "transporte")
    private List<EventoTransporte> eventoTransportes;
    @ManyToOne
    @JoinColumn(name = "prov_id", nullable = false)
    private Proveedor proveedor;
}

