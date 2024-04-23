package co.jamar.produccion.negocio.implementaciones;

import co.jamar.produccion.negocio.servicios.BitacoraServicio;
import co.jamar.produccion.persistencia.entidades.Bitacora;
import co.jamar.produccion.persistencia.entidades.Empleado;
import co.jamar.produccion.persistencia.entidades.Maquina;
import co.jamar.produccion.persistencia.entidades.Producto;
import co.jamar.produccion.persistencia.repositorios.BitacoraRepo;
import co.jamar.produccion.persistencia.repositorios.EmpleadoRepo;
import co.jamar.produccion.persistencia.repositorios.MaquinaRepo;
import co.jamar.produccion.persistencia.repositorios.ProductoRepo;
import co.jamar.produccion.web.dto.BitacoraRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BitacoraServicioImpl implements BitacoraServicio {

    private final BitacoraRepo bitacoraRepo;

    private final MaquinaRepo maquinaRepo;

    private final ProductoRepo productoRepo;

    private final EmpleadoRepo empleadoRepo;

    public BitacoraServicioImpl(BitacoraRepo bitacoraRepo, MaquinaRepo maquinaRepo, ProductoRepo productoRepo, EmpleadoRepo empleadoRepo) {
        this.bitacoraRepo = bitacoraRepo;
        this.maquinaRepo = maquinaRepo;
        this.productoRepo = productoRepo;
        this.empleadoRepo = empleadoRepo;
    }

    @Override
    public void guardarBitacota(BitacoraRequestDTO bitacoraRequestDTO) throws Exception {
        boolean esBitacoraPrincipal = bitacoraRepo.existsEsPrincipalEnUltimaFecha(bitacoraRequestDTO.getFecha());

        Maquina maquina = obtenerMaquina(bitacoraRequestDTO.getMaquinaDTO().getNombre());

        Empleado empleado = obtenerOCrearEmpleado(bitacoraRequestDTO.getEmpleadoDTO().getNombre());

        Producto producto = obtenerOCrearProducto(bitacoraRequestDTO.getProductoDTO().getNombre(), bitacoraRequestDTO.getProductoDTO().getReferencia());

        Bitacora bitacora = new Bitacora();
        bitacora.setConsecutivo(bitacoraRequestDTO.getConsecutivo());
        bitacora.setFecha(bitacoraRequestDTO.getFecha());
        bitacora.setEsPrincipal(!esBitacoraPrincipal);
        bitacora.setMaquina(maquina);
        bitacora.setEmpleado(empleado);
        bitacora.setProducto(producto);

        bitacoraRepo.save(bitacora);
    }

    @Override
    public void validarExisteBitacora(int numeroBitacora) throws Exception {
        Optional<Bitacora> existeBitacora = bitacoraRepo.obtenerBitacoraPorConsecutivo(numeroBitacora);
        if(existeBitacora.isPresent()){
            throw new Exception("Bitacora ya existe");
        }
    }

    private Maquina obtenerMaquina(String nombreMaquina) throws Exception {
        Optional<Maquina> maquinaOptional = maquinaRepo.encontrarPorNombre(nombreMaquina);
        if(maquinaOptional.isEmpty()){
            throw new Exception("Maquina no existe");
        }
        return maquinaOptional.get();
    }

    private Empleado obtenerOCrearEmpleado(String nombreEmpleado) throws Exception {
        Optional<Empleado> empleadoOptional = empleadoRepo.encontrarPorNombre(nombreEmpleado);

        if(empleadoOptional.isEmpty()){
            throw new Exception("Empleado no existe");
        }
        return empleadoOptional.get();
    }

    private Producto obtenerOCrearProducto(String nombreProducto, String referencia) throws Exception {
        Optional<Producto> productoOptional = productoRepo.encontrarPorNombreYReferencia(nombreProducto, referencia);

        if(productoOptional.isEmpty()){
            throw new Exception("Producto no existe");
        }
        return productoOptional.get();
    }


    private static Producto getProducto(BitacoraRequestDTO bitacoraRequestDTO) {
        Producto producto = new Producto();
        producto.setNombre(bitacoraRequestDTO.getProductoDTO().getNombre());
        producto.setReferencia(bitacoraRequestDTO.getProductoDTO().getReferencia());
        producto.setComplemento(bitacoraRequestDTO.getProductoDTO().getComplemento());
        producto.setPeso(bitacoraRequestDTO.getProductoDTO().getPeso());
        producto.setReferenciaP1(bitacoraRequestDTO.getProductoDTO().getReferenciaP1());
        producto.setLinea(bitacoraRequestDTO.getProductoDTO().getLinea());
        return producto;
    }
}
