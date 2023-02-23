package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;


import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Clientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Turismos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Alquileres;



public class Modelo {
    private  Clientes clientes;
    private  Alquileres alquileres;
    private  Turismos turismos;

    public Modelo(){
    }

    public void comenzar() throws OperationNotSupportedException{
        clientes = new Clientes();
        alquileres = new Alquileres();
        turismos = new Turismos();
        clientes.insertar(new Cliente("Juan","75719771E","999999999"));
        turismos.insertar(new Turismo("Seat", "Panda", 15, "1111BBB"));
        alquileres.insertar(new Alquiler(clientes.get().get(0), turismos.get().get(0), LocalDate.of(2022, 2, 20)));
        
    }

    public void terminar(){}

    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        clientes.insertar(new Cliente(cliente));

    }

    public void insertar(Turismo turismo) throws OperationNotSupportedException{
        turismos.insertar(new Turismo(turismo));
        
    }

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException, NullPointerException{
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
        }
        if(buscar(alquiler.getCliente())==null){
            throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
        }else if(buscar(alquiler.getTurismo())==null){
            throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
        }
        alquileres.insertar(new Alquiler(alquiler.getCliente(), alquiler.getTurismo(), alquiler.getFechaAlquiler()));
    }

    public Cliente buscar(Cliente cliente){
        return clientes.buscar(cliente);
    }

    public Turismo buscar(Turismo turismo){
        return turismos.buscar(turismo);
    }

    public Alquiler buscar(Alquiler alquiler){
        return alquileres.buscar(alquiler);
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{
        clientes.modificar(cliente, nombre, telefono);
    }

    public void devolver(Alquiler alquiler, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        // Si algun alquiler tiene fecha de devolucion nula, y ese alquiler es el mismo que el introducido, se devuelve el alquiler.
        boolean devuelto=false;
        for(Alquiler alquilerDev : getAlquileres()){
            if((alquilerDev.getFechaDevolucion()==null)&&((alquilerDev.getCliente().equals(alquiler.getCliente())))||(alquilerDev.getTurismo().equals(alquiler.getTurismo()))){
                alquileres.devolver(alquiler, fechaDevolucion);
                devuelto=true;
            }
        }
        if(!devuelto){
            throw new OperationNotSupportedException("ERROR: No hay ningun alquiler sin fecha de devolución para ese cliente o turismo.");
        }

    }
    // Se ha añadido un retorno de un booleano si se ha borrado, o devuelve null si no
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

    public boolean borrar(Turismo turismo) throws OperationNotSupportedException{
        boolean borrado = false;
        if (turismo == null) {
            throw new NullPointerException("El cliente para borrar no puede ser nulo.");
        }
        // Si el turismo tiene un alquiler sin fecha de devolución, impide su borrado
        for(Alquiler alquiler : getAlquileres()){
            if((alquiler.getTurismo().equals(turismo))&&(alquiler.getFechaDevolucion()==null)){
                throw new OperationNotSupportedException("ERROR: El turismo introducido tiene un alquiler sin devolver.");
            }
        }
        for(Turismo turismo2 : getTurismos()){
            turismos.borrar(turismo2);
            borrado=true;
        }
        if(!borrado){
            return false;
        }else{return true;}
    }

    public boolean borrar(Alquiler alquiler) throws OperationNotSupportedException{
        boolean borrado = false;
        if (alquiler == null) {
            throw new NullPointerException("El alquiler para borrar no puede ser nulo.");
        }
        for(Alquiler alquiler2 : getAlquileres()){
            alquileres.borrar(alquiler2);
            // Al eliminar un alquiler, se elimina su cliente asignado, así que he modificado el método para que devuelva un objeto tipo
            // Cliente, para posteriormente introducirlo al arrayList de clientes para así conservarlo en la base de datos.
            // clientes.insertar(new Cliente(alquiler2.getCliente()));
            borrado=true;
        }
        if(!borrado){
            return false;
        }else{return true;}
    }

    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> ArrayClientes = clientes.get();
        return ArrayClientes;
    }

    public ArrayList<Turismo> getTurismos(){
        ArrayList<Turismo> ArrayTurismos = turismos.get();
        return ArrayTurismos;
    }

    public ArrayList<Alquiler> getAlquileres(){
        ArrayList<Alquiler> ArrayAlquileres = alquileres.get();
        return ArrayAlquileres;
    }

    public ArrayList<Alquiler> getAlquileres(Cliente cliente){
        ArrayList<Alquiler> alquileresCliente = alquileres.get(cliente);
        return alquileresCliente;
    }

    public ArrayList<Alquiler> getAlquileres(Turismo turismo){
        ArrayList<Alquiler> alquileresTurismo = alquileres.get(turismo);
        return alquileresTurismo;
    }


}
