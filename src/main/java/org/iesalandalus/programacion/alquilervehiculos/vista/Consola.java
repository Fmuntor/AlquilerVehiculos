package org.iesalandalus.programacion.alquilervehiculos.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Consola(){}

    public static void mostrarCabecera(String mensaje){
        System.out.println(mensaje);
        int numeroletras=mensaje.length();
        for(int i=0;i<numeroletras;i++){
            System.out.print("-");
        }
        System.out.println("");
    }

    public static void mostrarMenu(){
        for(int i=0;i<Opcion.values().length;i++){
            mostrarCabecera(Opcion.values()[i].toString());
        }
    }

    public static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();  
    }

    private static int leerEntero(String mensaje){
        System.out.println(mensaje);
        int entero=0;
        do{
            entero=Entrada.entero();
            try{
                Opcion.esOrdinalValido(entero);
                return entero;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Entero inválido.");
            }
        }while(true);

    }
    // Al leerEntero tener un límite máximo de 17, al ser la cantidad de opciones del enumerado Opcion, he creado este metodo, cuya finalidad es 
    // unicamente introducir un entero para asignarlo a la cilindrada de un turismo, ya que el valor máximo es superior a 17.

    private static int leerCilindrada(String mensaje){
        System.out.println(mensaje);
        return Entrada.entero();
    }
    public static LocalDate leerFecha(String mensaje){
        String fecha="";
        System.out.println(mensaje);
        // al haber un do-while cuya condicion para que deje de iterar es while(true), no saldrá del bucle hasta que se lanze la instrucción return.
        // Para que se lanze, la fecha debe ser correcta, al parsearla y no recibir una excepción de tipo DateTimeParseException, salimos del bucle.
        // En este caso, al poder controlar la excepción sin problemas en este método, se tratan aquí.
        do{
            fecha=Entrada.cadena();
            try {
                LocalDate.parse(fecha, FORMATO_FECHA);
                return LocalDate.parse(fecha, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha no tiene el formato adecuado.");
            }
        }while(true);
    }

    public static Opcion elegirOpcion(){
        int entero=leerEntero("");
        return Opcion.values()[entero];
    }

    public static String leerNombre(){
        return leerCadena("Introduce el nombre del cliente:");
    }

    public static String leerTelefono(){
        return leerCadena("Introduce el teléfono del cliente: ");
    }

    public static Cliente leerCliente(){
        Cliente cliente = new Cliente(leerNombre(),leerCadena("Introduce un DNI:"), leerTelefono());
        return cliente;
        
    }

    public static Cliente leerClienteDNI(){
        do{
        String DNI=leerCadena("Introduce el DNI del cliente: ");
            try{
                return Cliente.getClienteConDni(DNI);
            }catch(IllegalArgumentException e){
                System.out.println("DNI incorrecto.");
            }
        }while(true);
    }

    public static Turismo leerTurismo(){
        do{
            try{
                return new Turismo(leerCadena("Introduce la marca: "), leerCadena("Introduce el modelo: "), leerCilindrada("Introduce la cilindrada"), leerCadena("Introduce la matrícula: "));    
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }while(true);
    }

    public static Turismo leerTurismoMatricula(){
        do{
            String matricula=leerCadena("Introduce la matricula del turismo: ");
                try{
                    return Turismo.getTurismoConMatricula(matricula);
                }catch(IllegalArgumentException e){
                    System.out.println("Matricula incorrecta.");
                }
        }while(true);
    }

    public static Alquiler leerAlquiler(){
        do{
            try{
                return new Alquiler(leerCliente(), leerTurismo(), leerFecha("Introduce una fecha de alquiler: "));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }while(true);
    }

    public static LocalDate leerFechaDevolucion(){
        return leerFecha("Introduce una fecha de devolución:");
    }
}
