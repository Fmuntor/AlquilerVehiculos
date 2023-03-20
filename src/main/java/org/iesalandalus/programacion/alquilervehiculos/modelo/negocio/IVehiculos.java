package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {
    
    ArrayList<Vehiculo> get();

    int getCantidad();

    void insertar(Vehiculo turismo) throws OperationNotSupportedException;

    Vehiculo buscar(Vehiculo turismo);

    void borrar(Vehiculo turismo) throws OperationNotSupportedException;

}