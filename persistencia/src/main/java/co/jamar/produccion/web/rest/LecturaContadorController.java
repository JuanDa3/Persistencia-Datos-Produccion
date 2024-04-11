package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.LecturaContadorServicio;
import co.jamar.produccion.web.dto.LecturaContadorRequestDTO;
import co.jamar.produccion.web.dto.Mensaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/lectura-contador")
public class LecturaContadorController {

    private final LecturaContadorServicio lecturaContadorServicio;

    public LecturaContadorController(LecturaContadorServicio lecturaContadorServicio) {
        this.lecturaContadorServicio = lecturaContadorServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarLecturaContador(@RequestBody LecturaContadorRequestDTO lecturaContadorRequestDTO){
        lecturaContadorServicio.guardarLecturaContador(lecturaContadorRequestDTO);
        return  ResponseEntity.status(201).body(new Mensaje("La Lectura del Contador se registró correctamente"));
    }
}
