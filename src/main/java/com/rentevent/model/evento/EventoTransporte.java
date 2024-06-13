package com.rentevent.model.evento;

import com.rentevent.model.transporte.Transporte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento_transporte")
public class EventoTransporte {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento_transporte")
    @SequenceGenerator(name = "seq_evento_transporte", sequenceName = "seq_evento_transporte", allocationSize = 1)
    @Column(name = "even_tran_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "tran_id", nullable = false)
    private Transporte transporte;
}
