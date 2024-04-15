package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.TiempoParadaMaquinaServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.TiempoParadaMaquinaRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/produccion/tiempos-parada-maquina")
public class TiempoParadaMaquinaController {

    private final TiempoParadaMaquinaServicio tiempoParadaMaquinaServicio;

    public TiempoParadaMaquinaController(TiempoParadaMaquinaServicio tiempoParadaMaquinaServicio) {
        this.tiempoParadaMaquinaServicio = tiempoParadaMaquinaServicio;
    }

    @PostMapping
    public ResponseEntity<?> guardarTiemposParadaMaquina(@RequestBody List<TiempoParadaMaquinaRequestDTO> tiemposParadaMaquinaDTO) {
        try {
            tiempoParadaMaquinaServicio.guardarTiemposParadaMaquina(tiemposParadaMaquinaDTO);
            return  ResponseEntity.status(201).body(new Mensaje("Los tiempos parada maquina se registraron correctamente"));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
