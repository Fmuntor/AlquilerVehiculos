package org.iesalandalus.programacion.alquilervehiculos.vista;

import javafx.application.Application;


public abstract class Vista extends Application{
    /*protected Controlador controlador;

    public void setControlador(Controlador controlador){
        if(!(controlador==null)) {
			
			this.controlador=controlador; 
		}
    }*/
    public abstract void comenzar() throws Exception;
    public abstract void terminar();
    

}