package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.persistencia.entidades.Bitacora;
import co.jamar.produccion.web.dto.ProduccionRequestDTO;

import java.util.HashMap;

public interface ProduccionServicio {
    HashMap<String, Double> guardarProduccion(Bitacora bitacoraGuardada, ProduccionRequestDTO produccionRequestDTO) throws Exception;

    void eliminarProduccion(int id);
}
