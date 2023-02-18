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

    public void comenzar(){
        clientes = new Clientes();
        alquileres = new Alquileres();
        turismos = new Turismos();
    }

    public void terminar(){
        System.out.println("CHAO");
    }

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
    /*public  void mostraString(){
        System.out.println(cliente);
        System.out.println(turismo);
        System.out.println(alquiler);
    }*/

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
        if(buscar(alquiler)==null){

        }
        alquileres.devolver(alquiler, fechaDevolucion);
    }

    public void borrar(Cliente cliente){
        // Buscar al cliente
        buscar(cliente); 
        // Borrar todos los alquileres del cliente
        //for(Alquiler alquiler : getAlquileres(cliente)){
            borrar(alquiler);
        //}
        // Borrar cliente
        borrar(cliente);
    }

    public void borrar(Turismo turismo){
        // Buscar al turismo
        buscar(turismo);
        // Borrar todos los alquileres del turismo
        for(Alquiler alquiler : getAlquileres(turismo)){
            borrar(alquiler);
        }
        // Borrar turismo
        borrar(turismo);
    }

    public void borrar(Alquiler alquiler){
        borrar(alquiler);
    }

    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> ArrayClientes = clientes.get();
        return ArrayClientes;
    }

    public ArrayList<Cliente> getTurismos(){
        ArrayList<Cliente> ArrayTurismos = getTurismos();
        return ArrayTurismos;
    }

    public ArrayList<Cliente> getAlquileres(){
        ArrayList<Cliente> ArrayAlquileres = getAlquileres();
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
