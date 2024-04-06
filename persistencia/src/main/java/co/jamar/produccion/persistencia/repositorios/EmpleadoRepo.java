package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {
    @Query("select e from Empleado e where e.nombre = :nombreEmpleado")
    Optional<Empleado> encontrarPorNombre(String nombreEmpleado);
}
