package com.rentevent.model.resenia;

import com.rentevent.model.evento.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenia")
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resenia")
    @SequenceGenerator(name = "seq_resenia", sequenceName = "seq_resenia", allocationSize = 1)
    @Column(name = "rese_id")
    private Integer id;

    @Column(name = "rese_comentario", nullable = false)
    private String comentario;

    @Column(name = "rese_calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "rese_fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;
}
