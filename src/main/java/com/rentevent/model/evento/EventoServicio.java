package com.rentevent.model.evento;

import com.rentevent.model.servicio.Servicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento_servicio")
public class EventoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento_servicio")
    @SequenceGenerator(name = "seq_evento_servicio", sequenceName = "seq_evento_servicio", allocationSize = 1)
    @Column(name = "even_serv_id")
    private Integer id;

//    @Column(name = "even_serv_personalizacion")
//    private String personalizacion;

    @Column(name = "even_serv_cantidad")
    private Integer cantidad;
//
//    @Column(name = "even_serv_costo")
//    private BigDecimal costo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "even_id")
    private Evento evento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serv_id")
    private Servicio servicio;
}
