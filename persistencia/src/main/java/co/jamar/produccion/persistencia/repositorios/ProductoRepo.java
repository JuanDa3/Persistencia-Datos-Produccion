package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {

    @Query("select p from Producto p where p.nombre = :nombreProducto and p.referencia = :referencia")
    Optional<Producto> encontrarPorNombreYReferencia(String nombreProducto, String referencia);
}
