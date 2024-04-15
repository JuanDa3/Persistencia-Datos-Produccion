package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlCemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idControlCemento;

    @Column(nullable = false)
    private double saldo;

    @Column(nullable = true)
    private double entradaKilos;

    @Column(nullable = true)
    private double salidaKilos;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaEntrada;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;
}
