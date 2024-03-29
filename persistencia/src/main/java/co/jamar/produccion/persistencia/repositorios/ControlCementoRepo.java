package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.ControlCemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlCementoRepo extends JpaRepository<ControlCemento, Integer> {
}
