package com.rentevent.model.camion;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.transporte.Transporte;
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
@Table(name = "camion")
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_camion")
    @SequenceGenerator(name = "seq_camion", sequenceName = "seq_camion", allocationSize = 1)
    @Column(name = "cami_id")
    private Integer id;

    @Column(name = "cami_matricula", nullable = false)
    private String matricula;

    @Column(name = "cami_marca", nullable = false)
    private String marca;

    @Column(name = "cami_modelo", nullable = false)
    private String modelo;

    @Column(name = "cami_tipo", nullable = false)
    private String tipo;

    @Column(name = "cami_capacidad", nullable = false)
    private Double capacidad;

    @ManyToOne
    @JoinColumn(name = "prov_id", nullable = false)
    private Proveedor proveedor;
    @OneToMany(mappedBy = "camion")
    private List<CamionEvento> camionEventos;

    @Override
    public String toString() {
        return "Camion{" +
                ", matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", capacidad=" + capacidad +
                ", proveedor=" + proveedor.getNombre() +
                '}';
    }
}