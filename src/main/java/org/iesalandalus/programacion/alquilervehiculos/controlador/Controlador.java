package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVistas;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.Consola;

public class Controlador {
    private Modelo modelo;
    private Vista vista;
    private static Controlador instancia;
    static Connection conexion;

    /*
     * public Controlador(Vista vista, Modelo modelo){
     * if (modelo == null) {
     * throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
     * }
     * if (vista == null) {
     * throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
     * }
     * 
     * this.vista = vista;
     * this.modelo = modelo;
     * this.vista.setControlador(this);
     * }
     */
    private Controlador() throws Exception {

        FactoriaFuenteDatos fuenteDatos = null;
        FactoriaVistas vistas = null;

        System.out.println("");
        String cadena = Consola.leerCadena(
                "Selecciona modo de aplicacion:\n1.Memoria y vista gráfica.\n2.Memoria y vista por consola.\n3.Ficheros y vista gráfica.\n4.Ficheros y vista por consola.\n5.MySQL y vista gráfica.\n6.MySQL y vista por consola.\n");
        
        switch (cadena) {
            case "1":
                fuenteDatos = FactoriaFuenteDatos.MEMORIA;
                vistas = FactoriaVistas.GRAFICOS;
                break;

            case "2":
                fuenteDatos = FactoriaFuenteDatos.MEMORIA;
                vistas = FactoriaVistas.TEXTO;
                break;
            case "3":
                fuenteDatos = FactoriaFuenteDatos.FICHEROS;
                vistas = FactoriaVistas.GRAFICOS;
                break;

            case "4":
                fuenteDatos = FactoriaFuenteDatos.FICHEROS;
                vistas = FactoriaVistas.TEXTO;
                break;
            case "5":
                try {
                    configurarConexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fuenteDatos = FactoriaFuenteDatos.BBDD;
                vistas = FactoriaVistas.GRAFICOS;
                break;

            case "6":
                try {
                    configurarConexion();
                } catch (Exception e) {

                }
                fuenteDatos = FactoriaFuenteDatos.BBDD;
                vistas = FactoriaVistas.TEXTO;
                break;
        }
        // Vista vistaTexto = new VistaTexto();
        // Controlador controlador = new Controlador(vistaTexto, modeloCascada);
        Modelo modeloCascada = new ModeloCascada(fuenteDatos);
        this.modelo = modeloCascada;
        this.vista = vistas.crear();
    }

    private void configurarConexion() throws SQLException{
        try {
            
        } catch (Exception e) {
            
        }
        String cadena = Consola.leerCadena("¿Desea utilizar los valores actuales como configuración por defecto?(LocalHost:3306, BD: Programacion) (s/n)");
        String host;
        String puerto;
        String baseDatos;
        String usuario;
        String contraseña;

        switch (cadena) {
            case "s":
            host = "localhost";
            puerto = "3306";
            baseDatos = "Programacion";
            usuario = Consola.leerCadena("Introduzca el usuario (Default:root):");
    
            contraseña = Consola.leerCadena("Introduzca la contraseña (Default:1234):");
    
            String url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos;
            String user = usuario;
            String password = contraseña;
    
            conexion = DriverManager.getConnection(url, user, password);
                break;
            case "n":
            host = Consola.leerCadena("Introduzca el host:");
    
            puerto = Consola.leerCadena("Introduzca el puerto:");

            baseDatos = Consola.leerCadena("Introduzca el nombre de la Base de Datos:");
            usuario = Consola.leerCadena("Introduzca el usuario (Default:root):");
    
            contraseña = Consola.leerCadena("Introduzca la contraseña (Default:1234):");
    
            String url2 = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos;
            String user2 = usuario;
            String password2 = contraseña;
    
            conexion = DriverManager.getConnection(url2, user2, password2);
            default:
            System.out.println("Valor incorrecto, vuelve a introducirlo:");
            configurarConexion();
                break;
        }
    }

    public static Connection getConexion() {
        return conexion;
    }

    public static Controlador getInstancia() {
        try {
            if (instancia == null) {

                instancia = new Controlador();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return instancia;
    }

    public void comenzar() throws Exception {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void insertar(Cliente cliente) throws Exception {
        modelo.insertar(cliente);
    }

    public void insertar(Vehiculo turismo) throws Exception {
        modelo.insertar(turismo);
    }

    public void insertar(Alquiler alquiler) throws Exception {
        modelo.insertar(alquiler);
    }

    public Cliente buscar(Cliente cliente) throws Exception {
        return modelo.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo turismo) throws Exception {
        return modelo.buscar(turismo);
    }

    public Alquiler buscar(Alquiler alquiler) throws Exception {
        return modelo.buscar(alquiler);
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws Exception {
        modelo.modificar(cliente, nombre, telefono);
    }

    public void devolver(Cliente cliente, LocalDate FechaDevolucion) throws Exception {
        modelo.devolver(cliente, FechaDevolucion);
    }

    public void devolver(Vehiculo vehiculo, LocalDate FechaDevolucion) throws Exception {
        modelo.devolver(vehiculo, FechaDevolucion);
    }

    public void borrar(Cliente cliente) throws Exception {

        modelo.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws Exception {

        modelo.borrar(vehiculo);
    }

    public void borrar(Alquiler alquiler) throws Exception {

        modelo.borrar(alquiler);
    }

    public List<Cliente> getClientes() {

        return modelo.getListaClientes();
    }

    public List<Vehiculo> getVehiculos() {

        return modelo.getListaVehiculos();
    }

    public List<Alquiler> getAlquileres() {

        return modelo.getListaAlquileres();
    }

    public List<Alquiler> getAlquileres(Cliente cliente) {

        return modelo.getListaAlquileres(cliente);
    }

    public List<Alquiler> getAlquileres(Vehiculo vehiculo) {

        return modelo.getListaAlquileres(vehiculo);
    }
}
