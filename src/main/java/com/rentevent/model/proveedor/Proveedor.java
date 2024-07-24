package com.rentevent.model.proveedor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rentevent.model.camion.Camion;
import com.rentevent.model.servicio.Servicio;
import com.rentevent.model.transporte.Transporte;
import com.rentevent.model.utileria.Utileria;
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
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proveedor")
    @SequenceGenerator(name = "seq_proveedor", sequenceName = "seq_proveedor", allocationSize = 1)
    @Column(name = "prov_id")
    private Integer id;

    @Column(name = "prov_nombre", nullable = false)
    private String nombre;

    @Column(name = "prov_producto")
    private String producto; // TODO es util este campo?

    @OneToMany(mappedBy = "proveedor")
    private List<Camion> camiones;
    @OneToMany(mappedBy = "proveedor")
    private List<Transporte> transportes;
//    @OneToMany(mappedBy = "proveedor")
//    private List<Utileria> utilerias;
    @OneToMany(mappedBy = "proveedor")
    @JsonBackReference
    private List<Servicio> servicios;
}