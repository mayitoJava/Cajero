package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Denominacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenominacionRepository extends JpaRepository<Denominacion, Integer> {

    List<Denominacion> findAllByOrderByDenominacionDesc();

}
