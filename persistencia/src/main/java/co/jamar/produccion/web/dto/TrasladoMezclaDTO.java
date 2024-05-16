package co.jamar.produccion.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrasladoMezclaDTO {
    @JsonProperty("deMaquina")
    private String deMaquina;
    @JsonProperty("aMaquina")
    private String aMaquina;
    private int cantidadKilos;
    private int numBitacora;
}
