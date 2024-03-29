package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.MateriaPrimaProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaProveedorRepo extends JpaRepository<MateriaPrimaProveedor, Integer> {
}
