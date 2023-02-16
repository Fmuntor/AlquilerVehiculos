package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

public class Alquileres {
    //Alquiler alquiler;
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

    /*private void comprobarAlquiler(Cliente cliente, Turismo turimo, LocalDate fechaAlquiler ){
        for (Alquiler alquiler : alquileres) {
            if(alquiler.getFechaDevolucion()==null){
                System.out.println("NO FECHA DEV ASIG");
            }
            if(!alquiler.getFechaDevolucion().isBefore(LocalDate.now())){
                System.out.println("hay 1 alquiler devuelto");
            }
        }
    }*/

    private void comprobarAlquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) throws OperationNotSupportedException{
        for(Alquiler alquiler : alquileres){
            if(alquiler.getFechaDevolucion() == null){
                if(alquiler.getCliente().equals(cliente) || alquiler.getTurismo().equals(turismo)){
                    // Esta comprobacion verifica si no hay una fecha de devolución asignada al alquiler para el cliente o el turismo
                    throw new OperationNotSupportedException("Ya existe un alquiler en curso para ese cliente o ese turismo.");
                }
            }else if(alquiler.getCliente().equals(cliente) || alquiler.getTurismo().equals(turismo)){
                if(alquiler.getFechaDevolucion().isAfter(fechaAlquiler)){
                    // Esta comprobacion verifique que para el cliente o turismo asignado a un alquiler, la fecha de devolución asignada es antes que la fecha de alquiler.
                    throw new OperationNotSupportedException("Ya existe un alquiler para ese cliente o ese turismo con fecha de devolución posterior a la fecha de alquiler.");
                }
            }
        }
    }
    

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException{

        if(alquiler==null){
            throw new NullPointerException("Alquiler nulo");
        }
        comprobarAlquiler(alquiler.getCliente(), alquiler.getTurismo(), alquiler.getFechaAlquiler());

        alquileres.add(alquiler);
    }

    public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        for (Alquiler alquiler : alquileres){
            if(alquiler.getCliente().equals(cliente)){
                alquiler.devolver(fechaDevolucion);
            }
        }
    }

    public Alquiler buscar(Alquiler alquiler){
        if(alquileres.contains(alquiler)){
            return alquiler;
        }else{
            return null;
        }
    }

    public void borrar(Alquiler alquiler){
        if(buscar(alquiler)!=null){
            alquileres.remove(alquiler);
        }
    }
}
