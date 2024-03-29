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

    @Column(length = 45,nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_aceptacion_id", nullable = false)
    private CriterioAceptacion criterioAceptacion;

    @OneToMany(mappedBy = "producto")
    private List<Bitacora> bitacoras;

}
