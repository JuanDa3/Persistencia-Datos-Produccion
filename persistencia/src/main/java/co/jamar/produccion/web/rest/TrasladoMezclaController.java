package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.TrasladoMezclaServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.TrasladoMezclaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produccion/traslado-mezcla")
public class TrasladoMezclaController {

    private final TrasladoMezclaServicio trasladoMezclaServicio;

    public TrasladoMezclaController(TrasladoMezclaServicio trasladoMezclaServicio) {
        this.trasladoMezclaServicio = trasladoMezclaServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarTrasladoMezcla(@RequestBody TrasladoMezclaDTO trasladoMezclaRequestDTO) {
        trasladoMezclaServicio.guardarTrasladoMezcla(trasladoMezclaRequestDTO);
        return  ResponseEntity.status(201).body(new Mensaje("El traslado Mezcla se registró correctamente"));
    }
}
