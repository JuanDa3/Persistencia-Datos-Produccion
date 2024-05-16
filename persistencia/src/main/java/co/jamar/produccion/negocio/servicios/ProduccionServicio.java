package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.ProduccionRequestDTO;

import java.util.HashMap;

public interface ProduccionServicio {
    HashMap<String, Double> guardarProduccion(ProduccionRequestDTO produccionRequestDTO) throws Exception;
}
