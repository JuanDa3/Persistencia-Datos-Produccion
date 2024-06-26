package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepo extends JpaRepository<Prueba, Integer> {
}
