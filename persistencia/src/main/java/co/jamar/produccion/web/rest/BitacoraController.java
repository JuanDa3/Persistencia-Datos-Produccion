package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.BitacoraServicio;
import co.jamar.produccion.web.dto.BitacoraRequestDTO;
import co.jamar.produccion.web.dto.Mensaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/produccion/bitacora")
public class BitacoraController {

    private final BitacoraServicio bitacoraServicio;

    public BitacoraController(BitacoraServicio bitacoraServicio) {
        this.bitacoraServicio = bitacoraServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarBitacora(@RequestBody BitacoraRequestDTO bitacoraRequestDTO){
        try {
            bitacoraServicio.guardarBitacota(bitacoraRequestDTO);
            return  ResponseEntity.status(201).body(new Mensaje("La Bitacora se registró correctamente"));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
