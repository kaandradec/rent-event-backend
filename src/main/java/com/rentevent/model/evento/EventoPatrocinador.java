package com.rentevent.model.evento;

import com.rentevent.model.patrocinador.Patrocinador;
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
@Table(name = "evento_patrocinador")
public class EventoPatrocinador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento_patrocinador")
    @SequenceGenerator(name = "seq_evento_patrocinador", sequenceName = "seq_evento_patrocinador", allocationSize = 1)
    @Column(name = "even_patr_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "patr_id", nullable = false)
    private Patrocinador patrocinador;
}