package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.ProduccionServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.ProduccionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/produccion/produccion")
public class ProduccionController {
    private final ProduccionServicio produccionServicio;

    public ProduccionController(ProduccionServicio produccionServicio) {
        this.produccionServicio = produccionServicio;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarProduccion(@PathVariable int id){
        try {
            produccionServicio.eliminarProduccion(id);
            return ResponseEntity.ok().body("Producción eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la producción: " + e.getMessage());
        }
    }
}
