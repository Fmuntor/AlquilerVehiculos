package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {
    
	void comenzar();

	void terminar(); 
    
    List<Vehiculo> get();

    void insertar(Vehiculo turismo) throws Exception;

    Vehiculo buscar(Vehiculo turismo) throws Exception;

    void borrar(Vehiculo turismo) throws Exception;

}