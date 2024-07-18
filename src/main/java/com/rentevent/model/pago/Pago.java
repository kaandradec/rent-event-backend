package com.rentevent.model.pago;

import com.rentevent.model.enums.MetodoPago;
import com.rentevent.model.enums.Rol;
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

    @Column(name = "pago_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "pago_monto")
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "pago_metodo")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "even_id")
    private Evento evento;
    @ManyToOne
    @JoinColumn(name = "fact_id")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "tarjeta_id")
    private Tarjeta tarjeta;
}
