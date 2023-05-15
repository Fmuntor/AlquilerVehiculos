package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

import javafx.stage.Stage;

public class VistaTexto extends Vista {

    @Override
	public void start(Stage arg0) throws Exception {
		
	}

    public VistaTexto() {
        
        Accion.setVista(this);
    }

    public void comenzar() throws Exception{
        boolean comenzar = false;
        do {
            Consola.mostrarCabecera("BIENVENIDO!");
            Consola.mostrarMenu();
            System.out.print("Introduce una opción: ");
            Accion opcionElegida = Consola.elegirAccion();
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
        Controlador.getInstancia().terminar();
    }

    protected void insertarCliente() throws Exception {
        try {
            Cliente clienteInsertado = Consola.leerCliente();
            Controlador.getInstancia().insertar(clienteInsertado);
            System.out.println("¡El cliente " + clienteInsertado.toString() + " se ha creado con éxito!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void insertarVehiculo() throws Exception {

        try {
            Controlador.getInstancia().insertar(Consola.leerVehiculo());

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    protected void insertarAlquiler() throws Exception {
        System.out.println("1.- INSERTAR POR CLIENTE");
        System.out.println("2.- INSERTAR POR TURISMO");

        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Controlador.getInstancia().buscar(Consola.leerClienteDNI());
                if (clienteAlquiler == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");

                }
                Vehiculo turismoAlquiler = Controlador.getInstancia().buscar(Consola.leerTurismoMatricula());
                if (turismoAlquiler == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un vehiculo inexistente.");

                }
                Alquiler alquiler1 = new Alquiler(clienteAlquiler, turismoAlquiler,
                        Consola.leerFecha("Introduce la fecha de alquiler:"));
                Controlador.getInstancia().insertar(alquiler1);
                System.out.println("¡Alquiler creado con éxito!: " + alquiler1);
                break;
            case "2":
                Vehiculo turismoAlquiler2 = Controlador.getInstancia().buscar(Consola.leerTurismoMatricula());
                if (turismoAlquiler2 == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un vehiculo inexistente.");

                }
                Cliente clienteAlquiler2 = Controlador.getInstancia().buscar(Consola.leerClienteDNI());
                if (clienteAlquiler2 == null) {
                    throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");

                }

                Alquiler alquiler2 = new Alquiler(clienteAlquiler2, turismoAlquiler2,
                        Consola.leerFecha("Introduce la fecha de alquiler:"));
                Controlador.getInstancia().insertar(alquiler2);
                System.out.println("¡Alquiler creado con éxito!: " + alquiler2);
        }
    }

    protected void buscarCliente() throws Exception {
        Cliente clienteBuscado = Consola.leerClienteDNI();
        Cliente clienteEncontrado = Controlador.getInstancia().buscar(clienteBuscado);

        if (clienteEncontrado == null) {
            System.out.println("El cliente introducido no existe.");

        }
        System.out.println("¡Cliente encontrado!: " + clienteEncontrado);
    }

    protected void buscarVehiculo() throws Exception {
        Vehiculo turismoBuscado = Consola.leerTurismoMatricula();
        Vehiculo turismoEncontrado = Controlador.getInstancia().buscar(turismoBuscado);

        if (turismoEncontrado == null) {
            System.out.println("El vehiculo introducido no existe.");
        }
        System.out.println("¡Vehiculo encontrado!: " + turismoEncontrado);

    }

    protected void buscarAlquiler() throws Exception {

        System.out.println("1.- BUSCAR POR DNI");
        System.out.println("2.- BUSCAR POR MARICULA");
        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado1 = false;
                if (Controlador.getInstancia().buscar(clienteAlquiler) == null) {
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
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
                if (Controlador.getInstancia().buscar(turismoAlquiler) == null) {
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
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
        Cliente clienteParaMod = Controlador.getInstancia().buscar(clienteMod);
        if (clienteParaMod == null) {
            throw new NullPointerException("El cliente no existe.");
        }
        System.out.println("¿Que quieres modificar del cliente?");
        System.out.println("1.- Modificar nombre.");
        System.out.println("2.- Modificar teléfono.");
        System.out.println("3.- Modificar nombre y teléfono.");
        switch (Consola.leerCadena("Introduce una opción:")) {
            case "1":
                Controlador.getInstancia().modificar(clienteParaMod, Consola.leerNombre(), null);
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
            case "2":
                Controlador.getInstancia().modificar(clienteParaMod, null, Consola.leerTelefono());
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
            case "3":
                Controlador.getInstancia().modificar(clienteParaMod, Consola.leerNombre(), Consola.leerTelefono());
                System.out.println("¡Cliente actualizado con éxito!");
                System.out.println(clienteParaMod);
                break;
        }
    }

    protected void devolverAlquilerCliente() throws Exception {
        Cliente clienteAlquiler = Consola.leerClienteDNI();
            boolean encontrado = false;
            if (Controlador.getInstancia().buscar(clienteAlquiler) == null) {
                System.out.println("No hay clientes con ese DNI.");
            }
            // Comprobar si hay clientes con un alquiler
            for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
                if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                    Controlador.getInstancia().devolver(clienteAlquiler, Consola.leerFechaDevolucion());
                    encontrado = true;
                    System.out.println("Alquiler devuelto con éxito!: " + alquiler);
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("No existe ningun alquiler con ese cliente.");
            }
    }

    protected void devolverAlquilerVehiculo() throws Exception {
        Vehiculo turismoAlquiler = Consola.leerTurismoMatricula();
            boolean encontrado2 = false;
            if (Controlador.getInstancia().buscar(turismoAlquiler) == null) {
                System.out.println("No hay turismos con esa matrícula.");
            }
            // Comprobar si hay clientes con un alquiler
            for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
                if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler.getMatricula())) {
                    Controlador.getInstancia().devolver(turismoAlquiler, Consola.leerFechaDevolucion());
                    encontrado2 = true;
                    System.out.println("Alquiler devuelto con éxito!: " + alquiler);
                    break;
                }
            }
            if (!encontrado2) {
                System.out.println("No existe ningun alquiler con ese vehiculo.");
            }
    }

    
    protected void borrarCliente() throws Exception {
        try {

			Controlador.getInstancia().borrar(Consola.leerClienteDNI());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
    }

    protected void borrarVehiculo() throws Exception {
        try {

			Controlador.getInstancia().borrar(Consola.leerVehiculoMatricula());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
    }

    protected void borrarAlquiler() throws Exception {
        System.out.println("1.- BORRAR POR DNI.");
        System.out.println("2.- BORRAR POR MATRÍCULA.");
        switch (Consola.leerCadena("Introduce la opción deseada: ")) {
            case "1":
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado = false;
                if (Controlador.getInstancia().buscar(clienteAlquiler) == null) {
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
                    if (alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())) {
                        Controlador.getInstancia().borrar(alquiler);
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
                if (Controlador.getInstancia().buscar(turismoAlquiler) == null) {
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : Controlador.getInstancia().getAlquileres()) {
                    if (alquiler.getVehiculo().getMatricula().equals(turismoAlquiler.getMatricula())) {
                        Controlador.getInstancia().borrar(alquiler);
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
        List<Cliente> listaClientes = Controlador.getInstancia().getClientes();
        listaClientes.sort((o1, o2) -> o1.compareTo(o2));
        for (Cliente clienteListado : listaClientes) {
            System.out.println(clienteListado);
        }
    }

    protected void listarVehiculos() {
        List<Vehiculo> listaVehiculos = Controlador.getInstancia().getVehiculos();
        listaVehiculos.sort((o1, o2) -> o1.compareTo(o2));
        for (Vehiculo turismoListado : listaVehiculos) {
            System.out.println(turismoListado);
        }
    }

    protected void listarAlquileres() {
        List<Alquiler> listaAlquileres = new ArrayList<Alquiler>();
        listaAlquileres = Controlador.getInstancia().getAlquileres();
        for (Alquiler alquilerListado : listaAlquileres) {
            System.out.println(alquilerListado);
        }
    }

    protected void listarAlquileresCliente(){
        Cliente clienteAlquiler = Consola.leerClienteDNI();
        boolean encontrado1 = false;
        boolean encontrado2 = true;if(Controlador.getInstancia().buscar(clienteAlquiler)==null){
            System.out.println("No hay clientes con ese DNI.");
        }
        // Comprobar si hay clientes con un alquiler
        for(
        Alquiler alquiler:Controlador.getInstancia().getAlquileres()){
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
        boolean encontrado4 = true;if(Controlador.getInstancia().buscar(turismoAlquiler2)==null){
            System.out.println("No hay turismos con esa matrícula.");
        }
        // Comprobar si hay turismos con un alquiler
        for(
        Alquiler alquiler:Controlador.getInstancia().getAlquileres()){
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

    private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		
		Map<TipoVehiculo, Integer> mapaVehiculos = new EnumMap<>(TipoVehiculo.class);
		
		mapaVehiculos.put(TipoVehiculo.AUTOBUS, 0);
		mapaVehiculos.put(TipoVehiculo.FURGONETA, 0);
		mapaVehiculos.put(TipoVehiculo.TURISMO, 0);
		
		return mapaVehiculos; 
	}

    public void mostrarEstadisticasMensualesTipoVehiculo() {
		
		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.MOSTRAR_ESTADISTICAS_MENSUALES);
		
		    Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();

            Month mes = Consola.leerMes().getMonth();

            int contadorTurismos = 0;
            int contadorAutobus = 0;
            int contadorFurgoneta = 0;
		  
		    for(Alquiler alquiler: Controlador.getInstancia().getAlquileres()) {
		    	
		    	TipoVehiculo tipoVehiculo = null; 
		    	
		    	if((alquiler.getVehiculo() instanceof Turismo) && (alquiler.getFechaAlquiler().getMonth().equals(mes))) {
                    contadorTurismos++;
		    		tipoVehiculo = TipoVehiculo.TURISMO;
		    		
		    		estadisticas.put(tipoVehiculo, contadorTurismos); 
		    		
		    	}else if((alquiler.getVehiculo() instanceof Autobus) && (alquiler.getFechaAlquiler().getMonth().equals(mes))) {
		    		contadorAutobus++;
		    		tipoVehiculo = TipoVehiculo.AUTOBUS; 
		    		
		    		estadisticas.put(tipoVehiculo, contadorAutobus); 
		    		
		    	}else if((alquiler.getVehiculo() instanceof Furgoneta) && (alquiler.getFechaAlquiler().getMonth().equals(mes))) {
		    		contadorFurgoneta++;
		    		tipoVehiculo = TipoVehiculo.FURGONETA; 
		    		
		    		estadisticas.put(tipoVehiculo, contadorFurgoneta); 
		    	}
		    }
            if(contadorAutobus == 0 && contadorFurgoneta == 0 && contadorTurismos==0){
                System.out.println("\nNo se han encontrado alquileres en ese mes:");
            }
            System.out.println(estadisticas); 
	}
}