package com.rentevent.model.evento;

import com.rentevent.model.utileria.Utileria;
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
@Table(name = "evento_utileria")
public class EventoUtileria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento_utileria")
    @SequenceGenerator(name = "seq_evento_utileria", sequenceName = "seq_evento_utileria", allocationSize = 1)
    @Column(name = "even_util_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "util_id", nullable = false)
    private Utileria utileria;
}
