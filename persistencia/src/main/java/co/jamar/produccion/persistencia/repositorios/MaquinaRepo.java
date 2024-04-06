package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaquinaRepo extends JpaRepository<Maquina, Integer> {

    @Query("select m from Maquina m where m.nombre = :nombreMaquina")
    Optional<Maquina>encontrarPorNombre(String nombreMaquina);
}
