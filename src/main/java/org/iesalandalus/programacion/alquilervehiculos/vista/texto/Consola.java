package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA="dd/MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern(PATRON_FECHA);

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
        for(int i=0;i<Accion.values().length;i++){
            mostrarCabecera(Accion.values()[i].toString());
        }
    }

    public static Accion elegirAccion() {
		
		int ordinalOpcion = 0;
		boolean error=false; 
		
		do {
			try {
				error=false; 
				ordinalOpcion=leerEntero("Elige una opción: "); 
				
				Accion.esOrdinalValido(ordinalOpcion);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				error = true;
			}
			
		} while (error);
		
		return Accion.get(ordinalOpcion);
	}

    public static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();  
    }

    private static Integer leerEntero(String mensaje) {
		
		System.out.println(mensaje); 
		int entero=Entrada.entero(); 
		
		return entero; 
	}

    private static int leerCilindrada(String mensaje){
        System.out.println(mensaje);
        return Entrada.entero();
    }
    public static LocalDate leerFecha(String mensaje) {
		
		int dia=0; 
		int mes=0; 
		int year=0; 
		
		LocalDate fecha=null; 
		
		do {
			
			fecha=null; 
			System.out.println(mensaje);
			
			dia=leerEntero("Por favor, indique el día: "); 
			mes=leerEntero("Por favor, indique el mes: ");
			year=leerEntero("Por favor, indique el año: ");
			
			fecha = LocalDate.of(year, mes, dia);
			
		}while(fecha==null); 
		
		return fecha;
	}
    public static Accion elegirOpcion(){
        int entero=leerEntero("");
        return Accion.values()[entero];
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

    public static Vehiculo leerTurismo(){
        do{
            try{
                return new Turismo(leerCadena("Introduce la marca: "), leerCadena("Introduce el modelo: "),leerCadena("Introduce la matrícula: "), leerCilindrada("Introduce la cilindrada"));    
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }while(true);
    }

    public static Vehiculo leerTurismoMatricula(){
        do{
            String matricula=leerCadena("Introduce la matricula del turismo: ");
                try{
                    return Vehiculo.getVehiculoConMatricula(matricula);
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

    private static TipoVehiculo elegirTipoVehiculo() {
		
		int ordinalOpcion = 0;
		
		try {
				ordinalOpcion=leerEntero("\nElige una opción: "); 
				
				TipoVehiculo.esOrdinalValido(ordinalOpcion);
				
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
			}
		
		return TipoVehiculo.get(ordinalOpcion);
	}

    private static void mostrarMenuTiposVehiculos() {
		
		System.out.println(); 
		mostrarCabecera("TIPOS DE VEHÍCULOS"); 
		
		for (TipoVehiculo tipoVehiculo: TipoVehiculo.values()) {
			System.out.println(tipoVehiculo);
		} 
	}

    public static Vehiculo leerVehiculo() {
		
		mostrarMenuTiposVehiculos(); 
		
		TipoVehiculo tipoVehiculo = elegirTipoVehiculo(); 
		
		return leerVehiculo(tipoVehiculo); 
	}

    private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		
		if(tipoVehiculo.equals(TipoVehiculo.TURISMO)){
			
			Turismo turismo = new Turismo(leerCadena("Introduzca la marca del turismo: "), 
					leerCadena("Introduzca el modelo del turismo: "),
                    leerCadena("Introduzca la matrícula del turismo: "),
					leerEntero("Introduzca la cilindrada del turismo: "));
					
			
			return turismo; 
		}
		
		if(tipoVehiculo.equals(TipoVehiculo.AUTOBUS)){
			
			Autobus autobus = new Autobus(leerCadena("Introduzca la marca del autobús: "), 
					leerCadena("Introduzca el modelo del autobús: "),
					leerCadena("Introduzca la matrícula del autobús: "),
                    leerEntero("Introduzca el número de plazas del autobús: ")); 
			
			return autobus; 
		}
		
		if(tipoVehiculo.equals(TipoVehiculo.FURGONETA)){
			
			Furgoneta furgoneta = new Furgoneta(leerCadena("Introduzca la marca de la furgoneta: "),
					leerCadena("Introduzca el modelo de la furgoneta: "),
                    leerCadena("Introduzca la matrícula de la furgoneta: "),
					leerEntero("Introduzca el PMA de la furgoneta: "),
					leerEntero("Introduzca el número de plazas de la furgoneta: ")); 
			
			return furgoneta; 
		}
		return null;
	}

    public static Vehiculo leerVehiculoMatricula() {
		
		String matricula = leerCadena("Introduzca la matrícula del vehículo: ");
		
		Vehiculo vehiculo = new Turismo("Seat", "Ibiza", matricula, 1); 
		
		return vehiculo; 
	}

    public static LocalDate leerMes() {
		
		LocalDate mes = leerFecha("Introduzca el mes: "); 
	
		return mes; 
	}
    
}
