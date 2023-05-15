package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class ControladorVistaPrincipal extends VistaGrafica {

	@FXML
	private Button botonSalir;

	@FXML
	private Button botonGestionarClientes;

	@FXML
	private Button botonGestionarVehiculos;

	@FXML
	private Button botonGestionarAlquileres;

	@FXML
	void gestionarClientes(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaClientes.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			Stage escenario = new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);

			escenario.setTitle("Gestión de Clientes");

			// Establecemos la escena

			escenario.setScene(escena);
			escenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void gestionarVehiculos(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaVehiculos.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			// ControllerVistaVehiculos controlador = fxmlLoader.getController();

			// Creamos el escenario

			Stage nuevoEscenario = new Stage();
			nuevoEscenario.initModality(Modality.APPLICATION_MODAL);

			nuevoEscenario.setTitle("Gestión de Vehículos");

			// Establecemos la escena

			nuevoEscenario.setScene(escena);
			nuevoEscenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void gestionarAlquileres(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaAlquileres.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			// ControllerVistaAlquileres controlador = fxmlLoader.getController();

			// Creamos el escenario

			Stage nuevoEscenario = new Stage();
			nuevoEscenario.initModality(Modality.APPLICATION_MODAL);

			nuevoEscenario.setTitle("Gestión de Alquileres");

			// Establecemos la escena

			nuevoEscenario.setScene(escena);
			nuevoEscenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void terminar() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("¡Hasta pronto!");

		alert.showAndWait();
		Controlador.getInstancia().terminar();
		
		/* Alert alert = new Alert(Alert.AlertType.INFORMATION);
		
		
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("La aplicación de alquiler de vehículos va a finalizar, hasta pronto...");
		alert.showAndWait();

		Controlador.getInstancia().terminar(); */
	}
}
