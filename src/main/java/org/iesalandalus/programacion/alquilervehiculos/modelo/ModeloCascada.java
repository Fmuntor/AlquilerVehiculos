
package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class ModeloCascada extends Modelo {

    public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos){
		
		super(factoriaFuenteDatos); 
		factoriaFuenteDatos.crear(); 
	}

    @Override
    public void insertar(Cliente cliente) throws Exception{
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws Exception{
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Alquiler alquiler) throws Exception{
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
        }
        if(buscar(alquiler.getCliente())==null){
            throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
        }else if(buscar(alquiler.getVehiculo())==null){
            throw new OperationNotSupportedException("ERROR: No existe el vehiculo del alquiler.");
        }
        alquileres.insertar(new Alquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler()));
    }

    @Override
    public Cliente buscar(Cliente cliente)  throws Exception{
        /*Cliente clienteBuscar = new Cliente("Null", cliente.getDni(), "111111111");
        try {
            clienteBuscar = clientes.buscar(cliente);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clienteBuscar == null) {
            return null;
        }else{
            return cliente;
        }*/
        return clientes.buscar(cliente);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) throws Exception{
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Alquiler buscar(Alquiler alquiler) throws Exception{
        return alquileres.buscar(alquiler);
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws Exception{
        clientes.modificar(cliente, nombre, telefono);
    }

    @Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {
		
		alquileres.devolver(cliente, fechaDevolucion); 
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {
		
		alquileres.devolver(vehiculo, fechaDevolucion); 
	}
    // Se ha añadido un retorno de un booleano si se ha borrado, o devuelve null si no
    public void borrar(Cliente cliente) throws Exception {
		
		clientes.borrar(cliente);
	}
	
	public void borrar(Vehiculo vehiculo) throws Exception {
		
		vehiculos.borrar(vehiculo);
	}
	
	public void borrar(Alquiler alquiler) throws Exception {
		
		alquileres.borrar(alquiler);
	}
	
    @Override
	public List<Cliente> getListaClientes() {
		
		return clientes.get();
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		
		return vehiculos.get(); 
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		
		return alquileres.get(); 
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		
		return alquileres.get(cliente);
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		
		return alquileres.get(vehiculo);
	}


}
