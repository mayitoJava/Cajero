package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Denominacion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.Service.CajeroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CajeroController {

    @Autowired
    private CajeroService cajeroService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cajero")
    public String cajero(Model model) {

        List<Denominacion> lista = cajeroService.obtenerDenominaciones();

        model.addAttribute("denominaciones", lista);

        return "cajero";
    }

    @PostMapping("/retirar")
    public String retirar(@RequestParam double monto, Model model) {

        Result result = cajeroService.retirar(monto);

        model.addAttribute("resultado", result);

        List<Denominacion> lista = cajeroService.obtenerDenominaciones();

        model.addAttribute("denominaciones", lista);

        return "cajero";
    }

}
