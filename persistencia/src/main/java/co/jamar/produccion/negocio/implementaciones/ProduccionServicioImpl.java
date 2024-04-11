package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.ProduccionServicio;
import co.jamar.produccion.persistencia.entidades.*;
import co.jamar.produccion.persistencia.repositorios.*;
import co.jamar.produccion.web.dto.ProduccionRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public void guardarProduccion(ProduccionRequestDTO produccionRequestDTO) throws Exception {
        Bitacora bitacora = obtenerBitacoraPorConsecutivo(produccionRequestDTO.getBitacora().getConsecutivo());
        double productividad = calcularProductividad(produccionRequestDTO.getHoraInicio(), produccionRequestDTO.getHoraFin());

        Produccion produccion = new Produccion();
        produccion.setCantidadProductos(produccionRequestDTO.getCantidadProductos());
        produccion.setBitacora(bitacora);
        produccion.setHoraInicio(produccionRequestDTO.getHoraInicio());
        produccion.setHoraFin(produccionRequestDTO.getHoraFin());
        produccion.setProductividad(productividad);
        produccion.setSobranteMezcla(produccionRequestDTO.getSobranteMezcla());
        produccion.setTotalMezcla(produccionRequestDTO.getSobranteMezcla());

        Produccion produccionGuardada = produccionRepo.save(produccion);

        asociarDatosProduccion(produccionGuardada, produccionRequestDTO.getListaDeMateriasPrimas());
    }

    public void asociarDatosProduccion(Produccion produccionGuardada, List<MateriaPrima> listaMateriasPrimas){

        List<MateriaPrima>listaMateriasPrimasGuardadas = new ArrayList<>();
        List<MateriaPrimaProveedor>listaMateriasPrimasProveedores = new ArrayList<>();

        for(MateriaPrima materiaPrima : listaMateriasPrimas){
            MateriaPrima materiaPrimaGuardar = new MateriaPrima();
            materiaPrimaGuardar.setNombre(materiaPrima.getNombre());
            materiaPrimaGuardar.setCantidad(materiaPrima.getCantidad());
            MateriaPrima materiaPrimaGuardada = materiaPrimaRepo.save(materiaPrimaGuardar);
            listaMateriasPrimasGuardadas.add(materiaPrimaGuardada);
        }

        for(MateriaPrima materiaPrima : listaMateriasPrimasGuardadas){
            Optional<Proveedor> proveedorMaterial = proveedorRepo.obtenerProveedorNombreProducto(materiaPrima.getNombre());
            MateriaPrimaProveedor materiaPrimaProveedor = new MateriaPrimaProveedor();
            materiaPrimaProveedor.setProveedor(proveedorMaterial.get());
            materiaPrimaProveedor.setMateriaPrima(materiaPrima);
            materiaPrimaProveedorRepo.save(materiaPrimaProveedor);
            listaMateriasPrimasProveedores.add(materiaPrimaProveedor);
        }

        for(MateriaPrimaProveedor materiaPrimaProveedor : listaMateriasPrimasProveedores){
            MaterialProducto materialProducto = new MaterialProducto();
            materialProducto.setMateriaPrimaProveedor(materiaPrimaProveedor);
            materialProducto.setProduccion(produccionGuardada);
            materialProductoRepo.save(materialProducto);
        }

    }

    public double calcularProductividad(LocalTime horaInicion, LocalTime horaFin){
        return 3;
    }

    public static double calcularDiferenciaTiempoEnMinutos(LocalTime horaInicio, LocalTime horaFin) {
        // Calcular la diferencia de tiempo entre la hora de inicio y la hora de fin
        Duration diferencia = Duration.between(horaInicio, horaFin);

        // Convertir la diferencia de tiempo a minutos como un double
        return (double) diferencia.toMinutes()/60;
    }

    public Bitacora obtenerBitacoraPorConsecutivo(int consecutivo)throws Exception{
        Optional<Bitacora>bitacoraOptional = bitacoraRepo.obtenerBitacoraPorConsecutivo(consecutivo);
        if(bitacoraOptional.isEmpty()){
            throw new Exception("Bitacora no existe");
        }
        return bitacoraOptional.get();
    }
}
