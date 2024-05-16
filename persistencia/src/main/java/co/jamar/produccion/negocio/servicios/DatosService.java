package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.DatosProduccionRequestDTO;

import java.util.HashMap;

public interface DatosService {
    HashMap<String, Double> guardarDatosProduccion(DatosProduccionRequestDTO datosProduccionRequestDTO) throws Exception;
}
