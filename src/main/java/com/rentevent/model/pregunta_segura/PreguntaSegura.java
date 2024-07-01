package com.rentevent.model.pregunta_segura;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.usuario.Usuario;
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
@Table(name = "pregunta_segura")
public class PreguntaSegura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pregunta_segura")
    @SequenceGenerator(name = "seq_pregunta_segura", sequenceName = "seq_pregunta_segura", allocationSize = 1)
    @Column(name = "pseg_id")
    private Integer id;

    @Column(name = "pseg_pregunta", nullable = false)
    private String pregunta;

    @Column(name = "pseg_respuesta", nullable = false)
    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "usua_id", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "clie_id", nullable = true)
    private Cliente cliente;

    @Transient
    public static final String[] preguntasSeguras= new String[]{
            "¿Cuál es el nombre de tu primer mascota?",
            "¿En qué ciudad naciste?",
            "¿Cuál es el nombre de tu escuela primaria?",
            "¿Cuál es el nombre de tu mejor amigo de la infancia?",
            "¿Cuál es el nombre de tu comida favorita?",
            "¿Cuál es tu libro favorito?",
            "¿Cuál es el nombre de tu primer maestro?",
            "¿Cuál es el nombre de tu primer amor?",
            "¿En qué ciudad conociste a tu pareja?",
            "¿Cuál es el nombre de tu abuela materna?"
    };

    @Override
    public String toString() {
        return "PreguntaSegura{" +
                "id=" + id +
                ", pregunta='" + pregunta + '\'' +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}

