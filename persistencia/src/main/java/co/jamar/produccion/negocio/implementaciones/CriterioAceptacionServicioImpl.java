package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.CriterioAceptacionServicio;
import co.jamar.produccion.persistencia.entidades.CriterioAceptacion;
import co.jamar.produccion.persistencia.repositorios.CriterioAceptacionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriterioAceptacionServicioImpl implements CriterioAceptacionServicio {

    private final CriterioAceptacionRepo criterioAceptacionRepo;

    public CriterioAceptacionServicioImpl(CriterioAceptacionRepo criterioAceptacionRepo) {
        this.criterioAceptacionRepo = criterioAceptacionRepo;
    }
    @Override
    public List<CriterioAceptacion> findCriteriosByNombreProducto(String nombreProducto) {
        List<CriterioAceptacion>criteriosAceptacion = criterioAceptacionRepo.obtenerCriteriosPorProducto(nombreProducto);
        return null;
    }
}
