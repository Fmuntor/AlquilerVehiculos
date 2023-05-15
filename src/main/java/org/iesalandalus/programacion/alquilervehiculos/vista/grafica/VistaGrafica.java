package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VistaGrafica extends Vista{

    private Stage escenario; 

	public VistaGrafica() {
		
	}

    @Override
	public void start(Stage escenario) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaPrincipal.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
		
        escenario.setTitle("Aplicación de Alquiler de Vehículos I.E.S. Al-Ándalus (Almería)");
		escenario.setScene(scene); 
		escenario.show(); 
	}

    @Override
	public void comenzar() throws Exception {
		
		launch();
		escenario = new Stage(); 
		start(escenario); 
		
	}

	@Override
	public void terminar() {}

    
}
