package co.jamar.produccion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LecturaContadorRequestDTO {
    private int lecturaIncial;
    private int lecturafinal;
    private ProduccionRequestDTO produccionDTO;
}
