package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.CriterioAceptacionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/criterio-aceptacion")
public class CriterioAceptacionController {

    private final CriterioAceptacionServicio criterioAceptacionServicio;

    public CriterioAceptacionController(CriterioAceptacionServicio criterioAceptacionServicio) {
        this.criterioAceptacionServicio = criterioAceptacionServicio;
    }

    @GetMapping("/producto")
    public ResponseEntity<?>obtenerCriteriosAceptacionPorProducto(@RequestParam String nombreProducto){
        criterioAceptacionServicio.findCriteriosByNombreProducto(nombreProducto);
        return null;
    }
}
