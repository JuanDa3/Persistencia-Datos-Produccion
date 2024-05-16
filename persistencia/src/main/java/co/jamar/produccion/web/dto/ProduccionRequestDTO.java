package co.jamar.produccion.web.dto;

import co.jamar.produccion.persistencia.entidades.MateriaPrima;
import co.jamar.produccion.persistencia.entidades.ProductoNoConforme;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProduccionRequestDTO {
    private int numBitacora;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private int totalMezcla;

    private double productividad;

    private int cantidadProductos;

    private int cementoPulir;

    private int sobranteMezcla;

    private List<MateriaPrima> listaDeMateriasPrimas = new ArrayList<>();

    private List<ProductoNoConforme> listaProductosNoConformes;
}
