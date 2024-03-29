package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {
}
