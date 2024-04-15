package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.ControlCementoServicio;
import co.jamar.produccion.web.dto.ControlCementoRequestDTO;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.SaldoCementoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            int saldo = controlCementoServicio.saldoCemento();
            SaldoCementoDTO saldoCementoDTO = new SaldoCementoDTO();
            saldoCementoDTO.setSaldo(saldo);
            return ResponseEntity.ok(saldoCementoDTO);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?>guardarControlCemento(@RequestBody ControlCementoRequestDTO controlCementoRequestDTO){
        try {
            controlCementoServicio.guardarControlCemento(controlCementoRequestDTO);
            return  ResponseEntity.status(201).body(new Mensaje("El Control Cemento se registró correctamente"));
        }catch (Exception e){
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
