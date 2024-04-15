package co.jamar.produccion.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LecturaContadorRequestDTO {
    private int lecturaIncial;
    private int lecturafinal;
    private ProduccionRequestDTO produccionDTO;
}
