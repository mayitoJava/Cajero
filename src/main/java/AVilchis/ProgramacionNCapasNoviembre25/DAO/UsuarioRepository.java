package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByUsername(String username);
}
