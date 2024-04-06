package co.jamar.produccion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private String nombre;
    private String referencia;
    private String complemento;
    private int peso;
    private String referenciaP1;
    private String linea;
}
