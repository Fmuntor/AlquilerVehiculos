package org.iesalandalus.programacion.alquilervehiculos;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class MainApp {
	public static void main(String[] args) throws OperationNotSupportedException {
		// Crear patrón MVC
		Vista vista = new Vista();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador(vista, modelo);
		
		// Establecer el controlador en la vista
		vista.setControlador(controlador);

		// Crear modelo y vista
		controlador.comenzar();
		
		// Ánimo!!!!
	}

}
