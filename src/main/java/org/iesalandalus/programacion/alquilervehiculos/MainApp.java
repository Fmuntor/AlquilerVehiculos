package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaFuenteVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.IFuenteVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.VistaTexto;

public class MainApp {
	public static void main(String[] args) throws Exception {
		boolean error=false;
		
		try{
			// Crear la interfaz de fuente de datos a usar en el programa
			IFuenteDatos fuenteDatos = FactoriaFuenteDatos.MEMORIA.crear();
			// Crear la interfaz de fuente de vistas a usar en el programa
			IFuenteVista fuenteVista = FactoriaFuenteVista.VISTA_TEXTO.crear();

			// Asignar las fuentes de vistas y datos a usar en el programa
			Modelo modeloCascada = new ModeloCascada(fuenteDatos);
			Vista vistaTexto = new VistaTexto(fuenteVista);

			// Crear controlador con la fuente de datos elegida
			Controlador controlador = new Controlador(vistaTexto,modeloCascada);

			controlador.comenzar();

		}catch(Exception e) {
			System.out.println(e.getMessage());
			error = true;
		}
		
	}

}
