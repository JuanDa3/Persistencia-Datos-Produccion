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
@Table(schema = "spring")
public class PruebaSuperficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProductoNoConforme;

    @Column(length = 45, nullable = false)
    private String cantidad;

    @Column(length = 45, nullable = false)
    private String tipo;

    @Column(length = 45, nullable = false)
    private String causa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_resultado_id", nullable = false)
    private TipoResultado tipoResultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;


}
