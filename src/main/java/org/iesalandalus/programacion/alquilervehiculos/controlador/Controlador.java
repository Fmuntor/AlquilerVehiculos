package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVistas;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {
    private Modelo modelo;
    private Vista vista;
    private static Controlador instancia; 
    /*
    public Controlador(Vista vista, Modelo modelo){
        if (modelo == null) {
			throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
		}

        this.vista = vista;
        this.modelo = modelo;
        this.vista.setControlador(this);
    }*/
    private Controlador(){

        FactoriaFuenteDatos fuenteDatos = FactoriaFuenteDatos.FICHEROS;
        FactoriaVistas vistas = FactoriaVistas.GRAFICOS;
        //Vista vistaTexto = new VistaTexto();
        //Controlador controlador = new Controlador(vistaTexto, modeloCascada);
        Modelo modeloCascada = new ModeloCascada(fuenteDatos);
        this.modelo = modeloCascada;
        this.vista = vistas.crear();
    }

    public static Controlador getInstancia(){
        if(instancia == null) {
			
			instancia = new Controlador(); 
        }

		return instancia;
    }

    public void comenzar() throws Exception{
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        System.exit(0);
    }

    public void insertar(Cliente cliente) throws Exception{
        modelo.insertar(cliente);
    }

    public void insertar(Vehiculo turismo) throws Exception{
        modelo.insertar(turismo);
    }

    public void insertar(Alquiler alquiler) throws Exception{
        modelo.insertar(alquiler);
    }

    public Cliente buscar(Cliente cliente){
        return modelo.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo turismo){
        return modelo.buscar(turismo);
    }

    public Alquiler buscar(Alquiler alquiler){
        return modelo.buscar(alquiler);
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws Exception{
        modelo.modificar(cliente, nombre, telefono);
    }

    public void devolver(Cliente cliente, LocalDate FechaDevolucion) throws Exception{
        modelo.devolver(cliente, FechaDevolucion);
    }

    public void devolver(Vehiculo vehiculo, LocalDate FechaDevolucion) throws Exception{
        modelo.devolver(vehiculo, FechaDevolucion);
    }

    public void borrar(Cliente cliente) throws Exception {
		
		modelo.borrar(cliente);
	}
	
	public void borrar(Vehiculo vehiculo) throws Exception{
		
		modelo.borrar(vehiculo);
	}
	
	public void borrar(Alquiler alquiler) throws Exception{
	
		modelo.borrar(alquiler);
	}

    public List<Cliente> getClientes() {
		
		return modelo.getListaClientes();
	}
	
	public List<Vehiculo> getVehiculos() {
		
		return modelo.getListaVehiculos(); 
	}
	
	public List<Alquiler> getAlquileres() {
		
		return modelo.getListaAlquileres(); 
	}	
	
	public List<Alquiler> getAlquileres(Cliente cliente) {
		
		return modelo.getListaAlquileres(cliente); 
	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {
	
		return modelo.getListaAlquileres(vehiculo); 
	}
}
