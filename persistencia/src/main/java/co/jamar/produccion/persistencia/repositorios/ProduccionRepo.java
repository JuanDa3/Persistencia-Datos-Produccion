package co.jamar.produccion.persistencia.repositorios;

import co.jamar.produccion.persistencia.entidades.MateriaPrima;
import co.jamar.produccion.persistencia.entidades.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduccionRepo extends JpaRepository<Produccion, Integer> {
    @Query("SELECT p FROM Produccion p WHERE p.bitacora.consecutivo = :consecutivo")
    Produccion obtenerProduccionPorConsecutivoBitacora(int consecutivo);

    @Query("SELECT mpri " +
            "FROM Bitacora b "+
            "JOIN Produccion p ON b.idBitacora = p.bitacora.idBitacora " +
            "JOIN MaterialProducto mp ON p.idProduccion = mp.produccion.idProduccion " +
            "JOIN MateriaPrimaProveedor mpp ON mp.idMaterialProducto = mpp.idMateriaPrimaProveedor JOIN MateriaPrima mpri ON mpp.materiaPrima.idMateriaPrima = mpri.idMateriaPrima " +
            "WHERE b.consecutivo = :consecutivoBitacora")
    List<MateriaPrima> listarMateriasPrimas(int consecutivoBitacora);
}
