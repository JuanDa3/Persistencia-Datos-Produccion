package co.jamar.produccion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PruebaRequestDTO {
    private int numero;
    private int numero_cocha;
    private int consecutivoBitacora;
    private String nombreResponsable;
}
