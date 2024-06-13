package com.rentevent.model.pago;

import com.rentevent.model.evento.Evento;
import com.rentevent.model.tarjeta.Tarjeta;
import com.rentevent.model.factura.Factura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pago")
    @SequenceGenerator(name = "seq_pago", sequenceName = "seq_pago", allocationSize = 1)
    @Column(name = "pago_id")
    private Integer id;

    @Column(name = "pago_fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "pago_monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "pago_metodo", nullable = false)
    private String metodo;

    @ManyToOne
    @JoinColumn(name = "even_id", nullable = false)
    private Evento evento;
    @ManyToOne
    @JoinColumn(name = "fact_id", nullable = false)
    private Factura factura;
    @OneToOne
    @JoinColumn(name = "tarjeta_id", unique = true)
    private Tarjeta tarjeta;
}
