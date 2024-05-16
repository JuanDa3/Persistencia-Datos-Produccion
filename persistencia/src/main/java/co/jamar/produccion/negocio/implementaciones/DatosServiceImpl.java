package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.*;
import co.jamar.produccion.web.dto.DatosProduccionRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DatosServiceImpl implements DatosService {

    private final BitacoraServicio bitacoraServicio;
    private final ProduccionServicio produccionServicio;
    private final TrasladoMezclaServicio trasladoMezclaServicio;
    private final LecturaContadorServicio lecturaContadorServicio;
    private final ControlCementoServicio controlCementoServicio;
    private final TiempoParadaMaquinaServicio tiempoParadaMaquinaServicio;
    private final ProductoNoConformeServicio productoNoConformeServicio;
    private final PruebaServicio pruebaServicio;

    public DatosServiceImpl(BitacoraServicio bitacoraServicio, ProduccionServicio produccionServicio, TrasladoMezclaServicio trasladoMezclaServicio, LecturaContadorServicio lecturaContadorServicio, ControlCementoServicio controlCementoServicio, TiempoParadaMaquinaServicio tiempoParadaMaquinaServicio, ProductoNoConformeServicio productoNoConformeServicio, PruebaServicio pruebaServicio) {
        this.bitacoraServicio = bitacoraServicio;
        this.produccionServicio = produccionServicio;
        this.trasladoMezclaServicio = trasladoMezclaServicio;
        this.lecturaContadorServicio = lecturaContadorServicio;
        this.controlCementoServicio = controlCementoServicio;
        this.tiempoParadaMaquinaServicio = tiempoParadaMaquinaServicio;
        this.productoNoConformeServicio = productoNoConformeServicio;
        this.pruebaServicio = pruebaServicio;
    }

    @Override
    @Transactional
    public HashMap<String, Double> guardarDatosProduccion(DatosProduccionRequestDTO datosProduccionRequestDTO) throws Exception{
        HashMap<String, Double> estadisticasProduccion = new HashMap<>();
        bitacoraServicio.guardarBitacota(datosProduccionRequestDTO.getBitacora());
        estadisticasProduccion = produccionServicio.guardarProduccion(datosProduccionRequestDTO.getProduccion());
        trasladoMezclaServicio.guardarTrasladoMezcla(datosProduccionRequestDTO.getTrasladoMezcla(), datosProduccionRequestDTO.getBitacora().getFecha());
        lecturaContadorServicio.guardarLecturaContador(datosProduccionRequestDTO.getLecturaContadorAgua());
        controlCementoServicio.guardarControlCemento(datosProduccionRequestDTO.getControlCemento());
        tiempoParadaMaquinaServicio.guardarTiemposParadaMaquina(datosProduccionRequestDTO.getListaTiemposParadaMaquina());
        productoNoConformeServicio.guardarProductoNoConforme(datosProduccionRequestDTO.getListaProductoNoConforme());
        pruebaServicio.guardarPrueba(datosProduccionRequestDTO.getPrueba());

        return estadisticasProduccion;
    }
}
