package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {
    private ArrayList<Cliente> clientes;

    @Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}

    public Clientes(){
        clientes = new ArrayList<Cliente>();
    }

    @Override
    public ArrayList<Cliente> get(){
        return new ArrayList<Cliente>(clientes);
    }

    public int getCantidad(){
        return clientes.size();
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
        }
        if(clientes.size()==0){
            // Si el arraylist está vacio, inserta el primer cliente.
            clientes.add(cliente);
        }else{
            for(Cliente cliente2 : clientes){
                if(cliente2.getDni().equals(cliente.getDni())){
                    throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI: "+cliente2);
                }
            }
            clientes.add(cliente);
        }
        
    }

    @Override
    public Cliente buscar(Cliente cliente){
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
        }
        if(clientes.size()==0){
            return null;
        }
        for(Cliente cliente2 : clientes){
            if(cliente2.getDni().equals(cliente.getDni())){
                return cliente2;
            }
        }
        return null;
    }

    @Override
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

    @Override
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
