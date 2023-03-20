package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

public interface IClientes {

    ArrayList<Cliente> get();

    int getCantidad();

    void insertar(Cliente cliente) throws OperationNotSupportedException;

    Cliente buscar(Cliente cliente);

    void borrar(Cliente cliente) throws OperationNotSupportedException;

    void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

}