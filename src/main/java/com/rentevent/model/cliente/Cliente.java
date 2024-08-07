package com.rentevent.model.cliente;

import com.rentevent.model.datos_facturacion.DatosFacturacion;
import com.rentevent.model.enums.Genero;
import com.rentevent.model.enums.Rol;
import com.rentevent.model.evento.Evento;
import com.rentevent.model.factura.Factura;
import com.rentevent.model.pregunta_segura.PreguntaSegura;
import com.rentevent.model.tarjeta.Tarjeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(columnNames = {"clie_usuario"})})
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    @SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "clie_id")
    private Integer id;
    @Column(name = "clie_nombre")
    private String nombre;
    @Column(name = "clie_apellido")
    private String apellido;
    @Column(name = "clie_contrasenia")
    private String contrasenia;
    @Column(name = "clie_telefono")
    private String telefono;
    @Column(name = "clie_pais")
    private String pais;
    @Column(name = "clie_region")
    private String region;
//    @Column(name = "clie_calle_principal")
//    private String callePrincipal;
//    @Column(name = "clie_calle_secundaria")
//    private String calleSecundaria;
//    @Column(name = "clie_referencia_direccion")
//    private String referenciaDireccion;
    @Column(name = "clie_prefijo")
    private String prefijo;
    @Column(name = "clie_correo")
    private String correo;
    @Enumerated(EnumType.STRING)
    @Column(name = "clie_genero")
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "clie_rol")
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "dafa_id", unique = true)
    private DatosFacturacion datosFacturacion;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarjeta> tarjetas;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaSegura> preguntaSeguras;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pais='" + pais + '\'' +
                ", region='" + region + '\'' +
                ", prefijo='" + prefijo + '\'' +
                ", correo='" + correo + '\'' +
                ", genero=" + genero +
                ", rol=" + rol +
                ", datosFacturacion=" + datosFacturacion.getNombreCliente() +
                ", facturas=" + facturas.size() +
                ", tarjetas=" + tarjetas.size() +
                ", eventos=" + eventos.size() +
                ", preguntaSeguras=" + preguntaSeguras.size() +
                '}';
    }
}
