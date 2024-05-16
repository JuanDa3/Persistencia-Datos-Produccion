package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.LecturaContadorServicio;
import co.jamar.produccion.persistencia.entidades.LecturaContadorAgua;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.repositorios.LecturaContadorRepo;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.web.dto.LecturaContadorRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class LecturaContadorServicioImpl implements LecturaContadorServicio {

    private final LecturaContadorRepo lecturaContadorRepo;
    private final ProduccionRepo produccionRepo;

    public LecturaContadorServicioImpl(LecturaContadorRepo lecturaContadorRepo, ProduccionRepo produccionRepo) {
        this.lecturaContadorRepo = lecturaContadorRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public void guardarLecturaContador(LecturaContadorRequestDTO lecturaContadorRequestDTO) {
        Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(lecturaContadorRequestDTO.getNumBitacora());

        LecturaContadorAgua lecturaContadorAgua = new LecturaContadorAgua();
        lecturaContadorAgua.setLecturaInicial(lecturaContadorRequestDTO.getLecturaIncial());
        lecturaContadorAgua.setLecturaFinal(lecturaContadorRequestDTO.getLecturafinal());
        lecturaContadorAgua.setProduccion(produccion);
        lecturaContadorRepo.save(lecturaContadorAgua);
    }
}
