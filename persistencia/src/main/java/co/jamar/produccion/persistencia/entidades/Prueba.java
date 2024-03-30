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
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCilindro;

    @Column(nullable = false)
    private int numero;

    @Column(name = "no_cocha", nullable = false)
    private int noCocha;

    @Column(length = 45)
    private String resultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_resultado_id", nullable = false)
    private TipoResultado tipoResultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;


}
