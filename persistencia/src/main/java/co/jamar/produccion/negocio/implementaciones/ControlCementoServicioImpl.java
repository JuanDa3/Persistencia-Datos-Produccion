package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ControlCementoServicio;
import co.jamar.produccion.persistencia.repositorios.ControlCementoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControlCementoServicioImpl implements ControlCementoServicio {

    private final ControlCementoRepo controlCementoRepo;

    public ControlCementoServicioImpl(ControlCementoRepo controlCementoRepo) {
        this.controlCementoRepo = controlCementoRepo;
    }

    @Override
    public int saldoCemento() {
        return controlCementoRepo.findLatestSaldo();
    }
}
