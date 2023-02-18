package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

public class Alquileres {

    private ArrayList<Alquiler> alquileres;

    public Alquileres(){
        alquileres = new ArrayList<Alquiler>();
    }

    public ArrayList<Alquiler> get(){
        return new ArrayList<Alquiler>(alquileres);
    }

    public ArrayList<Alquiler> get(Cliente cliente){
        ArrayList<Alquiler> alquileresCliente = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if(alquiler.getCliente().equals(cliente)){
                alquileresCliente.add(alquiler);
            }
        }
        return alquileresCliente;
    }

    public ArrayList<Alquiler> get(Turismo turismo){
        ArrayList<Alquiler> alquileresTurismo = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if(alquiler.getTurismo().equals(turismo)){
                alquileresTurismo.add(alquiler);
            }
        }
        return alquileresTurismo;
    }

    public int getCantidad(){
        return alquileres.size();
    }

    private void comprobarAlquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) throws OperationNotSupportedException{
        for(Alquiler alquiler : alquileres){
            if(alquiler.getFechaDevolucion() == null){
                if(alquiler.getCliente().equals(cliente)){
                    // Esta comprobacion verifica si no hay una fecha de devolución asignada al alquiler para el cliente o el turismo
                    throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
                }else if(alquiler.getTurismo().equals(turismo)){
                    // Si tengo un alquiler cuya fecha de devolucion es anterior a la fecha de alquiler de un nuevo alquiler
                    throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
                }
            }else{
                if(fechaAlquiler.isBefore(alquiler.getFechaDevolucion())){
                    if(alquiler.getCliente().equals(cliente)){
                        throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
                    }else if(alquiler.getTurismo().equals(turismo)&& !alquiler.getFechaAlquiler().isAfter(fechaAlquiler)){

                    }
                }
            }
        }

        for(Alquiler alquiler : alquileres){
            if(alquiler.getFechaDevolucion() == null){
                if(alquiler.getCliente().equals(cliente)){
                    // Esta comprobacion verifica si no hay una fecha de devolución asignada al alquiler para el cliente o el turismo
                    throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
                }else if(alquiler.getTurismo().equals(turismo) && !alquiler.getFechaAlquiler().isAfter(fechaAlquiler) && !alquiler.getFechaDevolucion().isBefore(fechaAlquiler)){
                    // Si tengo un alquiler cuya fecha de devolucion es anterior a la fecha de alquiler de un nuevo alquiler
                    throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
                }
            }
        }

    }
    

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException{
        if(alquiler==null){
            throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
        }
        
        comprobarAlquiler(alquiler.getCliente(), alquiler.getTurismo(), alquiler.getFechaAlquiler());
        for (Alquiler alquiler2 : alquileres) {
        }
        alquileres.add(alquiler);
    }

    public void devolver(Alquiler alquiler, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
        }
        if(buscar(alquiler)==null){
            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
        }
        for (Alquiler alquilerAux : alquileres){
            if(alquilerAux.equals(alquiler)){
                alquilerAux.devolver(fechaDevolucion);
            }
        }
    }

    public Alquiler buscar(Alquiler alquiler){
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
        }
        if(alquileres.contains(alquiler)){
            return alquiler;
        }else{
            return null;
        }
    }

    public void borrar(Alquiler alquiler) throws OperationNotSupportedException{
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
        }
        if(buscar(alquiler)!=null){
            alquileres.remove(alquiler);
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
        }
    }
}
