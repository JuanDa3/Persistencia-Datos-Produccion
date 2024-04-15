package co.jamar.produccion.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistroContableRequestDTO {
    private int numero;
    private int consecutivoBitacora;
}
