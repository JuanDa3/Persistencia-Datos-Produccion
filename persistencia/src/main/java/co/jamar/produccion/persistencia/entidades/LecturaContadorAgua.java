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
public class LecturaContadorAgua {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idLectura;

    @Column(nullable = false)
    private int lecturaInicial;

    @Column(nullable = false)
    private int lecturaFinal;

    @ManyToOne
    @JoinColumn(name = "produccion_id")
    private Produccion produccion;
}
