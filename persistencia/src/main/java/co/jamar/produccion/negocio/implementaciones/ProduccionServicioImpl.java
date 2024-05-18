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
        Produccion produccion = getProduccion(produccionRequestDTO, bitacora, productividad);

        Produccion produccionGuardada = null;
        if (bitacoraGuardada.isEsPrincipal()) {
            produccionGuardada = produccionRepo.save(produccion);
            listaMateriasPrimas = produccionRequestDTO.getListaDeMateriasPrimas();
            return asociarDatosProduccion(produccionGuardada, listaMateriasPrimas);
        } else {
            //obtener bitacora principal
            Optional<Bitacora> bitacoraPrincipal = bitacoraRepo.existsEsPrincipalEnUltimaFecha(bitacoraGuardada.getFecha());
            //obtener produccion
            Produccion produccionBitacoraPrincipal = produccionRepo.obtenerProduccionPorConsecutivoBitacora(bitacoraPrincipal.orElseThrow(() -> new RuntimeException("No existe produccion asociada a la bitacora")).getConsecutivo());
            //obtener lista de materias primas de bitacora principal
            List<MateriaPrima> listaMateriasPrimasCalculo = produccionRepo.listarMateriasPrimas(bitacoraPrincipal.orElseThrow(() -> new RuntimeException("Bitacora no existe")).getConsecutivo());
            //obtener porcentajes produccion de bitacora principal
            HashMap<String, Double> estadisticas = calcularEstadisticas(produccionBitacoraPrincipal, listaMateriasPrimasCalculo);
            //hacer calculos de la mezcla
            calcularValoresBitacorasNoPrincipal(produccionRequestDTO.getTotalMezcla(), estadisticas);
            //actualizar bitacora principal
            //guardar datos bitacoras hijos
            return null;
        }
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

    private HashMap<String, Double> asociarDatosProduccion(Produccion produccionGuardada, List<MateriaPrima> listaMateriasPrimas) {

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
        return calcularEstadisticas(produccionGuardada, listaMateriasPrimasGuardadas);
    }

    private void calcularValoresBitacorasNoPrincipal(int totalMezcla, HashMap<String, Double> estadisticasBitacoraPrincipal) {
        double porcentajeArenaFina = totalMezcla * estadisticasBitacoraPrincipal.get("Arena Fina");
        double porcentajeArenaGruesa = totalMezcla * estadisticasBitacoraPrincipal.get("Arena Gruesa");
        double porcentajeTriturado = totalMezcla * estadisticasBitacoraPrincipal.get("Triturado");
        double porcentajeCemento = totalMezcla * estadisticasBitacoraPrincipal.get("Cemento");
        double porcentajeAgua = totalMezcla * estadisticasBitacoraPrincipal.get("Agua");
        double porcentajeAditivo = totalMezcla * estadisticasBitacoraPrincipal.get("Aditivo");
        double porcentajeAcelerante = totalMezcla * estadisticasBitacoraPrincipal.get("Acelerante");
        double porcentajeDesmoldante = totalMezcla * estadisticasBitacoraPrincipal.get("Desmoldante");
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
                double porcentajeDesmoldante = (double) materiaPrima.getCantidad() / produccion.getCantidadProductos();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeDesmoldante * 100));
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
                double porcentajeCemento = (double) materiaPrima.getCantidad() / produccion.getTotalMezcla();
                referenciaCemento = materiaPrima;
                estadisticas.put("%Cemento ", redondearDosDecimales(porcentajeCemento * 100));
                materiaPrima.setPorcentaje(porcentajeCemento);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Triturado")) {
                double porcentajeTriturado = (double) materiaPrima.getCantidad() / produccion.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeTriturado * 100));
                materiaPrima.setPorcentaje(porcentajeTriturado);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Arena Gruesa")) {
                double porcentajeArenaGruesa = (double) materiaPrima.getCantidad() / produccion.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeArenaGruesa * 100));
                materiaPrima.setPorcentaje(porcentajeArenaGruesa);
                materiaPrimaRepo.save(materiaPrima);
            }
            if (materiaPrima.getNombre().equals("Arena Fina")) {
                double porcentajeArenaFina = (double) materiaPrima.getCantidad() / produccion.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeArenaFina * 100));
                materiaPrima.setPorcentaje(porcentajeArenaFina);
                materiaPrimaRepo.save(materiaPrima);
            }
        }

        if (referenciaCemento != null) {
            double kiloCementoPorProducto = (double) referenciaCemento.getCantidad() / produccion.getCantidadProductos();
            estadisticas.put("K C / Pdto ", redondearDosDecimales(kiloCementoPorProducto));
            referenciaCemento.setPorcentaje(redondearDosDecimales(kiloCementoPorProducto));
            materiaPrimaRepo.save(referenciaCemento);
        }

        if (referenciaAgua != null && referenciaCemento != null) {
            double porcentajeAgua = (double) referenciaAgua.getCantidad() / referenciaCemento.getCantidad();
            estadisticas.put("% Agua ", redondearDosDecimales(porcentajeAgua * 100));
            referenciaAgua.setPorcentaje(redondearDosDecimales(porcentajeAgua));
            materiaPrimaRepo.save(referenciaCemento);
        }

        if (referenciaAditivo != null && referenciaCemento != null) {
            double porcentajeAditivo = ((double) referenciaAditivo.getCantidad() / (referenciaCemento.getCantidad() * 1000));
            estadisticas.put("% Aditivo", redondearDosDecimales(porcentajeAditivo * 100));
            referenciaAditivo.setPorcentaje(porcentajeAditivo);
            materiaPrimaRepo.save(referenciaAditivo);
        }

        double porcentajeSobrante = (double) produccion.getSobranteMezcla() / produccion.getTotalMezcla();
        estadisticas.put("% Sobrante ", redondearDosDecimales(porcentajeSobrante * 100));

        double porcentajeCementoPulir = (double) produccion.getCementoPulir() / produccion.getCantidadProductos();
        estadisticas.put("% cemento Pulir", redondearDosDecimales(porcentajeCementoPulir));

        return estadisticas;
    }

    private double redondearDosDecimales(double valor) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(valor));
    }
}
