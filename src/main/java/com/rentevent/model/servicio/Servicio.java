package com.rentevent.model.servicio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // La anomación @Data es un Project Lombok que genera los métodos getter, setter, equals, hashcode y toString
@Builder // La anomación @Builder es un Project Lombok que genera un constructor con todos los argumentos
@NoArgsConstructor // La anomación @NoArgsConstructor es un Project Lombok que genera un constructor sin argumentos
@AllArgsConstructor // La anomación @AllArgsConstructor es un Project Lombok que genera un constructor con todos los argumentos
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
    @Column(name = "serv_precio")
    private BigDecimal precio;
    @Column(name = "serv_descripcion")
    private String descripcion;
    @Column(name = "serv_personalizacion") //TODO QUITAR PERSONALIZACION Y PONERLO EN LA TABLA Evento_Servicio
    private String personalizacion;
    @Column(name = "serv_imagen")
    private String imagen;

}
