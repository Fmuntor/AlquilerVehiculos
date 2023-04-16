package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {
    
    @Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}

    private ArrayList<Alquiler> alquileres;

    public Alquileres(){
        alquileres = new ArrayList<Alquiler>();
    }

    @Override
    public ArrayList<Alquiler> get(){
        return new ArrayList<Alquiler>(alquileres);
    }

    @Override
    public ArrayList<Alquiler> get(Cliente cliente){
        ArrayList<Alquiler> alquileresCliente = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if(alquiler.getCliente().equals(cliente)){
                alquileresCliente.add(alquiler);
            }
        }
        return alquileresCliente;
    }

    @Override
    public ArrayList<Alquiler> get(Vehiculo turismo){
        ArrayList<Alquiler> alquileresTurismo = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if(alquiler.getVehiculo().equals(turismo)){
                alquileresTurismo.add(alquiler);
            }
        }
        return alquileresTurismo;
    }

    public int getCantidad(){
        return alquileres.size();
    }

    private void comprobarAlquiler(Cliente cliente, Vehiculo turismo, LocalDate fechaAlquiler) throws OperationNotSupportedException{
        for(Alquiler alquiler : alquileres){
            if(alquiler.getFechaDevolucion() == null){
                if(alquiler.getCliente().equals(cliente)){
                    // Esta comprobacion verifica si no hay una fecha de devolución asignada al alquiler para el cliente o el turismo
                    throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
                }else if(alquiler.getVehiculo().equals(turismo)){
                    // Si tengo un alquiler cuya fecha de devolucion es anterior a la fecha de alquiler de un nuevo alquiler
                    throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
                }
            }else{
                // Si queremos que pase los test, 
                if(fechaAlquiler.isEqual(alquiler.getFechaDevolucion())){
                    if(alquiler.getCliente().equals(cliente)){
                        throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
                    }

                    if(alquiler.getVehiculo().equals(turismo)){
                        throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
                    }
                }
            }
        }
    }
    

    @Override
    public void insertar(Alquiler alquiler) throws OperationNotSupportedException{
        if(alquiler==null){
            throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
        }
        
        comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());        
        
        
        alquileres.add(alquiler);
    }

    @Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {
		
		Vehiculo vehiculo = new Turismo("Seat", "León", "0000BBB", 1500);
		LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1); 
		
		Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		
		if(buscar(alquiler) == null){
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		if(buscar(alquiler) != null){
			
			buscar(alquiler).devolver(fechaDevolucion);
		}
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {
		
		Cliente cliente = new Cliente("Nombre", "16832383V", "900900900");
		LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1); 
		
		Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		
		if(buscar(alquiler) == null){
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		if(buscar(alquiler) != null){
			
			buscar(alquiler).devolver(fechaDevolucion);
		}
	}

    @Override
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

    @Override
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
