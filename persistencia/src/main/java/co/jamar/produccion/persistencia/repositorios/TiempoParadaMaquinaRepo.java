package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.TiempoParadaMaquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiempoParadaMaquinaRepo extends JpaRepository<TiempoParadaMaquina, Integer> {
}
