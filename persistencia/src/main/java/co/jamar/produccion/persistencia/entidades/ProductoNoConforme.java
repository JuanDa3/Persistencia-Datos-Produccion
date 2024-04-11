package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoNoConforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProductoNoConforme;

    @Column(nullable = false)
    private int cantidad;

    @Column(length = 45, nullable = false)
    private String tipo;

    @Column(length = 45, nullable = false)
    private String causa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;


}
