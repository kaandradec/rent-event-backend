package com.rentevent.model.tarjeta;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pago.Pago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarjeta")
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarjeta")
    @SequenceGenerator(name = "seq_tarjeta", sequenceName = "seq_tarjeta", allocationSize = 1)
    @Column(name = "tarj_id")
    private Integer id;

    @Column(name = "tarj_token", nullable = false)
    private String token;

    @Column(name = "tarj_tipo", nullable = false)
    private String tipo;

    @Column(name = "tarj_nombre", nullable = false)
    private String nombre;

    @Column(name = "tarj_digitos", nullable = false)
    private String digitos;

    @Column(name = "tarj_fecha_expiracion", nullable = false)
    private Date fechaExpiracion;

    @Column(name = "tarj_proveedor", nullable = false)
    private String proveedor;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @OneToOne(mappedBy = "tarjeta", cascade = CascadeType.ALL)
    private Pago pago;
}

