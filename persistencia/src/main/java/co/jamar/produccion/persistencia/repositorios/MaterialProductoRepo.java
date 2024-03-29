package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.MaterialProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialProductoRepo extends JpaRepository<MaterialProducto, Integer> {
}
