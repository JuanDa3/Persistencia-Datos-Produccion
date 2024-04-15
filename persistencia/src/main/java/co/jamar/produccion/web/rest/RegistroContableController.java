package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.RegistroContableServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.RegistroContableRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/registro-contable")
public class RegistroContableController {

    private final RegistroContableServicio registroContableServicio;

    public RegistroContableController(RegistroContableServicio registroContableServicio) {
        this.registroContableServicio = registroContableServicio;
    }

    @PostMapping
    public ResponseEntity<?> guardarRegistroContable(@RequestBody RegistroContableRequestDTO registroContableRequestDTO) {
        try {
            registroContableServicio.guardarRegistroContable(registroContableRequestDTO);
            return  ResponseEntity.status(201).body(new Mensaje("Registro Contable asignado correctamente"));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
