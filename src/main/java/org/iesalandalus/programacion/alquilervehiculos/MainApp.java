package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public class MainApp {
	public static void main(String[] args) {

		// Introducir el patron singleton para asignar las factorias al controlador y no necesitar Lanzadora. 
		// Luego, hemos cambiado la visibilidad del constructor de Controlador a private,
		// ya que habrá un getInstancia(), cambiandolo de /*
		/*
		 * public Controlador(Vista vista, Modelo modelo){
        if (modelo == null) {
			throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
		}

        this.vista = vista;
        this.modelo = modelo;
        this.vista.setControlador(this);
    	}
		
		a 

		private Controlador() {
		
		FactoriaFuenteDatos fuenteDatos = FactoriaFuenteDatos.FICHEROS; 
		FactoriaVistas vistas = FactoriaVistas.GRAFICOS;
		
		Modelo modeloCascada = new ModeloCascada(fuenteDatos);
		
		this.modelo = modeloCascada; 
		this.vista = vistas.crear();
		}
		
		con su getInstancia correspondiente. Ahora no se asignan la vista y el controlador por parametro.
		
		Con esto, consegimos un modelo-vista-controlador mas estructurado y evitamos la creacion de Launcher, 
		ya que es mas sencillo y eficiente en memoria.

		Finalmente, implementamos VistaGrafica y FactoriaVistas, y incluimos en la creación del 
		Controlador la FactoriaVistas de modo que lo hacemos con FuenteDatos.
		
		
		try {
			
			FactoriaFuenteDatos fuenteDatos = FactoriaFuenteDatos.FICHEROS;

			Vista vistaTexto = new VistaTexto();
			Modelo modeloCascada = new ModeloCascada(fuenteDatos);

			Controlador controlador = new Controlador(vistaTexto, modeloCascada);
			controlador.getInstancia().comenzar();
		
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}*/
		try {
			
			Controlador.getInstancia().comenzar(); 
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		} 
	}
}