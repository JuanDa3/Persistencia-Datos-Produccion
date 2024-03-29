package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.RecursoHumanoProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoHumanoProduccionRepo extends JpaRepository<RecursoHumanoProduccion, Integer> {
}
