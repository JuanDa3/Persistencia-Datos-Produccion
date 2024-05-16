package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ControlCementoServicio;
import co.jamar.produccion.persistencia.entidades.ControlCemento;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.repositorios.ControlCementoRepo;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.web.dto.ControlCementoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControlCementoServicioImpl implements ControlCementoServicio {

    private final ControlCementoRepo controlCementoRepo;

    private final ProduccionRepo produccionRepo;

    public ControlCementoServicioImpl(ControlCementoRepo controlCementoRepo, ProduccionRepo produccionRepo) {
        this.controlCementoRepo = controlCementoRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public int saldoCemento() {
        return controlCementoRepo.findLatestSaldo();
    }

    @Override
    public void guardarControlCemento(ControlCementoRequestDTO controlCementoRequestDTO) {
        Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(controlCementoRequestDTO.getNumBitacora());

        ControlCemento controlCemento = new ControlCemento();
        controlCemento.setSaldo(controlCementoRequestDTO.getSaldo());
        controlCemento.setEntradaKilos(controlCementoRequestDTO.getEntradaKilos());
        controlCemento.setSalidaKilos(controlCementoRequestDTO.getSalidaKilos());
        controlCemento.setFechaEntrada(controlCementoRequestDTO.getFechaEntradaKilos());
        controlCemento.setFechaSalida(controlCementoRequestDTO.getFechaSalidaKilos());
        controlCemento.setProduccion(produccion);
        controlCementoRepo.save(controlCemento);
    }
}
