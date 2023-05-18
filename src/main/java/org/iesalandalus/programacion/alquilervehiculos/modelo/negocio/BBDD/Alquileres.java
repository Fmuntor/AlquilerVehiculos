package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.BBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import java.util.Iterator;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {
    private static Alquileres instancia;
    private static ArrayList<Alquiler> coleccionAlquileres;

    static Alquileres getInstancia() {
        if (instancia == null) {

            instancia = new Alquileres();
        }

        return instancia;
    }

    @Override
    public void comenzar() {

        coleccionAlquileres = new ArrayList<>();
        instancia = new Alquileres();

        try {
            leerBD();

        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void leerBD() throws Exception {
        String sql = "SELECT * FROM Alquileres";
        java.sql.Statement statement = Controlador.getConexion().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        coleccionAlquileres.removeAll(coleccionAlquileres);
        while (resultSet.next()) {

            Cliente cliente = Clientes.getInstancia()
                    .buscar(Cliente.getClienteConDni(resultSet.getString("DNI_cliente")));
            Vehiculo vehiculo = Vehiculos.getInstancia()
                    .buscar(Vehiculo.getVehiculoConMatricula(resultSet.getString("matricula_vehiculo")));

            java.sql.Date fechaAlqSql = resultSet.getDate("fecha_alquiler");
            LocalDate fechaAlq = fechaAlqSql.toLocalDate();

            Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlq);
            coleccionAlquileres.add(alquiler);

            java.sql.Date fechaDevSql = resultSet.getDate("fecha_devolucion");
            if (fechaDevSql != null) {
                LocalDate fechaDev = fechaDevSql.toLocalDate();
                alquiler.devolver(fechaDev);
            }
        }
    }

    @Override
    public void terminar() {

    }

    @Override
    public List<Alquiler> get() {
        return coleccionAlquileres;
    }

    @Override
    public ArrayList<Alquiler> get(Cliente cliente) {

        ArrayList<Alquiler> alquileresCliente = new ArrayList<>();

        Collections.sort(alquileresCliente, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
            }
        });

        Collections.sort(alquileresCliente, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
            }
        });

        Collections.sort(alquileresCliente, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
            }
        });

        Iterator<Alquiler> alquilerIterador = coleccionAlquileres.iterator();

        while (alquilerIterador.hasNext()) {

            Alquiler clienteAlquiler = alquilerIterador.next();
            if (clienteAlquiler.getCliente().equals(cliente)) {

                alquileresCliente.add(clienteAlquiler);
            }
        }

        return alquileresCliente;
    }

    @Override
    public ArrayList<Alquiler> get(Vehiculo vehiculo) {

        ArrayList<Alquiler> alquileresVehiculo = new ArrayList<>();

        Collections.sort(alquileresVehiculo, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
            }
        });

        Collections.sort(alquileresVehiculo, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
            }
        });

        Collections.sort(alquileresVehiculo, new Comparator<Alquiler>() {

            public int compare(Alquiler alquiler1, Alquiler alquiler2) {

                return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
            }
        });

        Iterator<Alquiler> alquilerIterador = coleccionAlquileres.iterator();

        while (alquilerIterador.hasNext()) {

            Alquiler vehiculoAlquiler = alquilerIterador.next();

            if (vehiculoAlquiler.getVehiculo().equals(vehiculo)) {
                alquileresVehiculo.add(vehiculoAlquiler);
            }
        }

        return alquileresVehiculo;
    }

    @Override
    public void insertar(Alquiler alquiler) throws Exception {
        String sql = "INSERT INTO Alquileres (DNI_cliente, matricula_vehiculo, fecha_alquiler, fecha_devolucion, precio) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        // Establecer valores de los parámetros de la sentencia SQL
        statement.setString(1, alquiler.getCliente().getDni());
        statement.setString(2, alquiler.getVehiculo().getMatricula());
        statement.setDate(3, java.sql.Date.valueOf(alquiler.getFechaAlquiler()));
        if (alquiler.getFechaDevolucion() == null) {
            statement.setDate(4, null);
            statement.setDouble(5, 0);
        } else {
            statement.setDate(4, java.sql.Date.valueOf(alquiler.getFechaDevolucion()));
            statement.setDouble(5, alquiler.getPrecio());
        }

        // Ejecutar sentencia SQL de inserción
        int filasInsertadas = statement.executeUpdate();
        System.out.println("Filas insertadas: " + filasInsertadas);
        leerBD();
    }

    @Override
    public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {

        Alquiler alquiler = getAlquilerAbierto(vehiculo);
        alquiler.devolver(fechaDevolucion);

        String sql = "UPDATE Alquileres SET fecha_devolucion = ? WHERE DNI_cliente = ? AND matricula_vehiculo = ?";
        PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

        statement.setDate(1, java.sql.Date.valueOf(fechaDevolucion));
        statement.setString(2, alquiler.getCliente().getDni());
        statement.setString(3, vehiculo.getMatricula());

        int filasActualizadas = statement.executeUpdate();
        System.out.println("Filas actualizadas: " + filasActualizadas);
        leerBD();
    }

    @Override
    public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {

        Alquiler alquiler = getAlquilerAbierto(cliente);

        alquiler.devolver(fechaDevolucion);
    }

    private Alquiler getAlquilerAbierto(Cliente cliente) {

        Vehiculo vehiculo = new Turismo("Seat", "León", "1234BCD", 1000);
        LocalDate fechaAlquiler = LocalDate.of(1996, 3, 24);

        Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler);
        Alquiler alquiler = null;

        if (buscar(alquilerInicial) == null) {

            throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
        }

        else {

            alquiler = buscar(alquilerInicial);
        }
        return alquiler;
    }

    private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {

        Cliente cliente = new Cliente("Nombre", "45241097M", "900900900");
        LocalDate fechaAlquiler = LocalDate.of(1996, 3, 24);

        Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler);
        Alquiler alquiler = null;

        if (buscar(alquilerInicial) == null) {

            throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
        }

        else

            alquiler = buscar(alquilerInicial);

        return alquiler;
    }

    @Override
    public Alquiler buscar(Alquiler alquiler) {
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
        }
        try {
            String sql = "SELECT * FROM Alquileres WHERE DNI=? AND MATRICULA=?";
            PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

            statement.setString(1, alquiler.getCliente().getDni());
            statement.setString(2, alquiler.getVehiculo().getMatricula());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                java.sql.Date fechaAlqSql = resultSet.getDate("fecha_alquiler");
                LocalDate fechaAlq = fechaAlqSql.toLocalDate();
                Alquiler alquilerEncontrado = new Alquiler(alquiler.getCliente(), alquiler.getVehiculo(), fechaAlq);
                java.sql.Date fechaDevSql = resultSet.getDate("fecha_devolucion");
                if (fechaDevSql != null) {
                    LocalDate fechaDev = fechaDevSql.toLocalDate();
                    alquilerEncontrado.devolver(fechaDev);
                }
                return alquilerEncontrado;
            } else {
                return null;
            }

        } catch (SQLException | NullPointerException | OperationNotSupportedException e) {
            // Aquí puedes agregar el código para manejar la excepción o mostrar un mensaje
            // de error
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void borrar(Alquiler alquiler) throws Exception {
        if (buscar(alquiler) != null) {
            String sql = "DELETE FROM Alquileres WHERE DNI_cliente=? AND matricula=?";
            PreparedStatement statement = Controlador.getConexion().prepareStatement(sql);

            statement.setString(1, alquiler.getCliente().getDni());
            statement.setString(2, alquiler.getVehiculo().getMatricula());

            int filasBorradas = statement.executeUpdate();
            System.out.println("Filas borradas: " + filasBorradas);
            leerBD();

        } else {
            throw new OperationNotSupportedException("No se puede borrar un alquiler inexistente");
        }

    }
}
