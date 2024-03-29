package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ControlCementoServicio;
import org.springframework.stereotype.Service;

@Service
public class ControlCementoServicioImpl implements ControlCementoServicio {
    @Override
    public int saldoCemento() {
        return 10;
    }
}
