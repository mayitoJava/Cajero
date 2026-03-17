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

    @Autowired
    private DenominacionRepository denominacionRepository;

    public Result retirar(double monto) {

        Result result = new Result();

        try {

            List<Denominacion> lista
                    = denominacionRepository.findAllByOrderByDenominacionDesc();

            Map<Double, Integer> retiro = new LinkedHashMap<>();

            double montoRestante = monto;

            for (Denominacion d : lista) {

                int usar = (int) (montoRestante / d.getDenominacion());

                if (usar > d.getCantidad()) {
                    usar = d.getCantidad();
                }

                if (usar > 0) {

                    retiro.put(d.getDenominacion(), usar);

                    montoRestante -= usar * d.getDenominacion();
                    montoRestante = Math.round(montoRestante * 100.0) / 100.0;
                    d.setCantidad(d.getCantidad() - usar);
                    denominacionRepository.save(d);
                }

            }

            if (montoRestante > 0) {

                result.Correct = false;
                result.ErrorMessage
                        = "El cajero no tiene suficientes denominaciones";

            } else {

                result.Correct = true;
                result.Object = retiro;

            }

        } catch (Exception ex) {

            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;
    }

    public List<Denominacion> obtenerDenominaciones() {

        return denominacionRepository.findAllByOrderByDenominacionDesc();

    }

}
