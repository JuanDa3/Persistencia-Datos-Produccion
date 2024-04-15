package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.PruebaServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.PruebaRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/prueba-cilindro")
public class PruebaController {

    private final PruebaServicio pruebaServicio;

    public PruebaController(PruebaServicio pruebaServicio) {
        this.pruebaServicio = pruebaServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarPrueba(@RequestBody PruebaRequestDTO pruebaRequestDTO) {
        try {
            pruebaServicio.guardarPrueba(pruebaRequestDTO);
            return  ResponseEntity.status(201).body(new Mensaje("La Prueba del cilindro se registró correctamente"));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
