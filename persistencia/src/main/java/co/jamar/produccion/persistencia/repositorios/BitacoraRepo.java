package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraRepo extends JpaRepository<Bitacora, Integer> {
}
