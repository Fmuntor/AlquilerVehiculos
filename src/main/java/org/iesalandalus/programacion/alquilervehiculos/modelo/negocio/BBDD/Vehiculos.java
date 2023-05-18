package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.BBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class Vehiculos implements IVehiculos {
    private static Vehiculos instancia;
    private static List<Vehiculo> coleccionVehiculos;

    static Vehiculos getInstancia() {

        if (instancia == null) {

            instancia = new Vehiculos(); // Crear la instancia sólo si aún no se ha creado
        }

        return instancia;
    }

    @Override
    public void comenzar() {

        coleccionVehiculos = new ArrayList<>();
        instancia = new Vehiculos();

        try {
            leerBD();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void leerBD() throws SQLException {
        String sql = "SELECT * FROM Vehiculos";
        java.sql.Statement statement = Controlador.getConexion().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        coleccionVehiculos.removeAll(coleccionVehiculos);
        while (resultSet.next()) {
            String matricula = resultSet.getString("matricula");
            String marca = resultSet.getString("marca");
            String modelo = resultSet.getString("modelo");
            int cilindrada = resultSet.getInt("cilindrada");
            int plazas = resultSet.getInt("num_plazas");
            int PMA = resultSet.getInt("PMA");

            if (cilindrada != 0) {
                Vehiculo turismo = new Turismo(marca, modelo, matricula, cilindrada);
                coleccionVehiculos.add(turismo);
            } else {
                if (PMA != 0) {
                    Vehiculo autobus = new Autobus(marca, modelo, matricula, plazas);
                    coleccionVehiculos.add(autobus);
                } else {
                    Vehiculo furgoneta = new Furgoneta(marca, modelo, matricula, PMA, plazas);
                    coleccionVehiculos.add(furgoneta);
                }
            }
        }
    }

    @Override
    public void terminar() {

    }

    /*
     * private void escribirBD(List<Vehiculo> vehiculos) throws Exception {
     * for (Vehiculo vehiculo : vehiculos) {
     * insertar(vehiculo);
     * }
     * }
     */

    @Override
    public List<Vehiculo> get() {
        try {
            leerBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coleccionVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) {

        String sql = "INSERT INTO Vehiculos (matricula, marca, modelo, cilindrada, num_plazas, PMA) VALUES (?, ?, ?, ?, ? ,?)";

        try {
            PreparedStatement preparedStatement = Controlador.getConexion().prepareStatement(sql);
            preparedStatement.setString(1, vehiculo.getMatricula());
            preparedStatement.setString(2, vehiculo.getMarca());
            preparedStatement.setString(3, vehiculo.getModelo());
            if (vehiculo instanceof Turismo) {

                Turismo turismo = (Turismo) vehiculo;
                preparedStatement.setInt(4, turismo.getCilindrada());
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, 0);
            } else if (vehiculo instanceof Autobus) {
                Autobus autobus = (Autobus) vehiculo;
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, autobus.getPlazas());
                preparedStatement.setInt(6, 0);
            } else {
                Furgoneta furgoneta = (Furgoneta) vehiculo;
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, furgoneta.getPlazas());
                preparedStatement.setInt(6, furgoneta.getPma());
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();
            leerBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Actualizar contenido de la BD en coleccionVehiculos

    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) throws SQLException {
        /*String sql = "SELECT * FROM Vehiculos WHERE matricula=?";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        statement.setString(1, vehiculo.getMatricula());

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String matricula = resultSet.getString("matricula");
            String marca = resultSet.getString("marca");
            String modelo = resultSet.getString("modelo");
            int cilindrada = resultSet.getInt("cilindrada");
            int plazas = resultSet.getInt("num_plazas");
            int PMA = resultSet.getInt("PMA");

            if (cilindrada != 0) {
                Vehiculo turismo = new Turismo(marca, modelo, matricula, cilindrada);
                return turismo;
            } else {
                if (PMA != 0) {
                    Vehiculo furgoneta = new Furgoneta(marca, modelo, matricula, PMA, plazas);
                    return furgoneta;
                } else {
                    Vehiculo autobus = new Autobus(marca, modelo, matricula, plazas);
                    return autobus;
                }
            }
        } else {
            return null;
        }*/
        if(vehiculo==null){
            throw new NullPointerException("ERROR: No se puede buscar un turismo nulo.");
        }
		if(coleccionVehiculos==null||coleccionVehiculos.size()==0){
			return null; 
		}


        for(Vehiculo turismo2 : coleccionVehiculos){
            if(turismo2.getMatricula().equals(vehiculo.getMatricula())){
                return turismo2;
            }
        }return null;

    }

    @Override
    public void borrar(Vehiculo vehiculo) throws Exception {
        String sql = "DELETE FROM Vehiculos WHERE matricula=?";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        statement.setString(1, vehiculo.getMatricula());

        int filasBorradas = statement.executeUpdate();
        System.out.println("Filas borradas: " + filasBorradas);
        leerBD();
    }

}
