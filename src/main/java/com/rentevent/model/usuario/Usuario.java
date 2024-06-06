package com.rentevent.model.usuario;

import com.rentevent.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"usua_usuario"})})
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "usua_id")
    private Integer id;
    @Column(name = "usua_usuario", nullable = false)
    private String username;
    @Column(name = "usua_apellido")
    private String lastname;
    @Column(name = "usua_nombre")
    private String firstname;
    @Column(name = "usua_contrasenia")
    private String password;
    @Column(name = "usua_sueldo")
    private BigDecimal sueldo;
    @Column(name = "usua_fecha_incorporacion")
    private LocalDate fechaIncormporacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "usua_rol")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
}
