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
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProveedor;

    @Column(name = "nombre_proveedor", length = 45, nullable = false)
    private String nombreProveedor;

    @Column(name = "nombre_materia_prima", length = 45, nullable = false)
    private String nombreMateriaPrima;

    @Column(length = 45, nullable = false)
    private String lote;

    @OneToMany(mappedBy = "proveedor")
    private List<MateriaPrimaProveedor> materiasPrimas;


}
