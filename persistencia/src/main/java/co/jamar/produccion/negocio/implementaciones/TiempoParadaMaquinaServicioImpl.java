package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.TiempoParadaMaquinaServicio;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.entidades.TiempoParadaMaquina;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.persistencia.repositorios.TiempoParadaMaquinaRepo;
import co.jamar.produccion.web.dto.TiempoParadaMaquinaRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TiempoParadaMaquinaServicioImpl implements TiempoParadaMaquinaServicio {

    private final TiempoParadaMaquinaRepo tiempoParadaMaquinaRepo;

    private final ProduccionRepo produccionRepo;

    public TiempoParadaMaquinaServicioImpl(TiempoParadaMaquinaRepo tiempoParadaMaquinaRepo, ProduccionRepo produccionRepo) {
        this.tiempoParadaMaquinaRepo = tiempoParadaMaquinaRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public void guardarTiemposParadaMaquina(List<TiempoParadaMaquinaRequestDTO> tiemposParadaMaquinaDTO) {
        for(TiempoParadaMaquinaRequestDTO tiempoParadaMaquinaRequestDTO: tiemposParadaMaquinaDTO){
            Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(tiempoParadaMaquinaRequestDTO.getConsecutivoBitacora());
            TiempoParadaMaquina tiempoParadaMaquina = new TiempoParadaMaquina();
            tiempoParadaMaquina.setTipo(String.valueOf(tiempoParadaMaquinaRequestDTO.getTipo()));
            tiempoParadaMaquina.setMinutos(tiempoParadaMaquinaRequestDTO.getMinutos());
            tiempoParadaMaquina.setProduccion(produccion);

            tiempoParadaMaquinaRepo.save(tiempoParadaMaquina);
        }
    }
}
