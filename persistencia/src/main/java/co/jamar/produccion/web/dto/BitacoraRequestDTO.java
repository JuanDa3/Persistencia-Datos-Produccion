package co.jamar.produccion.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BitacoraRequestDTO {
    private int consecutivo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private MaquinaDTO maquinaDTO;
    private EmpleadoDTO empleadoDTO;
    private ProductoDTO productoDTO;
}
