package co.jamar.produccion.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idEmpleado;

    @Column(length = 45)
    private String nombre;

    @OneToMany(mappedBy = "empleado")
    private List<Bitacora> bitacoras;

    @OneToMany(mappedBy = "empleado")
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "empleado")
    private List<RecursoHumanoProduccion> recursosHumanosProduccion;

}
