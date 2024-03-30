package co.jamar.produccion.persistencia.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMaterialProducto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 45, nullable = false)
    private String cantidad;

    @ManyToOne
    @JoinColumn(name = "produccion_id")
    private Produccion produccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_proveedor_id", nullable = false)
    private MateriaPrimaProveedor materiaPrimaProveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traslado_mezcla_id")
    private TrasladoMezcla trasladoMezcla;
}
