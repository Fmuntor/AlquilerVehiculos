package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class Vehiculos implements IVehiculos {
    private ArrayList<Vehiculo> turismos;

    @Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}

    public Vehiculos(){
        turismos = new ArrayList<Vehiculo>();
    }

    @Override
    public ArrayList<Vehiculo> get(){
        return new ArrayList<Vehiculo>(turismos);
    }

    public int getCantidad(){
        return turismos.size();
    }
    
    @Override
    public void insertar(Vehiculo turismo) throws OperationNotSupportedException{
        if (turismo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un turismo nulo.");
        }
        if(turismos.size()==0){
            // Si el arraylist está vacio, inserta el primer turismo.
            turismos.add(turismo);
        
        }else{
            for(int i=0;i<turismos.size();i++){
                if(turismos.get(i)==turismo){
                    throw new OperationNotSupportedException("ERROR: Ya existe un turismo con esa matrícula.");
                }
            }
            turismos.add(turismo);
        }
    }    

    @Override
    public Vehiculo buscar(Vehiculo turismo){
        if(turismo==null){
            throw new NullPointerException("ERROR: No se puede buscar un turismo nulo.");
        }
        if(turismos.size()==0){
            return null;
        }
        for(Vehiculo turismo2 : turismos){
            if(turismo2.getMatricula().equals(turismo.getMatricula())){
                return turismo2;
            }
        }return null;
    }

    @Override
    public void borrar(Vehiculo turismo) throws OperationNotSupportedException{
        boolean borrado=false;
        if (turismo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un turismo nulo.");
        }
        for(int i=0;i<turismos.size();i++){
            if(turismos.get(i)==turismo){
                turismos.remove(i);
                borrado=true;
            }
        }
        if(!borrado){
            throw new OperationNotSupportedException("ERROR: No existe ningún turismo con esa matrícula.");
        }

    }
}
