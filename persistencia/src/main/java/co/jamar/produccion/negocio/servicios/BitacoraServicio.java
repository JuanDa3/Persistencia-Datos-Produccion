package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.persistencia.entidades.Bitacora;
import co.jamar.produccion.web.dto.BitacoraRequestDTO;

public interface BitacoraServicio {

    Bitacora guardarBitacora(BitacoraRequestDTO bitacoraRequestDTO) throws Exception;
    void validarExisteBitacora(int numeroBitacora) throws Exception;
}
