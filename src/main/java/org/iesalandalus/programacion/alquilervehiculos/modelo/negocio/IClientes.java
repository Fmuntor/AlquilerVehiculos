package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

public interface IClientes {

	void comenzar();

	void terminar(); 
	
	List<Cliente> get();

	void insertar(Cliente cliente) throws Exception;

	void modificar(Cliente cliente, String nombre, String telefono) throws Exception;

	Cliente buscar(Cliente cliente) throws Exception;

	void borrar(Cliente cliente) throws Exception;

}