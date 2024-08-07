package com.rentevent.model.factura;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.detalle_factura.DetalleFactura;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.pago.Pago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "fact_fecha_emision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @Column(name = "fact_monto_total")
    private BigDecimal total;

    @Column(name = "fact_iva")
    private BigDecimal iva;

    @Column(name = "fact_numero")
    @SequenceGenerator(name = "seq_num_factura", sequenceName = "seq_num_factura", allocationSize = 1)
    private String numero;

    @Column(name = "fact_nombre_cliente")
    private String nombreCliente;

    @Column(name = "fact_cedula_cliente")
    private String cedulaCliente;

    @Column(name = "fact_direccion_cliente")
    private String direccionCliente;

    @Column(name = "fact_empresa")
    private String empresa;

    @Column(name = "fact_direccion_empresa")
    private String direccionEmpresa;

    @Column(name = "fact_RUC_empresa")
    private String rucEmpresa;

    @ManyToOne
    @JoinColumn(name = "clie_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura")
    private List<Pago> pagos;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalleFacturas;
}
