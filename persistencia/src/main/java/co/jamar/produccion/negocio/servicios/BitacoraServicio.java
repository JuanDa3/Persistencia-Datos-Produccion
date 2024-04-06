package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.BitacoraRequestDTO;

import java.time.LocalDate;
import java.util.Date;

public interface BitacoraServicio {

    void existeBitacoraPrincipalFecha(LocalDate fecha);

    void guardarBitacota(BitacoraRequestDTO bitacoraRequestDTO) throws Exception;
}
