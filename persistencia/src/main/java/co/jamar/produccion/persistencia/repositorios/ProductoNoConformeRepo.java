package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.ProductoNoConforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoNoConformeRepo extends JpaRepository<ProductoNoConforme,Integer> {
}
