package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.ProduccionRequestDTO;

public interface ProduccionServicio {
    void guardarProduccion(ProduccionRequestDTO produccionRequestDTO) throws Exception;
}
