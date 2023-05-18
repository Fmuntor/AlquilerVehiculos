package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {

    private static final String RUTA_FICHERO = "datos/vehiculos.xml";
	private static final String RAIZ = "Vehiculos"; 
	private static final String VEHICULO = "Vehiculo"; 
	private static final String MARCA = "Marca"; 
	private static final String MODELO = "Modelo"; 
	private static final String MATRICULA = "Matricula"; 
	private static final String CILINDRADA ="Cilindrada"; 
	private static final String PLAZAS = "Plazas"; 
	private static final String PMA = "Pma"; 
	private static final String TIPO = "Tipo"; 
	private static final String TURISMO = "Turismo"; 
	private static final String AUTOBUS = "Autobus"; 
	private static final String FURGONETA = "Furgoneta"; 
	private static final String TIPO_DATO = "TipoDato"; 

    private static Vehiculos instancia;
    private static List<Vehiculo> coleccionVehiculos = new ArrayList<Vehiculo>();
	
	private Vehiculos() {
		
	}

    protected static Vehiculos getInstancia(){
        if(instancia == null) {
			
			instancia = new Vehiculos();
        }
       
		return instancia;
    }

    @Override
	public void comenzar() {
		
		coleccionVehiculos = new ArrayList<>();
		instancia = new Vehiculos(); 
		leerXml();
		
	}
	
	private void leerXml() {
		
		Document documento = UtilidadesXml.xmlToDom(RUTA_FICHERO); 
		NodeList listaNodos=documento.getDocumentElement().getChildNodes();

		for (int i=0; i<listaNodos.getLength();i++){
			
			Node nodo=listaNodos.item(i);
			
			if(nodo.getNodeType() == Node.ELEMENT_NODE){
				
				Element elemento = (Element) nodo;
				
				Vehiculo vehiculo = elementToVehiculo(elemento); 
				
				coleccionVehiculos.add(vehiculo);
			}
		}
	}
	
	private Vehiculo elementToVehiculo(Element elemento) {
		
		Vehiculo vehiculo = null; 
		
		String matricula = elemento.getAttribute(MATRICULA);
		String tipo = elemento.getAttribute(TIPO);
		String marca = elemento.getElementsByTagName(MARCA).item(0).getTextContent();
		String modelo = elemento.getElementsByTagName(MODELO).item(0).getTextContent();

		if (tipo.equals(TURISMO)) {
			
			int cilindrada = Integer.parseInt(elemento.getElementsByTagName(CILINDRADA).item(0).getTextContent());
			
			vehiculo = new Turismo(marca, modelo, matricula, cilindrada); 
					
		return vehiculo; 
			
		} else if (tipo.equals(AUTOBUS)) {
			
			int plazas = Integer.parseInt(elemento.getElementsByTagName(PLAZAS).item(0).getTextContent());
			
			vehiculo = new Autobus(marca, modelo, matricula, plazas); 
			
		return vehiculo; 
			
		} else if (tipo.equals(FURGONETA)) {
			int pma = Integer.parseInt(elemento.getElementsByTagName(PMA).item(0).getTextContent());
			int plazas = Integer.parseInt(elemento.getElementsByTagName(PLAZAS).item(0).getTextContent());
			
			vehiculo = new Furgoneta(marca, modelo, matricula, pma, plazas); 
			
		return vehiculo; 
		
		}

		return null;
	}

	@Override
	public void terminar() {
		/*char opcion='a';
		do{
			String cadena=Consola.leerCadena("***GUARDAR VEHICULOS***\nElige una opción:\na)Sobreescribir el documento XML. Ruta: ("+RUTA_FICHERO+")\nb)Crear documento XML nuevo.");
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
		}*/
		escribirXml();
	}

	private void escribirXml() {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Vehiculo vehiculo : coleccionVehiculos) {
	        
	    	vehiculoToElement(document, vehiculo); 
	    }
	    
	    UtilidadesXml.domToXml(document, RUTA_FICHERO);
	}

	/*private void escribirXml(String ruta, String nombre) {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Vehiculo vehiculo : coleccionVehiculos) {
	        
	    	vehiculoToElement(document, vehiculo); 
	    }

		String rutaCompleta=ruta.concat("/").concat(nombre).concat(".xml");
	    
	    UtilidadesXml.domToXml(document, rutaCompleta);
	}*/

	
	private Element vehiculoToElement(Document dom, Vehiculo vehiculo) {

		Element raiz = dom.getDocumentElement();
	    
		String matricula = vehiculo.getMatricula();
		String tipo = vehiculo.getClass().getSimpleName();
		String marca = vehiculo.getMarca();
		String modelo = vehiculo.getModelo();

		Element elementoVehiculo = dom.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MATRICULA, matricula);
		elementoVehiculo.setAttribute(TIPO, tipo);

		Element marcaElement = dom.createElement(MARCA);
		marcaElement.setAttribute(TIPO_DATO, "String");
		marcaElement.setTextContent(marca);
		elementoVehiculo.appendChild(marcaElement);

		Element modeloElement = dom.createElement(MODELO);
		modeloElement.setAttribute(TIPO_DATO, "String");
		modeloElement.setTextContent(modelo);
		elementoVehiculo.appendChild(modeloElement);

		if (tipo.equals(TURISMO)) {

			Turismo turismo = (Turismo) vehiculo;
			int cilindrada = turismo.getCilindrada();
			Element turismoElement = dom.createElement(TURISMO);
			Element cilindradaElement = dom.createElement(CILINDRADA);
			cilindradaElement.setAttribute(TIPO_DATO, "Integer");
			cilindradaElement.setTextContent(String.valueOf(cilindrada));
			turismoElement.appendChild(cilindradaElement);
			elementoVehiculo.appendChild(turismoElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;

		} else if (tipo.equals(AUTOBUS)) {

			Autobus autobus = (Autobus) vehiculo;
			int plazas = autobus.getPlazas();
			Element autobusElement = dom.createElement(AUTOBUS);
			Element plazasBusElement = dom.createElement(PLAZAS);
			plazasBusElement.setAttribute(TIPO_DATO, "Integer");
			plazasBusElement.setTextContent(String.valueOf(plazas));
			autobusElement.appendChild(plazasBusElement);
			elementoVehiculo.appendChild(autobusElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;

		} else if (tipo.equals(FURGONETA)) {

			Furgoneta furgoneta = (Furgoneta) vehiculo;
			int pma = furgoneta.getPma();
			int plazas = furgoneta.getPlazas();
			Element furgonetaElement = dom.createElement(FURGONETA);
			Element pmaElement = dom.createElement(PMA);
			Element plazasFurgoElement = dom.createElement(PLAZAS);
			pmaElement.setAttribute(TIPO_DATO, "Integer");
			pmaElement.setTextContent(String.valueOf(pma));
			plazasFurgoElement.setAttribute(TIPO_DATO, "Integer");
			plazasFurgoElement.setTextContent(String.valueOf(plazas));
			furgonetaElement.appendChild(pmaElement);
			furgonetaElement.appendChild(plazasFurgoElement);
			elementoVehiculo.appendChild(furgonetaElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;
		}
		
		return null;
	}

    @Override
    public List<Vehiculo> get() {
		
		ArrayList<Vehiculo> copiaVehiculos = new ArrayList<>(); 
		copiaVehiculos.addAll(coleccionVehiculos);
		
		Collections.sort(copiaVehiculos, new Comparator<Vehiculo>() {
			
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				int resultado = vehiculo1.getMarca().compareTo(vehiculo2.getMarca());
				if (resultado != 0) {
					return resultado;
				}
				resultado = vehiculo1.getModelo().compareTo(vehiculo2.getModelo());
				if (resultado != 0) {
					return resultado;
				}
				return vehiculo1.getMatricula().compareTo(vehiculo2.getMatricula());
			}
		});
		return copiaVehiculos; 
	}
    
    @Override
    public void insertar(Vehiculo turismo) throws OperationNotSupportedException{
        if (turismo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un turismo nulo.");
        }
        if(coleccionVehiculos.size()==0){
            // Si el arraylist está vacio, inserta el primer turismo.
            coleccionVehiculos.add(turismo);
        
        }else{
            for(int i=0;i<coleccionVehiculos.size();i++){
                if(coleccionVehiculos.get(i)==turismo){
                    throw new OperationNotSupportedException("ERROR: Ya existe un turismo con esa matrícula.");
                }
            }
            coleccionVehiculos.add(turismo);
        }
    }    

    @Override
    public Vehiculo buscar(Vehiculo turismo){
        if(turismo==null){
            throw new NullPointerException("ERROR: No se puede buscar un turismo nulo.");
        }
		if(coleccionVehiculos==null||coleccionVehiculos.size()==0){
			return null; 
		}


        for(Vehiculo turismo2 : coleccionVehiculos){
            if(turismo2.getMatricula().equals(turismo.getMatricula())){
                return turismo2;
            }
        }return null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException{
        boolean borrado=false;
        if (vehiculo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un vehiculo nulo.");
        }

		for (Alquiler alquiler : Alquileres.getInstancia().get()) {
			if((alquiler.getVehiculo().equals(vehiculo)) && (alquiler.getFechaDevolucion()==null)){
				throw new OperationNotSupportedException("ERROR: No se puede borrar un vehículo que tiene alquileres en curso.");
			}
		}
        for(int i=0;i<coleccionVehiculos.size();i++){
            if(coleccionVehiculos.get(i)==vehiculo){
                coleccionVehiculos.remove(i);
                borrado=true;
            }
        }
        if(!borrado){
            throw new OperationNotSupportedException("ERROR: No existe ningún turismo con esa matrícula.");
        }

    }
}
