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
public class CriterioAceptacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCriterioAceptacion;

    @Column(length = 45, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int valor;

    @OneToMany(mappedBy = "criterioAceptacion")
    private List<Producto> productos;

}
