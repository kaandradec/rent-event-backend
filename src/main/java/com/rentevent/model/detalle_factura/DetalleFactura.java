package com.rentevent.model.detalle_factura;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.factura.Factura;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.model.transporte.Transporte;
import com.rentevent.model.utileria.Utileria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "defactura_servicio",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "serv_id")
    )
    private Set<Servicio> servicios = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "defactura_utileria",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "util_id")
    )
    private Set<Utileria> utilerias = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "defactura_transporte",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "tran_id")
    )
    private Set<Transporte> transportes = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "defactura_camion",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "cami_id")
    )
    private Set<Camion> camiones = new HashSet<>();
}