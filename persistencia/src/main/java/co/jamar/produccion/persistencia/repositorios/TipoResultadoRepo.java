package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.TipoResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoResultadoRepo extends JpaRepository<TipoResultado, Integer> {
}
