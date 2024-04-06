package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.CriterioAceptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriterioAceptacionRepo extends JpaRepository<CriterioAceptacion, Integer> {
    @Query("select CriterioAceptacion from CriterioAceptacion where ")
    List<CriterioAceptacion> obtenerCriteriosPorProducto(String nombreProducto);
}
