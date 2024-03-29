package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepo extends JpaRepository<Proveedor, Integer> {
}
