package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.TiempoParadaMaquinaRequestDTO;

import java.util.List;

public interface TiempoParadaMaquinaServicio {
    void guardarTiemposParadaMaquina(List<TiempoParadaMaquinaRequestDTO> tiemposParadaMaquinaDTO);
}
