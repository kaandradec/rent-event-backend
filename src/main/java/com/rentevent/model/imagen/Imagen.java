package com.rentevent.model.imagen;

import com.rentevent.model.servicio.Servicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imagen")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagen")
    @SequenceGenerator(name = "seq_imagen", sequenceName = "seq_imagen", allocationSize = 1)
    @Column(name = "img_id")
    private Integer id;

    @Column(name = "img_url", nullable = false)
    private String url;

    @Column(name = "img_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "img_etiqueta", nullable = false)
    private String etiqueta;

    @ManyToOne
    @JoinColumn(name = "img_serv_id")
    private Servicio servicio;
}
