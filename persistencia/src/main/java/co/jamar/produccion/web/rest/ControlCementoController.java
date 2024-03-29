package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.ControlCementoServicio;
import co.jamar.produccion.web.dto.Mensaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/control-cemento")
public class ControlCementoController {

    private final ControlCementoServicio controlCementoServicio;


    public ControlCementoController(ControlCementoServicio controlCementoServicio) {
        this.controlCementoServicio = controlCementoServicio;

    }

    @GetMapping("/saldo")
    public ResponseEntity<?>obtenerSaldoCemento(){
        try {
            return ResponseEntity.status(200).body(controlCementoServicio.saldoCemento());
        }catch (Exception e){
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
