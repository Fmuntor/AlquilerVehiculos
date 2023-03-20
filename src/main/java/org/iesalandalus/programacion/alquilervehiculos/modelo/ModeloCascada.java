
package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo {

    public ModeloCascada(IFuenteDatos fuenteDatos){
        setFuenteDatos(fuenteDatos);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException{
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Alquiler alquiler) throws OperationNotSupportedException, NullPointerException{
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
    public Cliente buscar(Cliente cliente){
        return clientes.buscar(cliente);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Alquiler buscar(Alquiler alquiler){
        return alquileres.buscar(alquiler);
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{
        clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void devolver(Alquiler alquiler, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        // Si algun alquiler tiene fecha de devolucion nula, y ese alquiler es el mismo que el introducido, se devuelve el alquiler.
        boolean devuelto=false;
        for(Alquiler alquilerDev : getAlquileres()){
            if((alquilerDev.getFechaDevolucion()==null)&&((alquilerDev.getCliente().equals(alquiler.getCliente())))||(alquilerDev.getVehiculo().equals(alquiler.getVehiculo()))){
                alquileres.devolver(alquiler, fechaDevolucion);
                devuelto=true;
            }
        }
        if(!devuelto){
            throw new OperationNotSupportedException("ERROR: No hay ningun alquiler sin fecha de devolución para ese cliente o vehiculo.");
        }

    }
    // Se ha añadido un retorno de un booleano si se ha borrado, o devuelve null si no
    @Override
    public boolean borrar(Cliente cliente) throws OperationNotSupportedException{
        boolean borrado = false;
        if (cliente == null) {
            throw new NullPointerException("El cliente para borrar no puede ser nulo.");
        }
        // Si el cliente tiene un alquiler sin fecha de devolución, impide su borrado
        for(Alquiler alquiler : getAlquileres()){
            if((alquiler.getCliente().equals(cliente))&&(alquiler.getFechaDevolucion()==null)){
                throw new OperationNotSupportedException("ERROR: El cliente introducido tiene un alquiler sin devolver.");
            }
        }

        for(Cliente cliente2 : getClientes()){
            clientes.borrar(cliente2);
            borrado=true;
        }
        if(!borrado){
            return false;
        }else{return true;}
    }

    @Override
    public boolean borrar(Vehiculo vehiculo) throws OperationNotSupportedException{
        boolean borrado = false;
        if (vehiculo == null) {
            throw new NullPointerException("El cliente para borrar no puede ser nulo.");
        }
        // Si el vehiculo tiene un alquiler sin fecha de devolución, impide su borrado
        for(Alquiler alquiler : getAlquileres()){
            if((alquiler.getVehiculo().equals(vehiculo))&&(alquiler.getFechaDevolucion()==null)){
                throw new OperationNotSupportedException("ERROR: El vehiculo introducido tiene un alquiler sin devolver.");
            }
        }
        for(Vehiculo turismo2 : getTurismos()){
            vehiculos.borrar(turismo2);
            borrado=true;
        }
        if(!borrado){
            return false;
        }else{return true;}
    }

    @Override
    public boolean borrar(Alquiler alquiler) throws OperationNotSupportedException{
        boolean borrado = false;
        if (alquiler == null) {
            throw new NullPointerException("El alquiler para borrar no puede ser nulo.");
        }
        for(Alquiler alquiler2 : getAlquileres()){
            alquileres.borrar(alquiler2);
            // Al eliminar un alquiler, se elimina su cliente asignado, así que he modificado el método para que devuelva un objeto tipo
            // Cliente, para posteriormente introducirlo al arrayList de clientes para así conservarlo en la base de datos.
            borrado=true;
        }
        if(!borrado){
            return false;
        }else{return true;}
    }

    @Override
    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> ArrayClientes = clientes.get();
        return ArrayClientes;
    }

    @Override
    public ArrayList<Vehiculo> getTurismos(){
        ArrayList<Vehiculo> ArrayTurismos = vehiculos.get();
        return ArrayTurismos;
    }

    @Override
    public ArrayList<Alquiler> getAlquileres(){
        ArrayList<Alquiler> ArrayAlquileres = alquileres.get();
        return ArrayAlquileres;
    }

    @Override
    public ArrayList<Alquiler> getAlquileres(Cliente cliente){
        ArrayList<Alquiler> alquileresCliente = alquileres.get(cliente);
        return alquileresCliente;
    }

    @Override
    public ArrayList<Alquiler> getAlquileres(Vehiculo vehiculo){
        ArrayList<Alquiler> alquileresTurismo = alquileres.get(vehiculo);
        return alquileresTurismo;
    }


}
