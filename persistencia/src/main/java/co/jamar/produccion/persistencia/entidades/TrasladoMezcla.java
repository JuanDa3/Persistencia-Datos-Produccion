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
public class TrasladoMezcla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idTrasladoMezcla;

    @Column(name = "de_maquina", length = 45, nullable = false)
    private String deMaquina;

    @Column(name = "a_maquina", length = 45, nullable = false)
    private String aMaquina;

    @Column(length = 45, nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;

    @OneToMany(mappedBy = "trasladoMezcla")
    private List<MaterialProducto> materialProductos;

}
