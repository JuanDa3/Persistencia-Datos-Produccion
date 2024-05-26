package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ProduccionServicio;
import co.jamar.produccion.persistencia.entidades.*;
import co.jamar.produccion.persistencia.repositorios.*;
import co.jamar.produccion.web.dto.ProduccionRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProduccionServicioImpl implements ProduccionServicio {

    private final ProduccionRepo produccionRepo;

    private final BitacoraRepo bitacoraRepo;

    private final ProveedorRepo proveedorRepo;

    private final MateriaPrimaRepo materiaPrimaRepo;

    private final MateriaPrimaProveedorRepo materiaPrimaProveedorRepo;

    private final MaterialProductoRepo materialProductoRepo;

    private final HashMap<String, Double> estadisticas = new HashMap<>();

    public ProduccionServicioImpl(ProduccionRepo produccionRepo, BitacoraRepo bitacoraRepo, ProveedorRepo proveedorRepo, MateriaPrimaRepo materiaPrimaRepo, MateriaPrimaProveedorRepo materiaPrimaProveedorRepo, MaterialProductoRepo materialProductoRepo) {
        this.produccionRepo = produccionRepo;
        this.bitacoraRepo = bitacoraRepo;
        this.proveedorRepo = proveedorRepo;
        this.materiaPrimaRepo = materiaPrimaRepo;
        this.materiaPrimaProveedorRepo = materiaPrimaProveedorRepo;
        this.materialProductoRepo = materialProductoRepo;
    }

    @Transactional
    @Override
    public HashMap<String, Double> guardarProduccion(Bitacora bitacoraGuardada, ProduccionRequestDTO produccionRequestDTO) throws Exception {
        Bitacora bitacora = obtenerBitacoraPorConsecutivo(produccionRequestDTO.getNumBitacora());
        double productividad = calcularProductividad(produccionRequestDTO.getHoraInicio(), produccionRequestDTO.getHoraFin(), produccionRequestDTO.getCantidadProductos());
        List<MateriaPrima> listaMateriasPrimas = new ArrayList<>();
        List<MateriaPrima> listaMateriasPrimasGuardadas = new ArrayList<>();
        Produccion produccion = getProduccion(produccionRequestDTO, bitacora, productividad);

        Produccion produccionGuardada = produccionRepo.save(produccion);

        if (bitacoraGuardada.isEsPrincipal()) {
            listaMateriasPrimas = produccionRequestDTO.getListaDeMateriasPrimas();
            listaMateriasPrimasGuardadas = asociarDatosProduccion(produccionGuardada, listaMateriasPrimas);
        } else {
            Optional<Bitacora> bitacoraPrincipal = bitacoraRepo.existsEsPrincipalEnUltimaFecha(bitacoraGuardada.getFecha());
            Produccion produccionBitacoraPrincipal = produccionRepo.obtenerProduccionPorConsecutivoBitacora(bitacoraPrincipal.orElseThrow(() -> new RuntimeException("No existe produccion asociada a la bitacora")).getConsecutivo());
            List<MateriaPrima> listaMateriasPrimasCalculo = produccionRepo.listarMateriasPrimas(bitacoraPrincipal.orElseThrow(() -> new RuntimeException("Bitacora no existe")).getConsecutivo());
            listaMateriasPrimasGuardadas = asociarDatosProduccion(produccionGuardada, calcularValoresBitacorasNoPrincipal(produccionRequestDTO.getTotalMezcla(), listaMateriasPrimasCalculo));
        }
        return calcularEstadisticas(produccionGuardada, listaMateriasPrimasGuardadas);
    }

    @Override
    public void eliminarProduccion(int id) {
        Produccion produccionEliminar = produccionRepo.getReferenceById(id);
        produccionRepo.delete(produccionEliminar);
    }

    private static Produccion getProduccion(ProduccionRequestDTO produccionRequestDTO, Bitacora bitacora, double productividad) {
        Produccion produccion = new Produccion();
        produccion.setCantidadProductos(produccionRequestDTO.getCantidadProductos());
        produccion.setBitacora(bitacora);
        produccion.setHoraInicio(produccionRequestDTO.getHoraInicio());
        produccion.setHoraFin(produccionRequestDTO.getHoraFin());
        produccion.setProductividad(productividad);
        produccion.setSobranteMezcla(produccionRequestDTO.getSobranteMezcla());
        produccion.setTotalMezcla(produccionRequestDTO.getTotalMezcla());
        return produccion;
    }

    private List<MateriaPrima> asociarDatosProduccion(Produccion produccionGuardada, List<MateriaPrima> listaMateriasPrimas) {

        List<MateriaPrimaProveedor> listaMateriasPrimasProveedores = new ArrayList<>();
        List<MateriaPrima> listaMateriasPrimasGuardadas = new ArrayList<>();

        for (MateriaPrima materiaPrima : listaMateriasPrimas) {
            MateriaPrima materiaPrimaGuardar = new MateriaPrima();
            materiaPrimaGuardar.setNombre(materiaPrima.getNombre());
            materiaPrimaGuardar.setCantidad(materiaPrima.getCantidad());
            MateriaPrima materiaPrimaGuardada = materiaPrimaRepo.save(materiaPrimaGuardar);
            listaMateriasPrimasGuardadas.add(materiaPrimaGuardada);
        }

        for (MateriaPrima materiaPrima : listaMateriasPrimasGuardadas) {
            Optional<Proveedor> proveedorMaterial = proveedorRepo.obtenerProveedorNombreProducto(materiaPrima.getNombre());
            MateriaPrimaProveedor materiaPrimaProveedor = new MateriaPrimaProveedor();
            materiaPrimaProveedor.setProveedor(proveedorMaterial.get());
            materiaPrimaProveedor.setMateriaPrima(materiaPrima);
            materiaPrimaProveedorRepo.save(materiaPrimaProveedor);
            listaMateriasPrimasProveedores.add(materiaPrimaProveedor);
        }

        for (MateriaPrimaProveedor materiaPrimaProveedor : listaMateriasPrimasProveedores) {
            MaterialProducto materialProducto = new MaterialProducto();
            materialProducto.setMateriaPrimaProveedor(materiaPrimaProveedor);
            materialProducto.setProduccion(produccionGuardada);
            materialProductoRepo.save(materialProducto);
        }
        return listaMateriasPrimasGuardadas;
    }

    private List<MateriaPrima> calcularValoresBitacorasNoPrincipal(int totalMezcla, List<MateriaPrima> listaMateriasPrimas) {

        List<MateriaPrima> listaMateriasPrimasGuardar = new ArrayList<>();

        for (MateriaPrima materiaPrima : listaMateriasPrimas) {
            MateriaPrima materiaPrimaGuardar = new MateriaPrima();
            double cantidadMateriaPrima = totalMezcla * materiaPrima.getPorcentaje();
            materiaPrimaGuardar.setNombre(materiaPrima.getNombre());
            materiaPrimaGuardar.setPorcentaje(materiaPrima.getPorcentaje());
            materiaPrimaGuardar.setCantidad((int) cantidadMateriaPrima);
            listaMateriasPrimasGuardar.add(materiaPrimaGuardar);
        }
        return listaMateriasPrimasGuardar;
    }

    private double calcularProductividad(LocalTime horaInicio, LocalTime horaFin, int cantidadProductos) {
        double minutosTrabajadosMaquina = calcularDiferenciaTiempoEnMinutos(horaInicio, horaFin);
        estadisticas.put("Productividad", minutosTrabajadosMaquina / cantidadProductos);
        return minutosTrabajadosMaquina / cantidadProductos;
    }

    private double calcularDiferenciaTiempoEnMinutos(LocalTime horaInicio, LocalTime horaFin) {
        // Calcular la diferencia de tiempo entre la hora de inicio y la hora de fin
        Duration horasTrabajadas = Duration.between(horaInicio, horaFin);
        estadisticas.put("TI Hora", (double) horasTrabajadas.toMinutes() - 60);
        // Convertir la horasTrabajadas de tiempo a minutos como un double
        return (double) horasTrabajadas.toMinutes() / 60;
    }

    private Bitacora obtenerBitacoraPorConsecutivo(int consecutivo) throws Exception {
        Optional<Bitacora> bitacoraOptional = bitacoraRepo.obtenerBitacoraPorConsecutivo(consecutivo);
        if (bitacoraOptional.isEmpty()) {
            throw new Exception("Bitacora no existe");
        }
        return bitacoraOptional.get();
    }

    private HashMap<String, Double> calcularEstadisticas(Produccion produccion, List<MateriaPrima> listaMateriasPrimas) {

        MateriaPrima referenciaAgua = null;
        MateriaPrima referenciaAditivo = null;
        MateriaPrima referenciaCemento = null;

        for (MateriaPrima materiaPrima : listaMateriasPrimas) {
            if (materiaPrima.getNombre().equals("Desmoldante")) {
                double porcentajeDesmoldante = redondearDosDecimales((double) (materiaPrima.getCantidad() / produccion.getCantidadProductos()) * 100);
                estadisticas.put("%" + materiaPrima.getNombre(), porcentajeDesmoldante);
                materiaPrima.setPorcentaje(porcentajeDesmoldante);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Aditivo")) {
                referenciaAditivo = materiaPrima;
            }
            if (materiaPrima.getNombre().equals("Agua")) {
                referenciaAgua = materiaPrima;
            }
            if (materiaPrima.getNombre().equals("Cemento")) {
                double porcentajeCemento = redondearDosDecimales(((double) materiaPrima.getCantidad() / produccion.getTotalMezcla()) * 100);
                referenciaCemento = materiaPrima;
                estadisticas.put("%" + materiaPrima.getNombre(), porcentajeCemento);
                materiaPrima.setPorcentaje(porcentajeCemento);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Triturado")) {
                double porcentajeTriturado = redondearDosDecimales(((double) materiaPrima.getCantidad() / produccion.getTotalMezcla()) * 100);
                estadisticas.put("%" + materiaPrima.getNombre(), porcentajeTriturado);
                materiaPrima.setPorcentaje(porcentajeTriturado);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Arena Gruesa")) {
                double porcentajeArenaGruesa = redondearDosDecimales((((double) materiaPrima.getCantidad() / produccion.getTotalMezcla()) * 100));
                estadisticas.put("%" + materiaPrima.getNombre(), porcentajeArenaGruesa);
                materiaPrima.setPorcentaje(porcentajeArenaGruesa);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Arena Fina")) {
                double porcentajeArenaFina = redondearDosDecimales(((double) materiaPrima.getCantidad() / produccion.getTotalMezcla()) * 100);
                estadisticas.put("%" + materiaPrima.getNombre(), porcentajeArenaFina);
                materiaPrima.setPorcentaje(porcentajeArenaFina);
                materiaPrimaRepo.save(materiaPrima);
            }
        }

        if (referenciaCemento != null) {
            double kiloCementoPorProducto = redondearDosDecimales((double) referenciaCemento.getCantidad() / produccion.getCantidadProductos());
            estadisticas.put("K C / Pdto ", kiloCementoPorProducto);
            referenciaCemento.setPorcentaje(kiloCementoPorProducto);
            materiaPrimaRepo.save(referenciaCemento);
        }

        if (referenciaAgua != null && referenciaCemento != null) {
            double porcentajeAgua = redondearDosDecimales(((double) referenciaAgua.getCantidad() / referenciaCemento.getCantidad()) * 100);
            estadisticas.put("%Agua ", porcentajeAgua);
            referenciaAgua.setPorcentaje(porcentajeAgua);
            materiaPrimaRepo.save(referenciaCemento);
        }

        if (referenciaAditivo != null && referenciaCemento != null) {
            double porcentajeAditivo = redondearDosDecimales((((double) referenciaAditivo.getCantidad() / (referenciaCemento.getCantidad() * 1000))) * 100);
            estadisticas.put("%Aditivo", porcentajeAditivo);
            referenciaAditivo.setPorcentaje(porcentajeAditivo);
            materiaPrimaRepo.save(referenciaAditivo);
        }

        double porcentajeSobrante = redondearDosDecimales(((double) produccion.getSobranteMezcla() / produccion.getTotalMezcla()) * 100);
        estadisticas.put("%Sobrante ", porcentajeSobrante);

        double porcentajeCementoPulir = redondearDosDecimales((double) produccion.getCementoPulir() / produccion.getCantidadProductos());
        estadisticas.put("%Cemento Pulir", porcentajeCementoPulir);

        return estadisticas;
    }

    private double redondearDosDecimales(double valor) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(valor));
    }
}
