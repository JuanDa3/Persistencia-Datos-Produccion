package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ProductoNoConformeServicio;
import co.jamar.produccion.persistencia.entidades.Produccion;
import co.jamar.produccion.persistencia.entidades.ProductoNoConforme;
import co.jamar.produccion.persistencia.repositorios.ProduccionRepo;
import co.jamar.produccion.persistencia.repositorios.ProductoNoConformeRepo;
import co.jamar.produccion.web.dto.ProductoNoConformeRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoNoConformeServicioImpl implements ProductoNoConformeServicio {

    private final ProductoNoConformeRepo productoNoConformeRepo;

    private final ProduccionRepo produccionRepo;

    public ProductoNoConformeServicioImpl(ProductoNoConformeRepo productoNoConformeRepo, ProduccionRepo produccionRepo) {
        this.productoNoConformeRepo = productoNoConformeRepo;
        this.produccionRepo = produccionRepo;
    }

    @Override
    public void guardarProductoNoConforme(List<ProductoNoConformeRequestDTO> listaProductoNoConforme) {
        for(ProductoNoConformeRequestDTO productoNoConforme : listaProductoNoConforme){
            Produccion produccion = produccionRepo.obtenerProduccionPorConsecutivoBitacora(productoNoConforme.getNumBitacora());

            ProductoNoConforme productoNoConformeEntity = new ProductoNoConforme();
            productoNoConformeEntity.setCantidad((int) productoNoConforme.getCantidad());
            productoNoConformeEntity.setTipo(productoNoConforme.getTipo());
            productoNoConformeEntity.setCausa(String.valueOf(productoNoConforme.getCausa()));
            productoNoConformeEntity.setProduccion(produccion);

            productoNoConformeRepo.save(productoNoConformeEntity);
        }
    }
}
