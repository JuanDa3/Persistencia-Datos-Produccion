package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrimaProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMateriaPrimaProveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id")
    private MateriaPrima materiaPrima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private Date fechaConsignacion;

    @Column(length = 45, nullable = false)
    private String cantidad;

    @Column(length = 45, nullable = false)
    private String lote;

    @OneToMany(mappedBy = "materiaPrimaProveedor")
    private List<MaterialProducto> materialProductos;

}
