package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.TrasladoMezcla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrasladoMezclaRepo extends JpaRepository<TrasladoMezcla, Integer> {
}
