package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.Bitacora;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface BitacoraRepo extends JpaRepository<Bitacora, Integer> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Bitacora b " +
            "WHERE b.fecha = :fecha " +
            "AND b.esPrincipal = true")
    boolean existsEsPrincipalEnUltimaFecha(@Param("fecha") LocalDate fecha);

    @Query("select b from Bitacora b where b.consecutivo = :consecutivo")
    Optional<Bitacora> obtenerBitacoraPorConsecutivo(int consecutivo);
}
