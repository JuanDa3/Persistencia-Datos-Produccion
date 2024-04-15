package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.RegistroContableServicio;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.entidades.RegistroContable;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.persistencia.repositorios.RegistroContableRepo;
import co.jamar.produccion.web.dto.RegistroContableRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class RegistroContableServicioImpl implements RegistroContableServicio {

    private final RegistroContableRepo registroContableRepo;

    private final ProduccionRepo produccionRepo;

    public RegistroContableServicioImpl(RegistroContableRepo registroContableRepo, ProduccionRepo produccionRepo) {
        this.registroContableRepo = registroContableRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public void guardarRegistroContable(RegistroContableRequestDTO registroContableRequestDTO) {
        Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(registroContableRequestDTO.getConsecutivoBitacora());

        RegistroContable registroContable = new RegistroContable();

        registroContable.setNumero(registroContableRequestDTO.getNumero());
        registroContable.setProduccion(produccion);

        registroContableRepo.save(registroContable);
    }
}
