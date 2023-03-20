package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.ArrayList;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {
    private Modelo modelo;
    private Vista vista;

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
    }

    public void comenzar() throws Exception{
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Cliente cliente) throws Exception{
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

    public void devolver(Alquiler alquiler, LocalDate FechaDevolucion) throws NullPointerException, Exception{
        modelo.devolver(alquiler, FechaDevolucion);
    }

    public boolean borrar(Cliente cliente) throws Exception{
        return modelo.borrar(cliente);
    }
    public boolean borrar(Vehiculo turismo) throws Exception{
        return modelo.borrar(turismo);
    }
    public boolean borrar(Alquiler alquiler) throws Exception{
        return modelo.borrar(alquiler);
    }

    public ArrayList<Cliente> getClientes(){
        return modelo.getClientes();
    }

    public ArrayList<Vehiculo> getTurismos(){
        return modelo.getTurismos();
    }

    public ArrayList<Alquiler> getAlquileres(){
        return modelo.getAlquileres();
    }
}
