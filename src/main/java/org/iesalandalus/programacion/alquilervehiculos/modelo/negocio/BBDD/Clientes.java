package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.BBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {
    private static Clientes instancia;
    private static List<Cliente> coleccionClientes;

    static Clientes getInstancia() {

        if (instancia == null) {

            instancia = new Clientes(); // Crear la instancia sólo si aún no se ha creado
        }

        return instancia;
    }

    @Override
    public void comenzar() {

        coleccionClientes = new ArrayList<>();
        instancia = new Clientes();

        try {
            leerBD();
           
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void leerBD() throws SQLException {
        String sql = "SELECT * FROM Clientes";
        java.sql.Statement statement = Controlador.getConexion().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        coleccionClientes.removeAll(coleccionClientes);
        while (resultSet.next()) {
            String DNI = resultSet.getString("DNI");
            String nombre = resultSet.getString("nombre");
            String telefono = resultSet.getString("telefono");
            Cliente cliente = new Cliente(nombre, DNI, telefono);
            coleccionClientes.add(cliente);
        }
    }

    @Override
    public void terminar() {
        
    }

    /*private void escribirBD(List<Cliente> clientes) throws SQLException {
        String sql = "INSERT INTO Clientes (nombre, dni, telefono) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = Controlador.getConexion().prepareStatement(sql);
        for (Cliente cliente : clientes) {
            preparedStatement.setString(1, cliente.getDni());
            preparedStatement.setString(2, cliente.getNombre());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
    }*/

    @Override
    public List<Cliente> get() {
        return coleccionClientes;
    }

    @Override
    public void insertar(Cliente cliente) throws SQLException {

        // Crear sentencia SQL de inserción
        String sql = "INSERT INTO Clientes (nombre, DNI, telefono) VALUES (?, ?, ?)";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        // Establecer valores de los parámetros de la sentencia SQL
        statement.setString(1, cliente.getNombre());
        statement.setString(2, cliente.getDni());
        statement.setString(3, cliente.getTelefono());

        // Ejecutar sentencia SQL de inserción
        int filasInsertadas = statement.executeUpdate();
        System.out.println("Filas insertadas: " + filasInsertadas);
        leerBD();
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws Exception {
        if (buscar(cliente) == null) {
            throw new Exception("Cliente no encontrado.");
        } else {
            String updateSql = "UPDATE Clientes SET nombre=?, telefono=? WHERE DNI=?";
            PreparedStatement updateStatement = Controlador.getConexion().prepareStatement(updateSql);
            updateStatement.setString(1, nombre);
            updateStatement.setString(2, telefono);
            updateStatement.setString(3, cliente.getDni());
    
            int filasActualizadas = updateStatement.executeUpdate();
    
            System.out.println("Filas actualizadas: " + filasActualizadas);
            leerBD();
        }
       
    }

    @Override
    public Cliente buscar(Cliente cliente) {
    try {
        String sql = "SELECT * FROM Clientes WHERE DNI=?";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        statement.setString(1, cliente.getDni());

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String dni = resultSet.getString("DNI");
            String nombre = resultSet.getString("nombre");
            String telefono = resultSet.getString("telefono");
            Cliente clienteEncontrado = new Cliente(nombre, dni, telefono);
            return clienteEncontrado;
        } else {
            return null;
        }
    } catch (SQLException e) {
        // Aquí puedes agregar el código para manejar la excepción o mostrar un mensaje de error
        e.printStackTrace();
        return null;
    }
}


    

    @Override
    public void borrar(Cliente cliente) throws Exception {
        String sql = "DELETE FROM Clientes WHERE DNI=?";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        statement.setString(1, cliente.getDni());

        int filasBorradas = statement.executeUpdate();
        System.out.println("Filas borradas: " + filasBorradas);
        leerBD();
    }

}
