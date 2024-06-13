package com.rentevent.model.camion;

import com.rentevent.model.evento.Evento;
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
@Table(name = "camion_evento")
public class CamionEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_camion_evento")
    @SequenceGenerator(name = "seq_camion_evento", sequenceName = "seq_camion_evento", allocationSize = 1)
    @Column(name = "came_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cami_id", nullable = false)
    private Camion camion;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;
}
