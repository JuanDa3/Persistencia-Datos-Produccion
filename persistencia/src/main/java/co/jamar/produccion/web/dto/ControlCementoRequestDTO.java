package co.jamar.produccion.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ControlCementoRequestDTO {
    private double saldo;
    private double entradaKilos;
    private LocalDate fechaEntradaKilos;
    private double salidaKilos;
    private LocalDate fechaSalidaKilos;
    private ProduccionRequestDTO produccionDTO;
}
