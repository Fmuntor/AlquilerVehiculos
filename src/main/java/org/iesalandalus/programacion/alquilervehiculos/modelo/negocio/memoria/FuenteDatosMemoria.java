package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosMemoria implements IFuenteDatos{

    @Override
    public IClientes crearClientes(){
    /*
        Al devolver un objeto de tipo Clientes como un objeto de tipo IClientes, 
        se está aprovechando el polimorfismo que permite que cualquier objeto que 
        implemente una interfaz pueda ser tratado como un objeto de dicha interfaz.
        */
    return new Clientes();
    }

    @Override
    public IVehiculos crearVehiculos(){  
        return new Vehiculos();
    }

    @Override
    public IAlquileres crearAlquileres(){
        return new Alquileres();
    }
    
}
