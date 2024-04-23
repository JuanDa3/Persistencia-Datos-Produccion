package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.DatosService;
import co.jamar.produccion.web.dto.DatosProduccionRequestDTO;
import co.jamar.produccion.web.dto.Mensaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/persistencia")
public class PersistenciaController {

    private final DatosService datosService;

    public PersistenciaController(DatosService datosService) {
        this.datosService = datosService;
    }

    @PostMapping
    public ResponseEntity<?>guardarDatosProduccion(@RequestBody DatosProduccionRequestDTO datosProduccionRequestDTO){
        try {
            System.out.println(datosProduccionRequestDTO);
            datosService.guardarDatosProduccion(datosProduccionRequestDTO);
            return  ResponseEntity.status(201).body(new Mensaje("La Produccion se registró correctamente"));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
