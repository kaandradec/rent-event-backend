package com.rentevent.model.evento;

import com.rentevent.model.camion.CamionEvento;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.incidencia.Incidencia;
import com.rentevent.model.pago.Pago;
import com.rentevent.model.resenia.Resenia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento")
    @SequenceGenerator(name = "seq_evento", sequenceName = "seq_evento", allocationSize = 1)
    @Column(name = "even_id")
    private Integer id;

    @Column(name = "even_nombre", nullable = false)
    private String nombre;

    @Column(name = "even_fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "even_pais", nullable = false)
    private String pais;

    @Column(name = "even_region", nullable = false)
    private String region;

    @Column(name = "even_precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "even_iva")
    private BigDecimal iva;

    @Column(name = "even_calle_principal", nullable = false)
    private String callePrincipal;

    @Column(name = "even_calle_secundaria", nullable = false)
    private String calleSecundaria;

    @Column(name = "even_referencia_direccion", nullable = false)
    private String referenciaDireccion;

    @Column(name = "even_hora", nullable = false)
    private LocalTime hora;

    @Column(name = "even_duracion", nullable = false)
    private LocalTime duracion;

    @OneToOne
    @JoinColumn(name = "inci_id")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "clie_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "evento")
    private List<CamionEvento> camionEventos;

    @OneToMany(mappedBy = "evento")
    private List<EventoTransporte> eventoTransportes;

    @OneToMany(mappedBy = "evento")
    private List<EventoUtileria> eventoUtilerias;

    @OneToMany(mappedBy = "evento")
    private List<EventoServicio> eventoServicios;

    @OneToMany(mappedBy = "evento")
    private List<EventoPatrocinador> eventoPatrocinadores;

    @OneToMany(mappedBy = "evento")
    private List<Resenia> resenias;

    @OneToMany(mappedBy = "evento")
    private List<Pago> pagos;
}