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
    public HashMap<String, Double> guardarProduccion(ProduccionRequestDTO produccionRequestDTO) throws Exception {
        Bitacora bitacora = obtenerBitacoraPorConsecutivo(produccionRequestDTO.getNumBitacora());
        double productividad = calcularProductividad(produccionRequestDTO.getHoraInicio(), produccionRequestDTO.getHoraFin(), produccionRequestDTO.getCantidadProductos());

        Produccion produccion = getProduccion(produccionRequestDTO, bitacora, productividad);

        Produccion produccionGuardada = produccionRepo.save(produccion);

        return asociarDatosProduccion(produccionGuardada, produccionRequestDTO.getListaDeMateriasPrimas(), produccionRequestDTO);
    }

    @Override
    public void eliminarProduccion(int id) {
        Produccion produccionEliminar = produccionRepo.getReferenceById(id);
        System.out.println(produccionEliminar.getIdProduccion());
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

    private HashMap<String, Double> asociarDatosProduccion(Produccion produccionGuardada, List<MateriaPrima> listaMateriasPrimas, ProduccionRequestDTO produccionRequestDTO) {

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
        return calcularEstadisticas(produccionRequestDTO);
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

    private HashMap<String, Double> calcularEstadisticas(ProduccionRequestDTO produccionRequestDTO) {

        double referenciaAgua = 0;
        double referenciaAditivo = 0;
        double referenciaCemento = 0;

        for (MateriaPrima materiaPrima : produccionRequestDTO.getListaDeMateriasPrimas()) {
            if (materiaPrima.getNombre().equals("Desmoldante")) {
                double porcentajeDesmoldante = (double) materiaPrima.getCantidad() / produccionRequestDTO.getCantidadProductos();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeDesmoldante * 100));
            }
            if (materiaPrima.getNombre().equals("Aditivo")) {
                referenciaAditivo = materiaPrima.getCantidad();
            }
            if (materiaPrima.getNombre().equals("Agua")) {
                referenciaAgua = materiaPrima.getCantidad();
            }
            if (materiaPrima.getNombre().equals("Cemento")) {
                double porcentajeCemento = (double) materiaPrima.getCantidad() / produccionRequestDTO.getTotalMezcla();
                referenciaCemento = materiaPrima.getCantidad();
                estadisticas.put("%Cemento ", redondearDosDecimales(porcentajeCemento * 100));
            }
            if (materiaPrima.getNombre().equals("Triturado")) {
                double porcentajeTriturado = (double) materiaPrima.getCantidad() / produccionRequestDTO.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeTriturado * 100));
            }
            if (materiaPrima.getNombre().equals("Arena Gruesa")) {
                double porcentajeArenaGruesa = (double) materiaPrima.getCantidad() / produccionRequestDTO.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeArenaGruesa * 100));
            }
            if (materiaPrima.getNombre().equals("Arena Fina")) {
                double porcentajeArenaFina = (double) materiaPrima.getCantidad() / produccionRequestDTO.getTotalMezcla();
                estadisticas.put("% " + materiaPrima.getNombre(), redondearDosDecimales(porcentajeArenaFina * 100));
            }
        }

        double kiloCementoPorProducto = referenciaCemento / produccionRequestDTO.getCantidadProductos();
        estadisticas.put("K C / Pdto ", redondearDosDecimales(kiloCementoPorProducto));

        double porcentajeAgua = referenciaAgua / referenciaCemento;
        estadisticas.put("% Agua ", redondearDosDecimales(porcentajeAgua * 100));

        double porcentajeSobrante = (double) produccionRequestDTO.getSobranteMezcla() / produccionRequestDTO.getTotalMezcla();
        estadisticas.put("% Sobrante ", redondearDosDecimales(porcentajeSobrante * 100));

        double porcentajeCementoPulir = (double) produccionRequestDTO.getCementoPulir() / produccionRequestDTO.getCantidadProductos();
        estadisticas.put("% cemento Pulir", redondearDosDecimales(porcentajeCementoPulir));

        double porcentajeAditivo = (referenciaAditivo / (referenciaCemento * 1000));
        estadisticas.put("% Aditivo", redondearDosDecimales(porcentajeAditivo * 100));

        return estadisticas;
    }

    private double redondearDosDecimales(double valor) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(valor));
    }
}
