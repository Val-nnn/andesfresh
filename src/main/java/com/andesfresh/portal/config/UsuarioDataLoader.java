package com.andesfresh.portal.config;
import com.andesfresh.portal.modelo.Rol;
import com.andesfresh.portal.modelo.Usuario;
import com.andesfresh.portal.repositorio.RolRepository;
import com.andesfresh.portal.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class UsuarioDataLoader implements CommandLineRunner {
    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;
    public UsuarioDataLoader(RolRepository rolRepo, UsuarioRepository usuarioRepo,
                             PasswordEncoder encoder) {
        this.rolRepo = rolRepo;
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
    }
    @Override
    public void run(String... args) {
        Rol admin = rolRepo.save(new Rol("ADMIN"));
        Rol vendedor = rolRepo.save(new Rol("VENDEDOR"));
        crear("admin", "Admin2026", admin);
        crear("vendedor", "Venta2026", vendedor);

        String hash = usuarioRepo.findByUsername("admin").get().getPassword();
        System.out.println(">>> Hash guardado para admin: " + hash);
        System.out.println(">>> ¿Admin2026 es correcta? " + encoder.matches("Admin2026", hash));   
        System.out.println(">>> ¿admin2026 es correcta? " + encoder.matches("admin2026", hash));
    }
    private void crear(String username, String clave, Rol rol) {
        Usuario u = new Usuario(username, encoder.encode(clave));   
        u.getRoles().add(rol);
        usuarioRepo.save(u);
    }
}