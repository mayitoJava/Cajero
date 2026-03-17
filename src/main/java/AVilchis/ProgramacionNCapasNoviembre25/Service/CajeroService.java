package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.DenominacionRepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Denominacion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService {
// Inyección automática del repositorio (Spring)
// Permite acceder a la base de datos sin instanciar manualmente
@Autowired
private DenominacionRepository denominacionRepository;


// Método para retirar dinero del cajero
public Result retirar(double monto) {

    // Objeto que se usará para devolver el resultado (éxito o error)
    Result result = new Result();

    try {

        // Obtiene todas las denominaciones (billetes/monedas)
        // ordenadas de mayor a menor (ej: 1000, 500, 200...)
        List<Denominacion> lista
                = denominacionRepository.findAllByOrderByDenominacionDesc();

        // Mapa donde se guardará qué billetes se entregan
        // Ejemplo: {500=2, 200=1}
        Map<Double, Integer> retiro = new LinkedHashMap<>();

        // Variable que irá disminuyendo conforme se arma el retiro
        double montoRestante = monto;

        // Recorre cada denominación disponible
        for (Denominacion d : lista) {

            // Calcula cuántos billetes de esta denominación puede usar
            int usar = (int) (montoRestante / d.getDenominacion());

            // Si intenta usar más de los que hay en el cajero,
            // se limita a la cantidad disponible
            if (usar > d.getCantidad()) {
                usar = d.getCantidad();
            }

            // Si sí se usará al menos un billete
            if (usar > 0) {

                // Guarda en el mapa cuántos billetes se usarán
                retiro.put(d.getDenominacion(), usar);

                // Resta el dinero usado al monto restante
                montoRestante -= usar * d.getDenominacion();

                // Redondea a 2 decimales (para evitar errores con decimales)
                montoRestante = Math.round(montoRestante * 100.0) / 100.0;

                // Actualiza la cantidad de billetes en el "cajero"
                d.setCantidad(d.getCantidad() - usar);

                // Guarda el cambio en la base de datos
                denominacionRepository.save(d);
            }

        }

        // Si al final aún queda dinero por cubrir
        if (montoRestante > 0) {

            // Significa que no había suficientes billetes
            result.Correct = false;
            result.ErrorMessage
                    = "El cajero no tiene suficientes denominaciones";

        } else {

            // Si todo salió bien
            result.Correct = true;

            // Se devuelve el detalle del retiro (qué billetes se dieron)
            result.Object = retiro;

        }

    } catch (Exception ex) {

        // En caso de error inesperado
        result.Correct = false;
        result.ErrorMessage = ex.getLocalizedMessage();
        result.ex = ex;

    }

    // Retorna el resultado final
    return result;
}
}
