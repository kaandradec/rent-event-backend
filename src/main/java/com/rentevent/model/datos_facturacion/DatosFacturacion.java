package com.rentevent.model.datos_facturacion;

import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.pago.Pago;
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
@Table(name = "datos_facturacion")
public class DatosFacturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_datos_factura")
    @SequenceGenerator(name = "seq_datos_factura", sequenceName = "seq_datos_factura", allocationSize = 1)
    @Column(name = "dafa_id")
    private Integer id;
    @Column(name = "dafa_nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(name = "dafa_cedula_cliente", nullable = false)
    private String cedulaCliente;

    @Column(name = "dafa_direccion_cliente", nullable = false)
    private String direccionCliente;

    @OneToOne(mappedBy = "datosFacturacion", cascade = CascadeType.ALL)
    private Cliente cliente;


}
