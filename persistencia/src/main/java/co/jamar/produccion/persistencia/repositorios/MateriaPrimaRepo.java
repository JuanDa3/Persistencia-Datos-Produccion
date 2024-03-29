package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaRepo extends JpaRepository<MateriaPrima, Integer> {
}
