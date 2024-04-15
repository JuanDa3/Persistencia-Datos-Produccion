package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.RegistroContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroContableRepo extends JpaRepository<RegistroContable, Integer> {
}
