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

    @Column(name = "serv_codigo")
    private String codigo;

    @Column(name = "even_nombre")
    private String nombre;

    @Column(name = "even_estado")
    private String estado; // ACTIVO CANCELADO COMPLETADO

    @Column(name = "even_isPagado")
    private Boolean isPagado;

    @Column(name = "even_fecha")
    private LocalDate fecha;

    @Column(name = "even_pais")
    private String pais;

    @Column(name = "even_region")
    private String region;

    @Column(name = "even_precio")
    private BigDecimal precio;

    @Column(name = "even_iva")
    private BigDecimal iva;

    @Column(name = "even_calle_principal")
    private String callePrincipal;

    @Column(name = "even_calle_secundaria")
    private String calleSecundaria;

    @Column(name = "even_referencia_direccion")
    private String referenciaDireccion;

    @Column(name = "even_hora")
    private LocalTime hora;
//
//    @Column(name = "even_duracion")
//    private LocalTime duracion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inci_id")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "clie_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<CamionEvento> camionEventos;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<EventoTransporte> eventoTransportes;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<EventoUtileria> eventoUtilerias;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<EventoServicio> eventoServicios;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<EventoPatrocinador> eventoPatrocinadores;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Resenia> resenias;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    @Override
    public String toString() {
        return "Evento{" +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", isPagado=" + isPagado +
                ", fecha=" + fecha +
                ", pais='" + pais + '\'' +
                ", region='" + region + '\'' +
                ", precio=" + precio +
                ", iva=" + iva +
                ", callePrincipal='" + callePrincipal + '\'' +
                ", calleSecundaria='" + calleSecundaria + '\'' +
                ", referenciaDireccion='" + referenciaDireccion + '\'' +
                ", hora=" + hora +
                '}';
    }
}