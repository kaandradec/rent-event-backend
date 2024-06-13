package com.rentevent.model.servicio;

import com.rentevent.model.evento.EventoServicio;
import com.rentevent.model.imagen.Imagen;
import com.rentevent.model.proveedor.Proveedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data // La anomación @Data es un Project Lombok que genera los métodos getter, setter, equals, hashcode y toString
@Builder // La anomación @Builder es un Project Lombok que genera un constructor con todos los argumentos
@NoArgsConstructor // La anomación @NoArgsConstructor es un Project Lombok que genera un constructor sin argumentos
@AllArgsConstructor
// La anomación @AllArgsConstructor es un Project Lombok que genera un constructor con todos los argumentos
@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @Column(name = "serv_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servicio")
    @SequenceGenerator(name = "seq_servicio", sequenceName = "seq_servicio", allocationSize = 1)
    private Integer id;
    @Column(name = "serv_codigo")
    private String codigo;
    @Column(name = "serv_nombre")
    private String nombre;
    @Column(name = "serv_tipo")
    private String tipo;
    @Column(name = "serv_costo")
    private BigDecimal costo;
    @Column(name = "serv_descripcion")
    private String descripcion;
    @Column(name = "serv_imagen")
    private String imagen;//todo reemplazar el mock

    @OneToMany(mappedBy = "servicio")
    private List<Imagen> imagenes;
    @OneToMany(mappedBy = "servicio")
    private List<EventoServicio> eventos;
    @ManyToOne
    @JoinColumn(name = "prov_id", nullable = false)
    private Proveedor proveedor;

}
