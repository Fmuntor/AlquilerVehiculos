package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores; 

import java.net.URL;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAddVehiculos extends ControladorVistaVehiculos {

	@FXML
    private TextField campoMarca; 
	
    @FXML
    private TextField campoModelo; 
    
    @FXML
    private TextField campoMatricula; 
    
    @FXML
    private TextField campoCilindrada; 
    
    @FXML
    private TextField campoNumPlazas; 
    
    @FXML
    private TextField campoPma; 
    
    @FXML
    private ChoiceBox<String> seleccionarTipo;

    protected Vehiculo vehiculo;
    
    protected Turismo turismo;
    
    protected Furgoneta furgoneta;
    
    protected Autobus autobus;
    
    protected ObservableList<Vehiculo> listaVehiculos;
    
    @FXML
    private Button buttonGuardar;
    
    private String [] vehiculosTipos = {"Turismo", "Furgoneta", "Autobus"};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// Cargar la lista de valores en el ChoiceBox
    	
    	this.campoCilindrada.setDisable(true);
        this.campoNumPlazas.setDisable(true);
        this.campoPma.setDisable(true);
        
    	this.seleccionarTipo.getItems().addAll(vehiculosTipos);
    	this.seleccionarTipo.setOnAction(this::seleccionarTipoVehiculo);
    }

    public void initAtributtes(ObservableList<Vehiculo> listaVehiculos) {
    	
        this.listaVehiculos = listaVehiculos;
    }

    public void initAtributtes(ObservableList<Vehiculo> listaVehiculos, Vehiculo vehiculo) {
    	
        this.listaVehiculos = listaVehiculos;
        this.vehiculo = vehiculo;
        
        // Se cargan los datos del vehículo: 
        
        if(vehiculo instanceof Turismo) {
        	
			this.campoMarca.setText(this.turismo.getMarca());
			this.campoModelo.setText(this.turismo.getModelo());
			this.campoMatricula.setText(this.turismo.getMarca());
			
			this.campoCilindrada.setText(Integer.toString(this.turismo.getCilindrada()));
			this.campoPma.setText(null);
			this.campoNumPlazas.setText(null);
		}
		
		if(vehiculo instanceof Autobus) {
			
			this.campoMarca.setText(this.autobus.getMarca());
			this.campoModelo.setText(this.autobus.getModelo());
			this.campoMatricula.setText(this.autobus.getMarca());
			
			this.campoCilindrada.setText(null); 
			this.campoPma.setText(null);
			this.campoNumPlazas.setText(Integer.toString(this.autobus.getPlazas()));
		}
		
		if(vehiculo instanceof Furgoneta) {

			this.campoMarca.setText(this.furgoneta.getMarca());
			this.campoModelo.setText(this.furgoneta.getModelo());
			this.campoMatricula.setText(this.furgoneta.getMarca());
			
			this.campoCilindrada.setText(null); 
			this.campoPma.setText(Integer.toString(this.furgoneta.getPma()));
			this.campoNumPlazas.setText(Integer.toString(this.furgoneta.getPlazas()));
		}
	}
    
    void seleccionarTipoVehiculo(ActionEvent event){
    	
    	String tipoVehiculo = this.seleccionarTipo.getValue(); 
    	
    	if(tipoVehiculo.equals("Turismo")) {
    		
    		this.campoCilindrada.setDisable(false);
    		this.campoPma.setDisable(true);
    		this.campoNumPlazas.setDisable(true);
    		
    	}else if(tipoVehiculo.equals("Furgoneta")) {
    		
    		this.campoCilindrada.setDisable(true);
    		this.campoPma.setDisable(false);
    		this.campoNumPlazas.setDisable(false);
    		
    	}else if(tipoVehiculo.equals("Autobus")) {
    		
    		this.campoCilindrada.setDisable(true);
    		this.campoPma.setDisable(true);
    		this.campoNumPlazas.setDisable(false);
    	}
    }

    public Vehiculo getVehiculo() {
    	
    	return this.vehiculo; 
    }

	@FXML
	void guardar(ActionEvent event) {

		try {

			// Se crea el vehículo:

			Vehiculo vehiculoNuevo = null;
			String tipo = null;

			// Se recuperan los datos del vehículo:

			if (this.seleccionarTipo.getValue().equals("Turismo")) {

				String marca = this.campoMarca.getText();
				String modelo = this.campoModelo.getText();
				String matricula = this.campoMatricula.getText();
				int cilindrada = Integer.parseInt(this.campoCilindrada.getText());

				vehiculoNuevo = new Turismo(marca, modelo, matricula,cilindrada);
				tipo="turismo";
			}

			if (this.seleccionarTipo.getValue().equals("Furgoneta")) {

				String marca = this.campoMarca.getText();
				String modelo = this.campoModelo.getText();
				String matricula = this.campoMatricula.getText();
				int numPlazas = Integer.parseInt(this.campoNumPlazas.getText());
				int pma = Integer.parseInt(this.campoPma.getText());

				vehiculoNuevo = new Furgoneta(marca, modelo,matricula, pma, numPlazas);
				tipo="furgoneta";
			}

			if (this.seleccionarTipo.getValue().equals("Autobus")) {

				String marca = this.campoMarca.getText();
				String modelo = this.campoModelo.getText();
				String matricula = this.campoMatricula.getText();
				int numPlazas = Integer.parseInt(this.campoNumPlazas.getText());

				vehiculoNuevo = new Autobus(marca, modelo, matricula, numPlazas);
				tipo="autobús";
			}

			// Comprobar si el vehículo ya existe:

			if (!listaVehiculos.contains(vehiculoNuevo)) {

				if (this.vehiculo == null) {

					this.vehiculo = vehiculoNuevo;

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Vehículo añadido");
					alert.setContentText("El "+tipo+" se ha añadido correctamente");
					alert.showAndWait();
				}

				// Se cierra la ventana:

				Stage stage = (Stage) this.buttonGuardar.getScene().getWindow();
				stage.close();

			} else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("El vehículo ya existe");
				alert.showAndWait();
			}

		} catch (Exception e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
