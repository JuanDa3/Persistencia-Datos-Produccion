package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.LecturaContadorServicio;
import co.jamar.produccion.persistencia.repositorios.LecturaContadorRepo;
import co.jamar.produccion.web.dto.LecturaContadorRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class LecturaContadorServicioImpl implements LecturaContadorServicio {

    private final LecturaContadorRepo lecturaContadorRepo;

    public LecturaContadorServicioImpl(LecturaContadorRepo lecturaContadorRepo) {
        this.lecturaContadorRepo = lecturaContadorRepo;
    }

    @Override
    public void guardarLecturaContador(LecturaContadorRequestDTO lecturaContadorRequestDTO) {

    }
}
