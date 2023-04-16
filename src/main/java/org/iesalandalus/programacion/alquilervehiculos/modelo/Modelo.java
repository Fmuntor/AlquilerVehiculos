package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public abstract class Modelo {
    public IClientes clientes;
    public IAlquileres alquileres;
    public IVehiculos vehiculos;
    public IFuenteDatos fuenteDatos;

    protected Modelo(FactoriaFuenteDatos factoriaFuenteDatos){
        this.fuenteDatos=factoriaFuenteDatos.crear();
    }

    protected IClientes getClientes() {
		
		return this.clientes;
	}
	
	protected IVehiculos getVehiculos() {
		
		return this.vehiculos; 
	}
	
	protected IAlquileres getAlquileres() {
	
		return this.alquileres; 
	}

    public void setFuenteDatos(IFuenteDatos fuenteDatos){
        if (fuenteDatos == null) {
            throw new NullPointerException("La fuente de datos no puede ser nula.");
        }
        this.fuenteDatos=fuenteDatos;  
    }

    public void comenzar() throws OperationNotSupportedException{
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
		alquileres = fuenteDatos.crearAlquileres();

        clientes.comenzar();
		vehiculos.comenzar(); 
		alquileres.comenzar();
        
    }

    public void terminar(){
        clientes.terminar();
		vehiculos.terminar(); 
		alquileres.terminar();
        
    }

	public abstract void insertar(Cliente cliente)  throws Exception;
	
	public abstract void insertar(Vehiculo vehiculo) throws Exception; 
	
	public abstract void insertar(Alquiler alquiler) throws Exception; 
	
	public abstract Cliente buscar(Cliente cliente); 
	
	public abstract Vehiculo buscar(Vehiculo vehiculo); 

	public abstract Alquiler buscar(Alquiler alquiler); 
	
	public abstract void modificar(Cliente cliente, String nombre, String Telefono) throws Exception; 
	
	public abstract void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception; 
	
	public abstract void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception; 
	
	public abstract void borrar(Cliente cliente) throws Exception; 
	
	public abstract void borrar(Vehiculo vehiculo) throws Exception; 
	
	public abstract void borrar(Alquiler alquiler) throws Exception; 
	
	public abstract List<Cliente> getListaClientes(); 
	
	public abstract List<Vehiculo> getListaVehiculos(); 
	
	public abstract List<Alquiler> getListaAlquileres(); 
	
	public abstract List<Alquiler> getListaAlquileres(Cliente cliente); 
	
	public abstract List<Alquiler> getListaAlquileres(Vehiculo vehiculo); 

}