package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.util.ArrayList;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.IFuenteVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

    public VistaTexto(IFuenteVista fuenteVista) {

        setFuenteVista(fuenteVista);
        Accion.setVista(this);
    }

    public void comenzar() throws Exception{
        boolean comenzar = false;
        do {
            Consola.mostrarCabecera("BIENVENIDO!");
            Consola.mostrarMenu();
            System.out.print("Introduce una opción: ");
            Accion opcionElegida = Consola.elegirOpcion();
            System.out.println();
            Consola.mostrarCabecera(opcionElegida.toString());
            System.out.println();
            opcionElegida.ejecutar();
            System.out.println();

        } while (!comenzar);
    }

    @Override
    public void terminar() {
        System.out.println("HASTA PRONTO!");
    }

    protected void insertarCliente() throws Exception {
        try {
            Cliente clienteInsertado = Consola.leerCliente();
            controlador.insertar(clienteInsertado);
            System.out.println("¡El cliente " + clienteInsertado.toString() + " se ha creado con éxito!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void insertarVehiculo() throws Exception {

        try {
            controlador.insertar(Consola.leerVehiculo());

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    protected void insertarAlquiler() throws Exception {
        System.out.println("1.- INSERTAR POR CLIENTE");
        System.out.println("2.- INSERTAR POR TURISMO");

        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = controlador.buscar(Consola.leerClienteDNI());
                if (clienteAlquiler == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");

                }
                Vehiculo turismoAlquiler = controlador.buscar(Consola.leerTurismoMatricula());
                if (turismoAlquiler == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un vehiculo inexistente.");

                }
                Alquiler alquiler1 = new Alquiler(clienteAlquiler, turismoAlquiler,
                        Consola.leerFecha("Introduce la fecha de alquiler:"));
                controlador.insertar(alquiler1);
                System.out.println("¡Alquiler creado con éxito!: " + alquiler1);
                break;
            case "2":
                Vehiculo turismoAlquiler2 = controlador.buscar(Consola.leerTurismoMatricula());
                if (turismoAlquiler2 == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un vehiculo inexistente.");

                }
                Cliente clienteAlquiler2 = controlador.buscar(Consola.leerClienteDNI());
                if (clienteAlquiler2 == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");

                }

                Alquiler alquiler2 = new Alquiler(clienteAlquiler2, turismoAlquiler2,
                        Consola.leerFecha("Introduce la fecha de alquiler:"));
                controlador.insertar(alquiler2);
                System.out.println("¡Alquiler creado con éxito!: " + alquiler2);
        }
    }

    protected void buscarCliente() {
        Cliente clienteBuscado = Consola.leerClienteDNI();
        Cliente clienteEncontrado = controlador.buscar(clienteBuscado);

        if (clienteEncontrado == null) {
            System.out.println("El cliente introducido no existe.");

        }
        System.out.println("¡Cliente encontrado!: " + clienteEncontrado);
    }

    protected void buscarVehiculo() {
        Vehiculo turismoBuscado = Consola.leerTurismoMatricula();
        Vehiculo turismoEncontrado = controlador.buscar(turismoBuscado);

        if (turismoEncontrado == null) {
            System.out.println("El vehiculo introducido no existe.");
        }
        System.out.println("¡Vehiculo encontrado!: " + turismoEncontrado);

    }

    protected void buscarAlquiler() {

        System.out.println("1.- BUSCAR POR DNI");
        System.out.println("2.- BUSCAR POR MARICULA");
        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado1 = false;
                if (controlador.buscar(clienteAlquiler) == null) {
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                        System.out.println("¡Alquiler encontrado!\n" + alquiler);
                        encontrado1 = true;
                        break;
                    }
                }
                if (!encontrado1) {
                    System.out.println("No existe ningun alquiler con ese cliente.");
                    break;
                }
                break;
            case "2":
                Vehiculo turismoAlquiler = Consola.leerTurismoMatricula();
                boolean encontrado2 = false;
                if (controlador.buscar(turismoAlquiler) == null) {
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler.getMatricula())) {
                        System.out.println("¡Alquiler encontrado!\n" + alquiler);
                        encontrado2 = true;
                        break;
                    }
                }
                if (!encontrado2) {
                    System.out.println("No existe ningun alquiler con ese vehiculo.");
                    break;
                }
        }
    }

    protected void modificarCliente() throws Exception {
        System.out.println("Inserta el DNI del cliente a modificar.");
        Cliente clienteMod = Consola.leerClienteDNI();
        Cliente clienteParaMod = controlador.buscar(clienteMod);
        if (clienteParaMod == null) {
            throw new NullPointerException("El cliente no existe.");
        }
        System.out.println("¿Que quieres modificar del cliente?");
        System.out.println("1.- Modificar nombre.");
        System.out.println("2.- Modificar teléfono.");
        System.out.println("3.- Modificar nombre y teléfono.");
        switch (Consola.leerCadena("Introduce una opción:")) {
            case "1":
                controlador.modificar(clienteParaMod, Consola.leerNombre(), null);
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
            case "2":
                controlador.modificar(clienteParaMod, null, Consola.leerTelefono());
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
            case "3":
                controlador.modificar(clienteParaMod, Consola.leerNombre(), Consola.leerTelefono());
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
        }
    }

    protected void devolverAlquiler() throws Exception {
        System.out.println("1.- DEVOLVER POR DNI.");
        System.out.println("2.- DEVOLVER POR MATRÍCULA.");
        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado = false;
                if (controlador.buscar(clienteAlquiler) == null) {
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                        controlador.devolver(alquiler, Consola.leerFechaDevolucion());
                        encontrado = true;
                        System.out.println("Alquiler devuelto con éxito!: " + alquiler);
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("No existe ningun alquiler con ese cliente.");
                    break;
                }
                break;
            case "2":
                Vehiculo turismoAlquiler = Consola.leerTurismoMatricula();
                boolean encontrado2 = false;
                if (controlador.buscar(turismoAlquiler) == null) {
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler.getMatricula())) {
                        controlador.devolver(alquiler, Consola.leerFechaDevolucion());
                        encontrado2 = true;
                        System.out.println("Alquiler devuelto con éxito!: " + alquiler);
                        break;
                    }
                }
                if (!encontrado2) {
                    System.out.println("No existe ningun alquiler con ese vehiculo.");
                    break;
                }
        }
    }

    protected void borrarCliente() throws Exception {
        Cliente clienteBorrar = Consola.leerClienteDNI();
        if (controlador.buscar(clienteBorrar) == null) {
            System.out.println("No hay clientes con ese DNI.");
        }
        if (controlador.borrar(clienteBorrar) == true) {
            System.out.println("Cliente borrado con éxito.");

        }
    }

    protected void borrarVehiculo() throws Exception {
        Vehiculo turismoBorrar = Consola.leerTurismoMatricula();
        if (controlador.buscar(turismoBorrar) == null) {
            System.out.println("No hay turismos con esa matrícula.");

        }
        if (controlador.borrar(turismoBorrar) == true) {
            System.out.println("Turismo borrado con éxito.");
        }
    }

    protected void borrarAlquiler() throws Exception {
        System.out.println("1.- BORRAR POR DNI.");
        System.out.println("2.- BORRAR POR MATRÍCULA.");
        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado = false;
                if (controlador.buscar(clienteAlquiler) == null) {
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                        controlador.borrar(alquiler);
                        encontrado = true;
                        System.out.println("Alquiler eliminado con éxito!: " + alquiler);
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("No existe ningun alquiler con ese cliente.");
                    break;
                }
                break;
            case "2":
                Vehiculo turismoAlquiler = Consola.leerTurismoMatricula();
                boolean encontrado2 = false;
                if (controlador.buscar(turismoAlquiler) == null) {
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler.getMatricula())) {
                        controlador.borrar(alquiler);
                        encontrado2 = true;
                        System.out.println("Alquiler eliminado con éxito!: " + alquiler);
                        break;
                    }
                }
                if (!encontrado2) {
                    System.out.println("No existe ningun alquiler con ese vehiculo.");
                    break;
                }
            default:
                break;
        }
    }

    public void listarClientes() {
        ArrayList<Cliente> listaClientes = controlador.getClientes();
        listaClientes.sort((o1, o2) -> o1.compareTo(o2));
        for (Cliente clienteListado : listaClientes) {
            System.out.println(clienteListado);
        }
    }

    protected void listarVehiculos() {
        ArrayList<Vehiculo> listaVehiculos = controlador.getTurismos();
        listaVehiculos.sort((o1, o2) -> o1.compareTo(o2));
        for (Vehiculo turismoListado : listaVehiculos) {
            System.out.println(turismoListado);
        }
    }

    protected void listarAlquileres() {
        ArrayList<Alquiler> listaAlquileres = new ArrayList<Alquiler>();
        listaAlquileres = controlador.getAlquileres();
        for (Alquiler alquilerListado : listaAlquileres) {
            System.out.println(alquilerListado);
        }
    }

    protected void listarAlquileresCliente(){
        Cliente clienteAlquiler = Consola.leerClienteDNI();
        boolean encontrado1 = false;
        boolean encontrado2 = true;if(controlador.buscar(clienteAlquiler)==null){
            System.out.println("No hay clientes con ese DNI.");
        }
        // Comprobar si hay clientes con un alquiler
        for(
        Alquiler alquiler:controlador.getAlquileres()){
            if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                if (encontrado2) {
                    System.out.println("Alquileres del cliente introducido:");
                }
                encontrado2 = false;
                encontrado1 = true;
                System.out.println(alquiler);
            }
        }if(!encontrado1){
            System.out.println("No existe ningun alquiler con ese cliente.");
        
        }
    }
   
    protected void listarAlquileresVehiculo() {
        Vehiculo turismoAlquiler2 = Consola.leerTurismoMatricula();
        boolean encontrado3 = false;
        boolean encontrado4 = true;if(controlador.buscar(turismoAlquiler2)==null){
            System.out.println("No hay turismos con esa matrícula.");
        }
        // Comprobar si hay turismos con un alquiler
        for(
        Alquiler alquiler:controlador.getAlquileres()){
            if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler2.getMatricula())) {
                if (encontrado4) {
                    System.out.println("Alquileres del vehiculo introducido:");
                }
                encontrado4 = false;
                encontrado3 = true;
                System.out.println(alquiler);
            }
        }if(!encontrado3){
            System.out.println("No existe ningun alquiler con ese vehiculo.");
            
        }

    }
}