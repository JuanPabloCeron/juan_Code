package com.jpceron.CarRegistry.entity;

import jakarta.persistence.*;
import lombok.*;
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
@ToString
@Table(name="users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String surname;

    @Column(unique = true) // es para que dos usuarios no tengan el mismo email, ya que se usara para la autenticacion
    String email;

    String password;

    String image;

    String role;

    //----------------------------------------------------------------------------
    // Al implementar la Clase UserDetail nos permite acceder a los siguientes metodos

    //Nos  devuelve una lista de roles que tenga activos
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    // Nos da el nombre del usuario
    @Override
    public String getUsername() {
        return email;
    }

    //Nos informa si la cuenta esta caducada o no
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    //Nos infoma si esta bloqueada o no
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    // Nos informa si las credenciales han caducado
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    //Nos infoma si esta activo el usuario
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}
