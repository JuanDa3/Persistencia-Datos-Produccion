package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.PruebaServicio;
import co.jamar.produccion.persistencia.entidades.Empleado;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.entidades.Prueba;
import co.jamar.produccion.persistencia.repositorios.EmpleadoRepo;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.persistencia.repositorios.PruebaRepo;
import co.jamar.produccion.web.dto.PruebaRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PruebaServicioImpl implements PruebaServicio {

    private final PruebaRepo pruebaRepo;

    private final ProduccionRepo produccionRepo;

    private final EmpleadoRepo empleadoRepo;

    public PruebaServicioImpl(PruebaRepo pruebaRepo, ProduccionRepo produccionRepo, EmpleadoRepo empleadoRepo) {
        this.pruebaRepo = pruebaRepo;
        this.produccionRepo = produccionRepo;
        this.empleadoRepo = empleadoRepo;
    }

    @Override
    public void guardarPrueba(PruebaRequestDTO pruebaRequestDTO) {
        Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(pruebaRequestDTO.getConsecutivoBitacora());

        Optional<Empleado> empleado = empleadoRepo.encontrarPorNombre(pruebaRequestDTO.getNombreResponsable());

        Prueba prueba = new Prueba();
        prueba.setEmpleado(empleado.get());
        prueba.setProduccion(produccion);
        prueba.setNumero(pruebaRequestDTO.getNumero());
        prueba.setNoCocha(pruebaRequestDTO.getNumero_cocha());

        pruebaRepo.save(prueba);
    }
}
