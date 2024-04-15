package co.jamar.produccion.web.rest;

import co.jamar.produccion.negocio.servicios.ProductoNoConformeServicio;
import co.jamar.produccion.web.dto.Mensaje;
import co.jamar.produccion.web.dto.ProductoNoConformeRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/produccion/producto-no-conforme")
public class ProductoNoConformeController {

    private final ProductoNoConformeServicio productoNoConformeServicio;

    public ProductoNoConformeController(ProductoNoConformeServicio productoNoConformeServicio) {
        this.productoNoConformeServicio = productoNoConformeServicio;
    }

    @PostMapping
    public ResponseEntity<?>guardarProductoNoConforme(@RequestBody List<ProductoNoConformeRequestDTO> listaProductoNoConforme) {
        try {
            productoNoConformeServicio.guardarProductoNoConforme(listaProductoNoConforme);
            return  ResponseEntity.status(201).body(new Mensaje("Los Productos no conformes se registraron correctamente"));
        }catch (Exception e) {
            return  ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

}
