package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;

public abstract class Modelo {
    public IClientes clientes;
    public IAlquileres alquileres;
    public IVehiculos vehiculos;
    public IFuenteDatos fuenteDatos;

    public void setFuenteDatos(IFuenteDatos fuenteDatos){
        if (fuenteDatos == null) {
            throw new NullPointerException("La fuente de datos no puede ser nula.");
        }
        this.fuenteDatos=fuenteDatos;  
    }

    public void comenzar() throws OperationNotSupportedException{
        clientes = fuenteDatos.crearClientes();
        alquileres = fuenteDatos.crearAlquileres();
        vehiculos = fuenteDatos.crearVehiculos();
        clientes.insertar(new Cliente("Juan","75719771E","999999999"));
        clientes.insertar(new Cliente("Juan","75719801Y","999999999"));
        
        clientes.insertar(new Cliente("Alberto","27231455F","999999999"));
       
        vehiculos.insertar(new Turismo("Seat", "Panda", "1121BBB", 15));
        vehiculos.insertar(new Furgoneta("Seat", "Panda", "1111BBB", 15,15));
        vehiculos.insertar(new Turismo("Seat", "Almera", "1111BBB", 15));
        vehiculos.insertar(new Autobus("KIA", "Panda", "1111BBB", 15));
        
        alquileres.insertar(new Alquiler(clientes.get().get(0), vehiculos.get().get(0), LocalDate.of(2022, 2, 20)));
        
    }

    public void terminar(){

    }

    abstract public void insertar(Cliente cliente) throws OperationNotSupportedException;

    abstract public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

    abstract public void insertar(Alquiler alquiler) throws OperationNotSupportedException, NullPointerException;

    abstract public Cliente buscar(Cliente cliente);

    abstract public Vehiculo buscar(Vehiculo vehiculo);

    abstract public Alquiler buscar(Alquiler alquiler);

    abstract public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

    abstract public void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
            throws NullPointerException, OperationNotSupportedException;

    // Se ha añadido un retorno de un booleano si se ha borrado, o devuelve null si no
    abstract public boolean borrar(Cliente cliente) throws OperationNotSupportedException;

    abstract public boolean borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

    abstract public boolean borrar(Alquiler alquiler) throws OperationNotSupportedException;

    abstract public ArrayList<Cliente> getClientes();

    abstract public ArrayList<Vehiculo> getTurismos();

    abstract public ArrayList<Alquiler> getAlquileres();

    abstract public ArrayList<Alquiler> getAlquileres(Cliente cliente);

    abstract public ArrayList<Alquiler> getAlquileres(Vehiculo vehiculo);

}