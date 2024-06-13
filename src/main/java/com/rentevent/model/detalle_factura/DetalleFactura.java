package com.rentevent.model.detalle_factura;

import com.rentevent.model.factura.Factura;
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
@Table(name = "detalle_factura")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "defa_id")
    private Integer id;

    @Column(name = "defa_precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "defa_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "defa_sub_total", nullable = false)
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "fact_id", nullable = false)
    private Factura factura;
}