package org.iesalandalus.programacion.alquilervehiculos.vista;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

public class Vista {
    private Controlador controlador;
    public void setControlador(Controlador controlador){
        if (controlador == null) {
            throw new NullPointerException("Controlador nulo.");
        }
        this.controlador=controlador;
    }

    public void comenzar(){
        Consola.mostrarCabecera("BIENVENIDO!");
        Consola.mostrarMenu();
        System.out.print("Introduce una opción: ");
        Opcion opcionElegida=Consola.elegirOpcion();

        while(opcionElegida!=Opcion.SALIR){
            System.out.println("");
            Consola.mostrarCabecera(opcionElegida.toString());
            try{
                ejecutar(opcionElegida);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("");
            Consola.mostrarMenu();
            System.out.print("Introduce una opción: ");
            opcionElegida=Consola.elegirOpcion();
        }
        terminar();
    }

    public void terminar(){
        System.out.println("HASTA PRONTO!");
    }

    private void ejecutar(Opcion opcion) throws OperationNotSupportedException{
        switch (opcion) {
            case SALIR:
                terminar();
                break;

            case INSERTAR_CLIENTE:
                Cliente clienteInsertado=Consola.leerCliente();
                controlador.insertar(clienteInsertado);
                System.out.println("¡El cliente "+clienteInsertado.toString()+" se ha creado con éxito!");
                break;

            case INSERTAR_TURISMO:
                Turismo turismoInsertado=Consola.leerTurismo();
                controlador.insertar(turismoInsertado);
                System.out.println("¡El turismo "+turismoInsertado.toString()+" se ha creado con éxito!");
                break;

            case INSERTAR_ALQUILER:
            System.out.println("1.- INSERTAR POR CLIENTE");
            System.out.println("2.- INSERTAR POR TURISMO");
            
            switch (Consola.leerCadena("Introduce la opción deseada: ")) {
                case "1":
                    Cliente clienteAlquiler = controlador.buscar(Consola.leerClienteDNI());
                    if(clienteAlquiler==null){
                        throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");
                        
                    }
                    Turismo turismoAlquiler = controlador.buscar(Consola.leerTurismoMatricula());
                    if(turismoAlquiler==null){
                        throw new NullPointerException("No se puede introducir un alquiler para un turismo inexistente.");
                        
                    }
                    Alquiler alquiler1 = new Alquiler(clienteAlquiler, turismoAlquiler, Consola.leerFecha("Introduce la fecha de alquiler:"));
                    controlador.insertar(alquiler1);
                    System.out.println("¡Alquiler creado con éxito!: "+alquiler1);
                    break;
                case "2":
                    Turismo turismoAlquiler2 = controlador.buscar(Consola.leerTurismoMatricula());
                    if(turismoAlquiler2==null){
                        throw new NullPointerException("No se puede introducir un alquiler para un turismo inexistente.");
                        
                    }
                    Cliente clienteAlquiler2 = controlador.buscar(Consola.leerClienteDNI());
                    if(clienteAlquiler2==null){
                        throw new NullPointerException("No se puede introducir un alquiler para un cliente inexistente.");
                        
                    }
                    
                    Alquiler alquiler2 = new Alquiler(clienteAlquiler2, turismoAlquiler2, Consola.leerFecha("Introduce la fecha de alquiler:"));
                    controlador.insertar(alquiler2);
                    System.out.println("¡Alquiler creado con éxito!: "+alquiler2);
                    break;
                default:
                    break;
            }break;
                
            case BUSCAR_CLIENTE:
                Cliente clienteBuscado = Consola.leerClienteDNI();
                Cliente clienteEncontrado=controlador.buscar(clienteBuscado);
                if(clienteEncontrado==null){
                    System.out.println("El cliente introducido no existe.");
                    break;
                }
                System.out.println("¡Cliente encontrado!: "+clienteEncontrado);
                break;

            case BUSCAR_TURISMO:
                Turismo turismoBuscado = Consola.leerTurismoMatricula();
                Turismo turismoEncontrado=controlador.buscar(turismoBuscado);
                if(turismoEncontrado==null){
                    System.out.println("El turismo introducido no existe.");
                    break;
                }
                System.out.println("¡Turismo encontrado!: "+turismoEncontrado);
                break;

            case BUSCAR_ALQUILER:
                System.out.println("1.- BUSCAR POR DNI");
                System.out.println("2.- BUSCAR POR MARICULA");
                switch (Consola.leerCadena("Introduce la opción deseada: ")) {
                    case "1":
                        Cliente clienteAlquiler = Consola.leerClienteDNI();
                        boolean encontrado1=false;
                        if(controlador.buscar(clienteAlquiler)==null){
                            System.out.println("No hay clientes con ese DNI.");
                        }
                        // Comprobar si hay clientes con un alquiler
                        for (Alquiler alquiler : controlador.getAlquileres()) {
                            if(alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())){
                                System.out.println("¡Alquiler encontrado!\n" +alquiler);
                                encontrado1=true;
                                break;
                            }
                        }
                        if(!encontrado1){
                            System.out.println("No existe ningun alquiler con ese cliente.");
                            break;
                        }
                        break;
                    case "2":
                        Turismo turismoAlquiler = Consola.leerTurismoMatricula();
                        boolean encontrado2=false;
                        if(controlador.buscar(turismoAlquiler)==null){
                            System.out.println("No hay turismos con esa matrícula.");
                        }
                        // Comprobar si hay clientes con un alquiler
                        for (Alquiler alquiler : controlador.getAlquileres()) {
                            if(alquiler.getTurismo().getMatricula().equals(turismoAlquiler.getMatricula())){
                                System.out.println("¡Alquiler encontrado!\n" +alquiler);
                                encontrado2=true;
                                break;
                            }
                        }
                        if(!encontrado2){
                            System.out.println("No existe ningun alquiler con ese turismo.");
                            break;
                        }
                    default:
                        break;
                }break;
            
            case MODIFICAR_CLIENTE:
                System.out.println("Inserta el DNI del cliente a modificar.");
                Cliente clienteMod = Consola.leerClienteDNI();
                Cliente clienteParaMod = controlador.buscar(clienteMod);
                if(clienteParaMod==null){
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
                    case"3":
                        controlador.modificar(clienteParaMod, Consola.leerNombre(), Consola.leerTelefono());
                        System.out.println("¡Cliente actualizado con éxito!");
                        System.out.println(clienteParaMod);
                        break;
                    default:
                        break;
                }break;

            case DEVOLVER_ALQUILER:
                System.out.println("1.- DEVOLVER POR DNI.");
                System.out.println("2.- DEVOLVER POR MATRÍCULA.");
                switch (Consola.leerCadena("Introduce la opción deseada: ")) {
                    case "1":
                        Cliente clienteAlquiler = Consola.leerClienteDNI();
                        boolean encontrado=false;
                        if(controlador.buscar(clienteAlquiler)==null){
                            System.out.println("No hay clientes con ese DNI.");
                        }
                        // Comprobar si hay clientes con un alquiler
                        for (Alquiler alquiler : controlador.getAlquileres()) {
                            if(alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())){
                                controlador.devolver(alquiler, Consola.leerFechaDevolucion());
                                encontrado=true;
                                System.out.println("Alquiler devuelto con éxito!: "+alquiler);
                                break;
                            }
                        }
                        if(!encontrado){
                            System.out.println("No existe ningun alquiler con ese cliente.");
                            break;
                        }break;
                    case "2":
                    Turismo turismoAlquiler = Consola.leerTurismoMatricula();
                    boolean encontrado2=false;
                    if(controlador.buscar(turismoAlquiler)==null){
                        System.out.println("No hay turismos con esa matrícula.");
                    }
                    // Comprobar si hay clientes con un alquiler
                    for (Alquiler alquiler : controlador.getAlquileres()) {
                        if(alquiler.getTurismo().getMatricula().equals(turismoAlquiler.getMatricula())){
                            controlador.devolver(alquiler, Consola.leerFechaDevolucion());
                            encontrado2=true;
                            System.out.println("Alquiler devuelto con éxito!: "+alquiler);
                            break;
                        }
                    }
                    if(!encontrado2){
                        System.out.println("No existe ningun alquiler con ese turismo.");
                        break;
                    }
                    default:
                        break;
                }break;
            case BORRAR_CLIENTE:
                Cliente clienteBorrar = Consola.leerClienteDNI();
                if(controlador.buscar(clienteBorrar)==null){
                    System.out.println("No hay clientes con ese DNI.");
                    break;
                }
                if(controlador.borrar(clienteBorrar)==true){
                    System.out.println("Cliente borrado con éxito.");

                }
                break;

            case BORRAR_TURISMO:
            Turismo turismoBorrar = Consola.leerTurismoMatricula();
                if(controlador.buscar(turismoBorrar)==null){
                    System.out.println("No hay turismos con esa matrícula.");
                    break;
                }
                if(controlador.borrar(turismoBorrar)==true){
                    System.out.println("Turismo borrado con éxito.");
                }
                break;
            case BORRAR_ALQUILER:
            System.out.println("1.- BORRAR POR DNI.");
            System.out.println("2.- BORRAR POR MATRÍCULA.");
            switch (Consola.leerCadena("Introduce la opción deseada: ")) {
                case "1":
                    Cliente clienteAlquiler = Consola.leerClienteDNI();
                    boolean encontrado=false;
                    if(controlador.buscar(clienteAlquiler)==null){
                        System.out.println("No hay clientes con ese DNI.");
                    }
                    // Comprobar si hay clientes con un alquiler
                    for (Alquiler alquiler : controlador.getAlquileres()) {
                        if(alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())){
                            controlador.borrar(alquiler);
                            encontrado=true;
                            System.out.println("Alquiler eliminado con éxito!: "+alquiler);
                            break;
                        }
                    }
                    if(!encontrado){
                        System.out.println("No existe ningun alquiler con ese cliente.");
                        break;
                    }break;
                case "2":
                Turismo turismoAlquiler = Consola.leerTurismoMatricula();
                boolean encontrado2=false;
                if(controlador.buscar(turismoAlquiler)==null){
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if(alquiler.getTurismo().getMatricula().equals(turismoAlquiler.getMatricula())){
                        controlador.borrar(alquiler);
                        encontrado2=true;
                        System.out.println("Alquiler eliminado con éxito!: "+alquiler);
                        break;
                    }
                }
                if(!encontrado2){
                    System.out.println("No existe ningun alquiler con ese turismo.");
                    break;
                }
                default:
                    break;
            }break;
            /*
            Alquiler alquilerBorrar = Consola.leerTurismoMatricula();
            if(controlador.buscar(turismoBorrar)==null){
                System.out.println("No hay turismos con esa matrícula.");
                break;
            }
            if(controlador.borrar(turismoBorrar)==true){
                System.out.println("Turismo borrado con éxito.");
            }
            break;*/
            case LISTAR_CLIENTES:
                ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
                listaClientes=controlador.getClientes();
                for (Cliente clienteListado : listaClientes) {
                    System.out.println(clienteListado);
                }
                break;
                
            case LISTAR_TURISMOS:
                ArrayList<Turismo> listaTurismos = new ArrayList<Turismo>();
                listaTurismos=controlador.getTurismos();
                for (Turismo turismoListado : listaTurismos) {
                    System.out.println(turismoListado);
                }
                break;

            case LISTAR_ALQUILERES:
                ArrayList<Alquiler> listaAlquileres = new ArrayList<Alquiler>();
                listaAlquileres=controlador.getAlquileres();
                for (Alquiler alquilerListado : listaAlquileres) {
                    System.out.println(alquilerListado);
                }
                break;
            
            case LISTAR_ALQUILERES_CLIENTE:
                Cliente clienteAlquiler = Consola.leerClienteDNI();
                boolean encontrado1=false;
                boolean encontrado2=true;
                if(controlador.buscar(clienteAlquiler)==null){
                    System.out.println("No hay clientes con ese DNI.");
                }
                // Comprobar si hay clientes con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if(alquiler.getCliente().getDni().equals(clienteAlquiler.getDni())){
                        if(encontrado2){
                            System.out.println("Alquileres del cliente introducido:");
                        }
                        encontrado2=false;
                        encontrado1=true;
                        System.out.println(alquiler);
                    }
                }
                if(!encontrado1){
                    System.out.println("No existe ningun alquiler con ese cliente.");
                    break;
                }
                    break;
            case LISTAR_ALQUILERES_TURISMO:
                Turismo turismoAlquiler2 = Consola.leerTurismoMatricula();
                boolean encontrado3=false;
                boolean encontrado4=true;
                if(controlador.buscar(turismoAlquiler2)==null){
                    System.out.println("No hay turismos con esa matrícula.");
                }
                // Comprobar si hay turismos con un alquiler
                for (Alquiler alquiler : controlador.getAlquileres()) {
                    if(alquiler.getTurismo().getMatricula().equals(turismoAlquiler2.getMatricula())){
                        if(encontrado4){
                            System.out.println("Alquileres del turismo introducido:");
                        }
                        encontrado4=false;
                        encontrado3=true;
                        System.out.println(alquiler);
                    }
                }
                if(!encontrado3){
                    System.out.println("No existe ningun alquiler con ese turismo.");
                    break;
                }
                    break;
                default:
                break;
        }
    }
}
