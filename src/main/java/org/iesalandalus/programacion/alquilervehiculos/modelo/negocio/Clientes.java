package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

public class Clientes {
    private ArrayList<Cliente> clientes;

    public Clientes(){
        clientes = new ArrayList<Cliente>();
    }

    public ArrayList<Cliente> get(){
        return new ArrayList<Cliente>(clientes);
    }

    public int getCantidad(){
        return clientes.size();
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
        }
        if(clientes.size()==0){
            // Si el arraylist está vacio, inserta el primer cliente.
            clientes.add(cliente);
        
        }else{
            for(int i=0;i<clientes.size();i++){
                if(clientes.get(i)==cliente){
                    throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
                }
            }
            clientes.add(cliente);
        }
        
    }

    public Cliente buscar(Cliente cliente){
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
        }
        if(clientes.size()==0){
            return null;
        }
        for(int i=0;i<=clientes.size();i++){
            if(clientes.get(i)==cliente){
                return cliente;
            }
        }
        return null;
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException{
        boolean borrado=false;
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
        }
        for(int i=0;i<clientes.size();i++){
            if(clientes.get(i)==cliente){
                clientes.remove(i);
                borrado=true;
            }
        }
        if(!borrado){
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
            
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
        }
        if(!clientes.contains(cliente)){
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
        if(nombre==null&&telefono!=null){
            cliente.setTelefono(telefono);
        }
        if(nombre!=null&&telefono==null){
            cliente.setNombre(nombre);
        }
        if(cliente!=null&&nombre!=null&&telefono!=null){
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
        }
    }
}
