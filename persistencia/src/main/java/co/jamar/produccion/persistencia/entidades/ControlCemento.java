package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "spring")
public class ControlCemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idControlCemento;

    @Column(nullable = false)
    private int saldo;

    @Column(nullable = true)
    private int entradaKilos;

    @Column(nullable = true)
    private int salidaKilos;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;
}
