package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.persistencia.entidades.CriterioAceptacion;
import feign.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CriterioAceptacionServicio {
    @Query("SELECT ca FROM CriterioAceptacion ca JOIN ca.productos p WHERE p.nombre = :nombreProducto")
    List<CriterioAceptacion> findCriteriosByNombreProducto(@Param("nombreProducto") String nombreProducto);
}
