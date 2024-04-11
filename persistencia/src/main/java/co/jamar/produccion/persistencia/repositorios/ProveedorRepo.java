package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepo extends JpaRepository<Proveedor, Integer> {

    @Query("select p from Proveedor p where p.producto = :nombreProducto")
    Optional<Proveedor> obtenerProveedorNombreProducto(String nombreProducto);
}
