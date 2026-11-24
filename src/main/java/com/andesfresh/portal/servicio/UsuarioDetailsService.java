package com.andesfresh.portal.servicio;
import com.andesfresh.portal.modelo.Rol;
import com.andesfresh.portal.modelo.Usuario;
import com.andesfresh.portal.repositorio.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UsuarioDetailsService implements UserDetailsService {   
    private final UsuarioRepository repo;
    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String[] roles = u.getRoles().stream()
            .map(Rol::getNombre)
            .toArray(String[]::new);

        return User.withUsername(u.getUsername())
            .password(u.getPassword())
            .roles(roles)                 
            .disabled(!u.isActivo())
            .build();
    }
}