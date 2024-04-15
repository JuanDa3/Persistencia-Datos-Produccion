package co.jamar.produccion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TiempoParadaMaquinaRequestDTO {
    private int tipo;
    private int minutos;
    private int consecutivoBitacora;
}
