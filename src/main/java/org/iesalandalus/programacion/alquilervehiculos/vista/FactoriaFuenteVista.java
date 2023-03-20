package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.vista.texto.FuenteVistaTexto;

public enum FactoriaFuenteVista {

	VISTA_TEXTO {

			@Override
			public IFuenteVista crear() {
				
				IFuenteVista fuenteVistasVistaTexto = new FuenteVistaTexto();  
				
				return fuenteVistasVistaTexto;
			}
		};
		
		public abstract IFuenteVista crear(); 
}

