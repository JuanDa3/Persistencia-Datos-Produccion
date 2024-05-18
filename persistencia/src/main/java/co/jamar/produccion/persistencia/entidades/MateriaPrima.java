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
@ToString
public class MateriaPrima {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMateriaPrima;

    @Column(length = 45, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int cantidad;

    @Column()
    private double porcentaje;

    @OneToMany(mappedBy = "materiaPrima")
    private List<MateriaPrimaProveedor> proveedores;
}
