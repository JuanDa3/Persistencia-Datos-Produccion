package co.jamar.produccion.negocio.servicios;

import co.jamar.produccion.persistencia.entidades.ProductoNoConforme;
import co.jamar.produccion.web.dto.ProductoNoConformeRequestDTO;

import java.util.List;

public interface ProductoNoConformeServicio {
    void guardarProductoNoConforme(List<ProductoNoConformeRequestDTO> listaProductoNoConforme);
}
