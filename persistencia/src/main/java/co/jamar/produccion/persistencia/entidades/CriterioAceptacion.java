package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioAceptacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCriterioAceptacion;

    @Column(length = 45, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int valor;

    @ManyToMany(mappedBy = "criterios")
    private List<Producto> productos = new ArrayList<>();
}
