package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.web.dto.ControlCementoRequestDTO;

public interface ControlCementoServicio {
    int saldoCemento();

    void guardarControlCemento(ControlCementoRequestDTO controlCementoRequestDTO);
}
