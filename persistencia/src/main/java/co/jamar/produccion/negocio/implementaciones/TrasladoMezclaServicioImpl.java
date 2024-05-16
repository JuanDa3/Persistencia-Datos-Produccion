package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.TrasladoMezclaServicio;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.entidades.TrasladoMezcla;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.persistencia.repositorios.TrasladoMezclaRepo;
import co.jamar.produccion.web.dto.TrasladoMezclaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TrasladoMezclaServicioImpl implements TrasladoMezclaServicio {

    private final TrasladoMezclaRepo trasladoMezclaRepo;

    private final ProduccionRepo produccionRepo;

    public TrasladoMezclaServicioImpl(TrasladoMezclaRepo trasladoMezclaRepo, ProduccionRepo produccionRepo) {
        this.trasladoMezclaRepo = trasladoMezclaRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public void guardarTrasladoMezcla(TrasladoMezclaDTO trasladoMezclaDTO, LocalDate fecha) {
        Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(trasladoMezclaDTO.getNumBitacora());
        TrasladoMezcla trasladoMezcla = new TrasladoMezcla();
        trasladoMezcla.setProduccion(produccion);
        trasladoMezcla.setDeMaquina(trasladoMezclaDTO.getDeMaquina());
        trasladoMezcla.setAMaquina(trasladoMezclaDTO.getAMaquina());
        trasladoMezcla.setCantidad(trasladoMezclaDTO.getCantidadKilos());
        trasladoMezcla.setFecha(fecha);
        trasladoMezclaRepo.save(trasladoMezcla);
    }
}
