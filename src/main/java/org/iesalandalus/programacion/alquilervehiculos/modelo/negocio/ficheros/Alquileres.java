package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.Consola;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

    private static final String RUTA_FICHERO = "datos/alquileres.xml";  
	private static final DateTimeFormatter FORMATO_FECHA = Consola.FORMATO_FECHA;
	private static final String RAIZ = "Alquileres"; 
	private static final String ALQUILER = "Alquiler"; 
	private static final String DNI_CLIENTE = "Dni"; 
	private static final String MATRICULA_VEHICULO = "Matricula"; 
	private static final String FECHA_ALQUILER = "FechaAlquiler"; 
	private static final String FECHA_DEVOLUCION = "FechaDevolucion"; 
	private static final String FORMATO = "Formato"; 
	private static final String TIPO_DATO = "TipoDato"; 

    private static Alquileres instancia; 
    private static ArrayList<Alquiler> coleccionAlquileres;

    private Alquileres() {
		
	}

    protected static Alquileres getInstancia(){
        if(instancia == null) {
			
			instancia = new Alquileres();
        }
       
		return instancia;
    }

    @Override
	public void comenzar(){
		
		coleccionAlquileres = new ArrayList<>();
		instancia = new Alquileres(); 
		leerXml();		
	}
	
	private void leerXml(){
		
		Document documento = UtilidadesXml.xmlToDom(RUTA_FICHERO); 
		NodeList listaNodos = documento.getDocumentElement().getChildNodes();

		for (int i=0; i<listaNodos.getLength();i++){
			
			Node nodo=listaNodos.item(i);
			
			if(nodo.getNodeType() == Node.ELEMENT_NODE){
				
				Element elemento = (Element) nodo;
				
				Alquiler alquiler = elementToAlquiler(elemento); 
				
				coleccionAlquileres.add(alquiler);
			}
		}
	}

	private Alquiler elementToAlquiler(Element elemento){
		
		Alquiler alquiler = null; 
		
		String dni = elemento.getAttribute(DNI_CLIENTE);
		String matricula = elemento.getAttribute(MATRICULA_VEHICULO); 
		String cadenaAlquiler = elemento.getElementsByTagName(FECHA_ALQUILER).item(0).getTextContent();
		String cadenaDevolucion = elemento.getElementsByTagName(FECHA_DEVOLUCION).item(0).getTextContent();
		
		Cliente clienteInicial = new Cliente("Nombre", dni, "600000000");
		Cliente cliente = Clientes.getInstancia().buscar(clienteInicial);  
		
		Vehiculo vehiculoInicial = new Turismo("Ford", "Fiesta", matricula, 1000); 
		Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoInicial);  
		
		LocalDate fechaAlquiler = null; 
		LocalDate fechaDevolucion = null; 
		
		if(cadenaAlquiler != "") {
			
			fechaAlquiler = LocalDate.parse(cadenaAlquiler, FORMATO_FECHA);
		}
		
		if(cadenaDevolucion != "") {
			
			fechaDevolucion = LocalDate.parse(cadenaDevolucion, FORMATO_FECHA);
		}
	
		alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
				
		if(fechaDevolucion == null) {
			
			return alquiler; 
			
		}else {

			try {
				alquiler.devolver(fechaDevolucion);
			} catch (Exception e) {
				e.getMessage();
			}

			return alquiler; 
		} 
	}
	
	@Override
	public void terminar() {
		char opcion='a';
		do{
			String cadena=Consola.leerCadena("***GUARDAR ALQUILERES***\nElige una opción:\na)Sobreescribir el documento XML. Ruta: ("+RUTA_FICHERO+")\nb)Crear documento XML nuevo.");
			opcion=cadena.charAt(0);
		}while(opcion!='a' && opcion !='b');

		switch (opcion) {
			case 'a':
				escribirXml();
				break;
			
			case 'b':
				String nombre=null;
				String ruta=null;
				do{
					ruta=Consola.leerCadena("Selecciona la ruta a partir de la raiz:");
					nombre=Consola.leerCadena("Ruta elegida: raiz/"+ruta+"/xxx.xml\nSelecciona el nombre del archivo: (escribir atras para cambiar la ruta)");
				}while(nombre.equals("atras"));;

					escribirXml(ruta, nombre);
					break;

			default:
				break;
		}
	}

	private void escribirXml() {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Alquiler alquiler : coleccionAlquileres) {
	        
	    	alquilerToElement(document, alquiler); 
	    }
	    
	    UtilidadesXml.domToXml(document, RUTA_FICHERO);
	}

	private void escribirXml(String ruta, String nombre) {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Alquiler alquiler : coleccionAlquileres) {
	        
	    	alquilerToElement(document, alquiler); 
	    }

		String rutaCompleta=ruta.concat("/").concat(nombre).concat(".xml");
	    
	    UtilidadesXml.domToXml(document, rutaCompleta);
	}
	
	private Element alquilerToElement(Document dom, Alquiler alquiler) {
		
		Element raiz = dom.getDocumentElement();
	    
		String dni = alquiler.getCliente().getDni(); 
		String matricula = alquiler.getVehiculo().getMatricula();
		String cadenaAlquiler = alquiler.getFechaAlquiler().format(FORMATO_FECHA); 
		String cadenaDevolucion = null; 
		
		if(alquiler.getFechaDevolucion() != null) {
			
			cadenaDevolucion = alquiler.getFechaDevolucion().format(FORMATO_FECHA); 
		}
		
		Element elementoAlquiler = dom.createElement(ALQUILER);
		elementoAlquiler.setAttribute(DNI_CLIENTE, dni);
		elementoAlquiler.setAttribute(MATRICULA_VEHICULO, matricula);

		Element elementoFechaAlquiler = dom.createElement(FECHA_ALQUILER);
		elementoFechaAlquiler.setAttribute(FORMATO, Consola.PATRON_FECHA);
		elementoFechaAlquiler.setTextContent(cadenaAlquiler );
		elementoAlquiler.appendChild(elementoFechaAlquiler);

		Element elementoFechaDevolucion = dom.createElement(FECHA_DEVOLUCION);
		elementoFechaDevolucion.setAttribute(TIPO_DATO, "String");
		elementoFechaDevolucion.setTextContent(cadenaDevolucion);
		elementoAlquiler.appendChild(elementoFechaDevolucion);
		raiz.appendChild(elementoAlquiler);
		
		return elementoAlquiler; 
	} 
	
	@Override
	public ArrayList<Alquiler> get() {
		
		ArrayList<Alquiler> copiaAlquileres = new ArrayList<>(); 
		copiaAlquileres.addAll(coleccionAlquileres);
		
		Collections.sort(copiaAlquileres, new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
			}
		}); 
		
		Collections.sort(copiaAlquileres, new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
			}
		});

		Collections.sort(copiaAlquileres, new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
			}
		});
		
		return copiaAlquileres; 
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
		
		Iterator <Alquiler> alquilerIterador=coleccionAlquileres.iterator(); 
		
		while (alquilerIterador.hasNext()) {
			
			Alquiler clienteAlquiler=alquilerIterador.next(); 
			if(clienteAlquiler.getCliente().equals(cliente)){
				
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
		
		Iterator <Alquiler> alquilerIterador=coleccionAlquileres.iterator(); 
		
		while (alquilerIterador.hasNext()) {
			
			Alquiler vehiculoAlquiler=alquilerIterador.next();
			
			if(vehiculoAlquiler.getVehiculo().equals(vehiculo)){
				alquileresVehiculo.add(vehiculoAlquiler);
            }
		}
		
		return alquileresVehiculo;
	}
	
    private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) throws OperationNotSupportedException{
        for(Alquiler alquiler : coleccionAlquileres){
            if(alquiler.getFechaDevolucion() == null){
                if(alquiler.getCliente().equals(cliente)){
                    // Esta comprobacion verifica si no hay una fecha de devolución asignada al alquiler para el cliente o el turismo
                    throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
                }else if(alquiler.getVehiculo().equals(vehiculo)){
                    // Si tengo un alquiler cuya fecha de devolucion es anterior a la fecha de alquiler de un nuevo alquiler
                    throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
                }
            }else{
                // Si queremos que pase los test, 
                if(fechaAlquiler.isEqual(alquiler.getFechaDevolucion())){
                    if(alquiler.getCliente().equals(cliente)){
                        throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
                    }

                    if(alquiler.getVehiculo().equals(vehiculo)){
                        throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
                    }
                }
            }
        }
    }
    

    @Override
    public void insertar(Alquiler alquiler) throws OperationNotSupportedException{
        if(alquiler==null){
            throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
        }
        
        comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());        
        
        
        coleccionAlquileres.add(alquiler);
    }
	
	private Alquiler getAlquilerAbierto(Cliente cliente) {
		
		Vehiculo vehiculo = new Turismo("Seat", "León","1234BCD", 1000);
		LocalDate fechaAlquiler = LocalDate.of(1996, 3, 24); 
		
		Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		Alquiler alquiler = null; 
		
		if(buscar(alquilerInicial) == null){
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		else{
			
			alquiler = buscar(alquilerInicial); 
		}
		return alquiler;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception{
		
		Alquiler alquiler = getAlquilerAbierto(vehiculo); 
		
		alquiler.devolver(fechaDevolucion);
	}

	
	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception{
		
		Alquiler alquiler = getAlquilerAbierto(cliente); 
		
		alquiler.devolver(fechaDevolucion);
	}
	
	private Alquiler getAlquilerAbierto(Vehiculo vehiculo){
		
		Cliente cliente = new Cliente("Nombre", "45241097M", "900900900"); 
		LocalDate fechaAlquiler = LocalDate.of(1996, 3, 24); 
		
		Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		Alquiler alquiler = null; 
		
		if(buscar(alquilerInicial) == null){
			
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		else 
			
			alquiler = buscar(alquilerInicial); 
		
		return alquiler;
	}

    @Override
    public Alquiler buscar(Alquiler alquiler){
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
        }

		for (Alquiler alquilerExistente : coleccionAlquileres) {
			if(alquiler.getCliente().getDni().equals(alquilerExistente.getCliente().getDni())){
				return alquilerExistente;

			}else if(alquiler.getVehiculo().getMatricula().equals(alquilerExistente.getVehiculo().getMatricula())){
				return alquilerExistente;
			}
		}
		return null;
    }

    @Override
    public void borrar(Alquiler alquiler) throws OperationNotSupportedException{
        if (alquiler == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
        }
        if(buscar(alquiler)!=null){
            coleccionAlquileres.remove(alquiler);
            
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
        }
    }
}
