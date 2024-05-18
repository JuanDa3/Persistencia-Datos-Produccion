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
    private double productividad;

    @Column(name = "sobrante_mezcla")
    private int sobranteMezcla;

    @Column(name = "cemento_pulir")
    private int cementoPulir;

    @OneToOne
    @JoinColumn(name = "id_bitacora")
    private Bitacora bitacora;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<TiempoParadaMaquina> tiemposParadaMaquina;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<ProductoNoConforme> listaProductosNoConformes;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<TrasladoMezcla> trasladosMezcla;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<MaterialProducto> materiales;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<RecursoHumanoProduccion> recursosHumanosProduccion;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<ControlCemento> listaControlCemento;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<RegistroContable>listaRegistros;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    private List<LecturaContadorAgua> lecturasContadorAgua;
}
