package com.rentevent.model.servicio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rentevent.model.enums.EstadoServicio;
import com.rentevent.model.enums.TipoServicio;
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
    @Column(name = "serv_estado")
    private String estado;
    @Column(name = "serv_costo")
    private BigDecimal costo;
    @Column(name = "serv_descripcion", length = 500)
    private String descripcion;
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Imagen> imagenes;
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<EventoServicio> eventos;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prov_id")
    @JsonManagedReference
    private Proveedor proveedor;
}