package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.CriterioAceptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriterioAceptacionRepo extends JpaRepository<CriterioAceptacion, Integer> {
}
