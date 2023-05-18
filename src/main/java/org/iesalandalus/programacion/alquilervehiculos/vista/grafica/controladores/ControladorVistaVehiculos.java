package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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

public class ControladorVistaVehiculos implements Initializable {

    @FXML
    private Button botonAdd;

    @FXML
    private ChoiceBox<String> botonSeleccionarBuscar;

    @FXML
    private Button botonVolver;

    @FXML
    private Button botonEdit;

    @FXML
    private Button botonDelete;

    @FXML
    private TextField campoBuscar;

    @FXML
    private TableColumn<Vehiculo, String> colTipoVehiculo;

    @FXML
    private TableColumn<Vehiculo, String> colMarca;

    @FXML
    private TableColumn<Vehiculo, String> colModelo;

    @FXML
    private TableColumn<Vehiculo, String> colMatricula;

    @FXML
    private TableColumn<Vehiculo, Integer> colCilindrada;

    @FXML
    private TableColumn<Vehiculo, Integer> colNumPlazas;

    @FXML
    private TableColumn<Vehiculo, Integer> colNumPma;

    @FXML
    private TableView<Vehiculo> tablaVehiculos;

    private ObservableList<Vehiculo> listaVehiculos;

    private ObservableList<Vehiculo> listaVehiculosVisible;

    protected Vehiculo registro;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        listaVehiculos = FXCollections.observableArrayList();
        listaVehiculos.setAll(Controlador.getInstancia().getVehiculos());

        listaVehiculosVisible = FXCollections.observableArrayList();
        listaVehiculosVisible.setAll(Controlador.getInstancia().getVehiculos());

        this.colMarca.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("marca"));
        this.colModelo.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("modelo"));
        this.colMatricula.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("matricula"));
        this.colCilindrada.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("cilindrada"));
        this.colNumPlazas.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("plazas"));
        this.colNumPma.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("pma"));
        this.colTipoVehiculo.setCellValueFactory(cellData -> {
            Vehiculo vehiculo = cellData.getValue();
            if (vehiculo instanceof Autobus) {
                return new SimpleStringProperty("Autobús");
            } else if (vehiculo instanceof Furgoneta) {
                return new SimpleStringProperty("Furgoneta");
            } else if (vehiculo instanceof Turismo) {
                return new SimpleStringProperty("Turismo");
            } else {
                return new SimpleStringProperty("");
            }
        });

        this.tablaVehiculos.setItems(listaVehiculosVisible);
        this.tablaVehiculos.refresh();

        SortedList<Vehiculo> sortedList = new SortedList<>(tablaVehiculos.getItems());
        tablaVehiculos.setItems(sortedList);
        sortedList.comparatorProperty().bind(tablaVehiculos.comparatorProperty());

        ObservableList<String> opcionesBuscar = FXCollections.observableArrayList("Marca", "Modelo", "Matricula", "Cilindrada", "Plazas", "PMA");
        botonSeleccionarBuscar.setItems(opcionesBuscar);
        botonSeleccionarBuscar.setValue("Marca"); // Valor por defecto
    }

    @FXML
    void addVehiculo(ActionEvent event) {

        try {

            // Cargo la vista

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/FXML/VistaAddVehiculos.fxml"));

            Parent root = loader.load();

            ControladorAddVehiculos controlador = loader.getController();
            controlador.initAtributtes(listaVehiculos);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Añadir Vehículo");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Leer vehículo creado:

            Vehiculo vehiculo = controlador.getVehiculo();

            if (vehiculo != null) {

                listaVehiculos.add(vehiculo);

                if (vehiculo.getModelo().toLowerCase().contains(this.campoBuscar.getText().toLowerCase())) {

                    this.listaVehiculosVisible.add(vehiculo);

                    try {

                        Controlador.getInstancia().insertar(vehiculo);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

                this.tablaVehiculos.refresh();
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

        /*String filtroNombre = this.campoBuscar.getText();

        if (filtroNombre.isEmpty()) {

            this.tablaVehiculos.setItems(listaVehiculos);

        } else {

            this.listaVehiculosVisible.clear();

            for (Vehiculo vehiculo : this.listaVehiculos) {

                if (vehiculo.getModelo().toLowerCase().contains(filtroNombre.toLowerCase())) {

                    this.listaVehiculosVisible.add(vehiculo);
                }
            }

            this.tablaVehiculos.setItems(listaVehiculosVisible);
        }*/
        buscar();
    }

    public void buscar(){
        String filtro = campoBuscar.getText().toLowerCase();
        String opcionBuscar = botonSeleccionarBuscar.getValue().toString().toLowerCase();

        listaVehiculosVisible.clear();

        for (Vehiculo vehiculo : listaVehiculos) {
            if (vehiculo instanceof Turismo) {
                Turismo turismo = (Turismo) vehiculo;
                if (opcionBuscar.equals("marca") && vehiculo.getMarca().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("modelo") && vehiculo.getModelo().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("matrícula") && vehiculo.getMatricula().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("cilindrada") && String.valueOf(turismo.getCilindrada()).contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                }
            } else if (vehiculo instanceof Autobus) {
                Autobus autobus = (Autobus) vehiculo;
                if (opcionBuscar.equals("marca") && autobus.getMarca().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("modelo") && autobus.getModelo().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("matricula") && autobus.getMatricula().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("plazas") && Integer.toString(autobus.getPlazas()).contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                }
            } else if (vehiculo instanceof Furgoneta) {
                Furgoneta furgoneta = (Furgoneta) vehiculo;
                if (opcionBuscar.equals("marca") && furgoneta.getMarca().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("modelo") && furgoneta.getModelo().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("matrícula") && furgoneta.getMatricula().toLowerCase().contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("plazas") && Integer.toString(furgoneta.getPlazas()).contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                } else if (opcionBuscar.equals("pma") && Integer.toString(furgoneta.getPma()).contains(filtro)) {
                    listaVehiculosVisible.add(vehiculo);
                }
            }
        }
        
        tablaVehiculos.refresh();
    }
    @FXML
    void ordenar(ActionEvent event) {

        /*
         * SortedList<Vehiculo> sortedList = new
         * SortedList<>(tablaVehiculos.getItems());
         * tablaVehiculos.setItems(sortedList);
         * sortedList.comparatorProperty().bind(tablaVehiculos.comparatorProperty());
         */
    }

    @FXML
    void deleteVehiculo(ActionEvent event) throws Exception {

        Vehiculo vehiculo = this.tablaVehiculos.getSelectionModel().getSelectedItem();

        if (vehiculo == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningún vehículo");
            alert.showAndWait();

        } else {
            boolean error = false;
            try {
                Controlador.getInstancia().borrar(vehiculo);

                this.listaVehiculos.remove(vehiculo);
                this.listaVehiculosVisible.remove(vehiculo);
            } catch (Exception e) {
                error = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("No se puede borrar un vehículo que tiene alquileres en curso.");
                alert.showAndWait();
            }
            if (!error) {
                this.tablaVehiculos.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("Vehículo eliminado");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void seleccionar(MouseEvent event) {

        this.registro = this.tablaVehiculos.getSelectionModel().getSelectedItem();
    }

    @FXML
    void volver(ActionEvent event) {

        Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        escenarioActual.close();
    }
}