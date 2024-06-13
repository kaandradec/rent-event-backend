package com.rentevent.model.utileria;

import com.rentevent.model.evento.EventoUtileria;
import com.rentevent.model.proveedor.Proveedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utileria")
public class Utileria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_utileria")
    @SequenceGenerator(name = "seq_utileria", sequenceName = "seq_utileria", allocationSize = 1)
    @Column(name = "util_id")
    private Integer id;

    @Column(name = "util_nombre", nullable = false)
    private String nombre;

    @Column(name = "util_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "util_cantidad", nullable = false)
    private String cantidad;

    @Column(name = "util_precio_alquiler", nullable = false)
    private String precioAlquiler;

    @Column(name = "util_precio_proveedor", nullable = false)
    private String precioProveedor;

    @OneToMany(mappedBy = "utileria")
    private List<EventoUtileria> eventoUtilerias;
    @ManyToOne
    @JoinColumn(name = "prov_id", nullable = false)
    private Proveedor proveedor;
}
