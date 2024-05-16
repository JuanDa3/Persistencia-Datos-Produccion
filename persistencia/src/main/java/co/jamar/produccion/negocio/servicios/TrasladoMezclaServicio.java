package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.TrasladoMezclaDTO;

import java.time.LocalDate;

public interface TrasladoMezclaServicio {
    void guardarTrasladoMezcla(TrasladoMezclaDTO trasladoMezclaDTO, LocalDate fecha);
}
