package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaquinaRepo extends JpaRepository<Maquina, Integer> {
}
