package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {
    Modelo modelo;
    Vista vista;

    public Controlador(Vista vista, Modelo modelo){
        this.vista = vista;
        this.modelo = modelo;
    }

    public void comenzar() throws OperationNotSupportedException{
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        modelo.insertar(cliente);
    }

    public void insertar(Turismo turismo) throws OperationNotSupportedException{
        modelo.insertar(turismo);
    }

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException{
        modelo.insertar(alquiler);
    }

    public Cliente buscar(Cliente cliente){
        return modelo.buscar(cliente);
    }

    public Turismo buscar(Turismo turismo){
        return modelo.buscar(turismo);
    }

    public Alquiler buscar(Alquiler alquiler){
        return modelo.buscar(alquiler);
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{
        modelo.modificar(cliente, nombre, telefono);
    }

    public void devolver(Alquiler alquiler, LocalDate FechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        modelo.devolver(alquiler, FechaDevolucion);
    }

    public boolean borrar(Cliente cliente) throws OperationNotSupportedException{
        return modelo.borrar(cliente);
    }
    public boolean borrar(Turismo turismo) throws OperationNotSupportedException{
        return modelo.borrar(turismo);
    }
    public boolean borrar(Alquiler alquiler) throws OperationNotSupportedException{
        return modelo.borrar(alquiler);
    }

    public ArrayList<Cliente> getClientes(){
        return modelo.getClientes();
    }

    public ArrayList<Turismo> getTurismos(){
        return modelo.getTurismos();
    }

    public ArrayList<Alquiler> getAlquileres(){
        return modelo.getAlquileres();
    }
}
