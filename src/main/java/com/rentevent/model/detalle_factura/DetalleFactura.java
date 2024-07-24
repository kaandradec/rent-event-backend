package com.rentevent.model.detalle_factura;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.evento.Evento;
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

    @Column(name = "defa_precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "defa_cantidad")
    private Integer cantidad;

    @Column(name = "defa_sub_total")
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "fact_id")
    private Factura factura;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.ALL })
    @JoinTable(
            name = "defactura_servicio",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "serv_id")
    )
    private Set<Servicio> servicios = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.ALL })
    @JoinTable(
            name = "defactura_transporte",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "tran_id")
    )
    private Set<Transporte> transportes = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.ALL })
    @JoinTable(
            name = "defactura_camion",
            joinColumns = @JoinColumn(name = "defa_id"),
            inverseJoinColumns = @JoinColumn(name = "cami_id")
    )
    private Set<Camion> camiones = new HashSet<>();

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "even_id")
    private Evento evento;

}