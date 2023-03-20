package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;


public abstract class Vista {
    protected Controlador controlador;
    protected IFuenteVista fuenteVista;

    protected void setFuenteVista(IFuenteVista fuenteVista) {
		
		this.fuenteVista=fuenteVista; 
	}

    public void setControlador(Controlador controlador){
        if (controlador == null) {
            throw new NullPointerException("Controlador nulo.");
        }
        this.controlador=controlador;
    }
    public abstract void comenzar() throws Exception;
    public abstract void terminar();
    

}