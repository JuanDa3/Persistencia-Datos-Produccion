package co.jamar.produccion.persistencia.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "spring")
public class Produccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProduccion;

    @Column(name = "cantidad_productos", nullable = false)
    private int cantidadProductos;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "total_mezcla", nullable = false)
    private int totalMezcla;

    @Column(nullable = false)
    private int productividad;

    @Column(name = "sobrante_mezcla")
    private int sobranteMezcla;

    @OneToOne
    @JoinColumn(name = "id_bitacora")
    private Bitacora bitacora;

    @OneToMany(mappedBy = "produccion")
    private List<TiempoParadaMaquina> tiemposParadaMaquina;

    @OneToMany(mappedBy = "produccion")
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "produccion")
    private List<PruebaSuperficial> pruebasSuperficiales;

    @OneToMany(mappedBy = "produccion")
    private List<TrasladoMezcla> trasladosMezcla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_producto_id", nullable = false)
    private MaterialProducto materialProducto;

    @OneToMany(mappedBy = "produccion")
    private List<RecursoHumanoProduccion> recursosHumanosProduccion;

    @OneToMany(mappedBy = "produccion")
    private List<ControlCemento> listaControlCemento;

}
