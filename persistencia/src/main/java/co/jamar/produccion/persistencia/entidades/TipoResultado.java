package co.jamar.produccion.persistencia.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "spring")
public class TipoResultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idTipoResultado;

    @Column(length = 45, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tipoResultado")
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "tipoResultado")
    private List<PruebaSuperficial> pruebasSuperficiales;
}
