package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.ProduccionServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.ProduccionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/produccion/produccion")
public class ProduccionController {
    private final ProduccionServicio produccionServicio;

    public ProduccionController(ProduccionServicio produccionServicio) {
        this.produccionServicio = produccionServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarProduccion(@RequestBody ProduccionRequestDTO produccionRequestDTO){
        try {
            HashMap<String, Double> estadisticasProduccion = produccionServicio.guardarProduccion(produccionRequestDTO);
            return  ResponseEntity.status(201).body( estadisticasProduccion);
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
