package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

public class Turismos {
    private ArrayList<Turismo> turismos;

    public Turismos(){
        turismos = new ArrayList<Turismo>();
    }

    public ArrayList<Turismo> get(){
        return new ArrayList<Turismo>(turismos);
    }

    public int getCantidad(){
        return turismos.size();
    }
    
    public void insertar(Turismo turismo) throws OperationNotSupportedException{
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

    public Turismo buscar(Turismo turismo){
        if(turismo==null){
            throw new NullPointerException("ERROR: No se puede buscar un turismo nulo.");
        }
        if(turismos.size()==0){
            return null;
        }
        for(Turismo turismo2 : turismos){
            if(turismo2.getMatricula().equals(turismo.getMatricula())){
                return turismo2;
            }
        }return null;
    }

    public void borrar(Turismo turismo) throws OperationNotSupportedException{
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
