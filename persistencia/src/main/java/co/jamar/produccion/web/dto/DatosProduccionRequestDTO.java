package co.jamar.produccion.web.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DatosProduccionRequestDTO {
    private BitacoraRequestDTO bitacora;
    private ProduccionRequestDTO produccion;
    private TrasladoMezclaDTO trasladoMezcla;
    private LecturaContadorRequestDTO lecturaContadorAgua;
    private ControlCementoRequestDTO controlCemento;
    private List<TiempoParadaMaquinaRequestDTO> listaTiemposParadaMaquina;
    private List<ProductoNoConformeRequestDTO>listaProductoNoConforme;
    private PruebaRequestDTO prueba;
}
