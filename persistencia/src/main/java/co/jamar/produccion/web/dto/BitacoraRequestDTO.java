package co.jamar.produccion.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BitacoraRequestDTO {
    private int consecutivo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private MaquinaDTO maquinaDTO;
    private EmpleadoDTO empleadoDTO;
    private ProductoDTO productoDTO;
}
