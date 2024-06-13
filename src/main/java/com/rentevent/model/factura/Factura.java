package com.rentevent.model.factura;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.detalle_factura.DetalleFactura;
import com.rentevent.model.pago.Pago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_factura")
    @SequenceGenerator(name = "seq_factura", sequenceName = "seq_factura", allocationSize = 1)
    @Column(name = "fact_id")
    private Integer id;

    @Column(name = "fact_fecha_emision", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @Column(name = "fact_monto_total", nullable = false)
    private Double total;

    @Column(name = "fact_numero", nullable = false)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "clie_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura")
    private List<Pago> pagos;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalleFacturas;
}
