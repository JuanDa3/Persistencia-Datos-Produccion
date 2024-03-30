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
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProducto;

    @Column(length = 45,nullable = false)
    private String nombre;

    @Column(length = 45,nullable = false)
    private String referencia;

    @Column(length = 45)
    private String complemento;

    @Column(length = 45)
    private String referenciaP1;

    @Column(length = 45,nullable = false)
    private String linea;

    @Column(nullable = false)
    private int peso;

    @ManyToMany
    @JoinTable(
            name = "producto_criterio",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "criterio_id")
    )
    private List<CriterioAceptacion> criterios = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    private List<Bitacora> bitacoras;

}
