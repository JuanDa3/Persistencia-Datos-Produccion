package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "produccion_jamar")
public class RegistroContable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRegistroContable;

    @Column(nullable = false)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "produccion_id")
    private Produccion produccion;
}
