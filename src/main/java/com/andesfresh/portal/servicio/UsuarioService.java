package com.andesfresh.portal.servicio;
import com.andesfresh.portal.modelo.Rol;
import com.andesfresh.portal.modelo.Usuario;
import com.andesfresh.portal.repositorio.RolRepository;
import com.andesfresh.portal.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder encoder;
    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo,
                          PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.encoder = encoder;
    }
    public void registrar(String username, String clave) {
        if (usuarioRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
        if (clave.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        Rol vendedor = rolRepo.findByNombre("VENDEDOR").orElseThrow();
        Usuario u = new Usuario(username, encoder.encode(clave));   // casilla 9
        u.getRoles().add(vendedor);
        usuarioRepo.save(u);
    }
    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }
}