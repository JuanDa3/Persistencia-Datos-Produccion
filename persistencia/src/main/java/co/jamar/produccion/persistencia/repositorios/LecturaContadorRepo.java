package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.LecturaContadorAgua;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturaContadorRepo extends JpaRepository<LecturaContadorAgua, Integer> {
}
