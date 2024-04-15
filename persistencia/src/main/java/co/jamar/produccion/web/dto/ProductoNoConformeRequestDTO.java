package co.jamar.produccion.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductoNoConformeRequestDTO {
    private int numBitacora;
    private double cantidad;
    private String tipo;
    private int causa;
}
