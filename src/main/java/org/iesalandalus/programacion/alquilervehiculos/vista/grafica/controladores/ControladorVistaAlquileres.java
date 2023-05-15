package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVistaAlquileres implements Initializable {

	@FXML
	private Button buttonAdd;

	@FXML
	private ChoiceBox<String> botonSeleccionarBuscar;

	@FXML
	private Button buttonDevolver;

	@FXML
	private Button buttonBack;

	@FXML
	private TextField campoBuscar;

	@FXML
	private TableColumn<Alquiler, Cliente> colCliente;

	@FXML
	private TableColumn<Alquiler, Vehiculo> colVehiculo;

	@FXML
	private TableColumn<Alquiler, String> colTipoVehiculo;

	@FXML
	private TableColumn<Alquiler, LocalDate> colFechaAlquiler;

	@FXML
	private TableColumn<Alquiler, LocalDate> colFechaDevolucion;

	@FXML
	private TableColumn<Alquiler, Double> colGetPrecio;

	@FXML
	protected TableView<Alquiler> tablaAlquileres;

	protected ObservableList<Alquiler> listaAlquileres;

	protected ObservableList<Alquiler> listaAlquileresVisible;

	protected Alquiler registro;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		listaAlquileres = FXCollections.observableArrayList();
		listaAlquileres.setAll(Controlador.getInstancia().getAlquileres());

		listaAlquileresVisible = FXCollections.observableArrayList();
		listaAlquileresVisible.setAll(Controlador.getInstancia().getAlquileres());

		this.colCliente.setCellValueFactory(new PropertyValueFactory<Alquiler, Cliente>("cliente"));
		this.colVehiculo.setCellValueFactory(new PropertyValueFactory<Alquiler, Vehiculo>("vehiculo"));
		this.colFechaAlquiler.setCellValueFactory(new PropertyValueFactory<Alquiler, LocalDate>("fechaAlquiler"));
		this.colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<Alquiler, LocalDate>("fechaDevolucion"));
		this.colGetPrecio
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
		this.colTipoVehiculo.setCellValueFactory(cellData -> {
			Vehiculo vehiculo = cellData.getValue().getVehiculo();
			if (vehiculo instanceof Autobus) {
				return new ReadOnlyStringWrapper("Autobús");
			} else if (vehiculo instanceof Furgoneta) {
				return new ReadOnlyStringWrapper("Furgoneta");
			} else if (vehiculo instanceof Turismo) {
				return new ReadOnlyStringWrapper("Turismo");
			} else {
				return new ReadOnlyStringWrapper("");
			}
		});

		this.tablaAlquileres.setItems(listaAlquileresVisible);
		this.tablaAlquileres.refresh();

		ObservableList<String> opcionesBuscar = FXCollections.observableArrayList("Cliente", "Vehículo");
		botonSeleccionarBuscar.setItems(opcionesBuscar);
		botonSeleccionarBuscar.setValue("Cliente"); // Valor por defecto

	}

	@FXML
	void addAlquiler(ActionEvent event) throws Exception {

		try {

			// Cargo la vista

			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaAddAlquileres.fxml"));

			// Cargo la ventana

			Parent root = loader.load();

			// Creamos una instancia del controlador de AddClientes:

			ControladorAddAlquileres controlador = loader.getController();
			controlador.initAtributtes(listaAlquileres);

			// Se crea Stage y Scene:

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("Añadir Alquiler");

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

			// Se recupera el alquiler :

			Alquiler alquiler = controlador.getAlquiler();

			if (alquiler != null) {

				listaAlquileres.add(alquiler);

				if (alquiler.getCliente().getNombre().toLowerCase()
						.contains(this.campoBuscar.getText().toLowerCase())) {

					this.listaAlquileresVisible.add(alquiler);

				}

				this.tablaAlquileres.refresh();
			}

		} catch (IOException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void buscar(KeyEvent event) {

		/*
		 * String filtroNombre = this.campoBuscar.getText();
		 * 
		 * if (filtroNombre.isEmpty()) {
		 * 
		 * this.tablaAlquileres.setItems(listaAlquileres);
		 * 
		 * } else {
		 * 
		 * this.listaAlquileresVisible.clear();
		 * 
		 * for (Alquiler alquiler : this.listaAlquileres) {
		 * 
		 * if (alquiler.getCliente().getNombre().toLowerCase().contains(filtroNombre.
		 * toLowerCase())) {
		 * 
		 * this.listaAlquileresVisible.add(alquiler);
		 * }
		 * }
		 * 
		 * this.tablaAlquileres.setItems(listaAlquileresVisible);
		 * }
		 */
		String filtro = campoBuscar.getText().toLowerCase();
		String opcionBuscar = botonSeleccionarBuscar.getValue().toString().toLowerCase();

		listaAlquileresVisible.clear();

		/*
		 * if (opcionBuscar.equals("cliente") && alquiler.getCliente().equals(filtro)) {
		 * listaAlquileresVisible.add(alquiler);
		 * 
		 * } /*else if (opcionBuscar.equals("vehiculo") &&
		 * alquiler.getDni().toLowerCase().contains(filtro)) {
		 * listaAlquileresVisible.add(alquiler);
		 * } else if (opcionBuscar.equals("fechaAlquiler") &&
		 * alquiler.getFechaAlquiler().contains(filtro)) {
		 * listaAlquileresVisible.add(alquiler);
		 * }
		 */

		// Si se ha seleccionado Cliente, se comprueba si se ha introducido nombre, DNI
		// o telefono.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (Alquiler alquiler : listaAlquileres) {
			if (opcionBuscar.equals("cliente") &&
					(alquiler.getCliente().getDni().toLowerCase().contains(filtro) ||
							alquiler.getCliente().getNombre().toLowerCase().contains(filtro) ||
							alquiler.getCliente().getTelefono().toLowerCase().contains(filtro))) {
				listaAlquileresVisible.add(alquiler);
			}else if (alquiler.getVehiculo() instanceof Turismo) {
				Turismo turismo = (Turismo) alquiler.getVehiculo();
				if (opcionBuscar.equals("vehiculo") &&
						turismo.getMarca().toLowerCase().contains(filtro) ||
						turismo.getModelo().toLowerCase().contains(filtro) ||
						turismo.getMatricula().toLowerCase().contains(filtro) ||
						String.valueOf(turismo.getCilindrada()).contains(filtro)) {
					listaAlquileresVisible.add(alquiler);
				}
			} else if (alquiler.getVehiculo() instanceof Autobus) { 
				Autobus autobus = (Autobus) alquiler.getVehiculo();
				if (opcionBuscar.equals("vehiculo") &&
						autobus.getMarca().toLowerCase().contains(filtro) ||
						autobus.getModelo().toLowerCase().contains(filtro) ||
						autobus.getMatricula().toLowerCase().contains(filtro) ||
						String.valueOf(autobus.getPlazas()).contains(filtro)) {
					listaAlquileresVisible.add(alquiler);
				}
			} else if (alquiler.getVehiculo() instanceof Furgoneta) {
				Furgoneta furgoneta = (Furgoneta) alquiler.getVehiculo();
				if (opcionBuscar.equals("vehiculo") &&
						furgoneta.getMarca().toLowerCase().contains(filtro) ||
						furgoneta.getModelo().toLowerCase().contains(filtro) ||
						furgoneta.getMatricula().toLowerCase().contains(filtro) ||
						String.valueOf(furgoneta.getPlazas()).contains(filtro) ||
						String.valueOf(furgoneta.getPma()).contains(filtro)) {
					listaAlquileresVisible.add(alquiler);
				}
			}
		}
	}

	@FXML
	void devolver(ActionEvent event) {

		Alquiler alquiler = this.tablaAlquileres.getSelectionModel().getSelectedItem();

		if (alquiler == null) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se ha seleccionado ningún alquiler");
			alert.showAndWait();

		} else if (this.tablaAlquileres.getSelectionModel().getSelectedItem().getFechaDevolucion() != null) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se puede devolver un alquiler ya devuelto");
			alert.showAndWait();

		} else {

			try {

				// Cargo la vista

				FXMLLoader loader = new FXMLLoader(getClass().getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaDevolverAlquileres.fxml"));

				// Cargo la ventana

				Parent root = loader.load();

				// Creamos una instancia del controlador de AddClientes:

				ControladorDevolverAlquileres controlador = loader.getController();
				controlador.initAtributtes(listaAlquileres, alquiler);

				// Se crea Stage y Scene:

				Scene scene = new Scene(root);
				Stage stage = new Stage();

				stage.setTitle("Devolver Alquiler");

				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.showAndWait();

				Alquiler alquilerSeleccionado = controlador.getAlquiler();

				if (alquiler != null) {

					if (alquilerSeleccionado.getCliente().getNombre().toLowerCase()
							.contains(this.campoBuscar.getText().toLowerCase())) {

						try {

							Controlador.getInstancia().devolver(alquilerSeleccionado.getCliente(),
									alquiler.getFechaDevolucion());

						} catch (Exception e) {

							e.printStackTrace();
						}
					}

					this.tablaAlquileres.refresh();
				}

			} catch (IOException e) {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}

	@FXML
	void ordenar(ActionEvent event) {

		/*
		 * SortedList<Alquiler> sortedList = new
		 * SortedList<>(tablaAlquileres.getItems());
		 * tablaAlquileres.setItems(sortedList);
		 * sortedList.comparatorProperty().bind(tablaAlquileres.comparatorProperty());
		 */
	}

	@FXML
	void seleccionar(MouseEvent event) {

		this.registro = this.tablaAlquileres.getSelectionModel().getSelectedItem();
	}

	@FXML
	void volver(ActionEvent event) {

		Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
		escenarioActual.close();
	}
}
