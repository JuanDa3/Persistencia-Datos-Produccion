package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionRepo extends JpaRepository<Produccion, Integer> {
}
