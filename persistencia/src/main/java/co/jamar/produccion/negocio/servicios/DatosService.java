package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.DatosProduccionRequestDTO;

public interface DatosService {
    void guardarDatosProduccion(DatosProduccionRequestDTO datosProduccionRequestDTO) throws Exception;
}
