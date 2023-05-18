package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;


import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {
    private static final String RUTA_FICHERO = "datos/clientes.xml";  
	private static final String RAIZ = "Clientes"; 
	private static final String CLIENTE = "Cliente"; 
	private static final String NOMBRE = "Nombre"; 
	private static final String DNI = "Dni"; 
	private static final String TELEFONO = "Telefono"; 
	private static final String TIPO_DATO = "TipoDato"; 
    private static Clientes instancia; 
    private static List<Cliente> coleccionClientes;

	private Clientes() {
		
	}



	static Clientes getInstancia() {
		
		if(instancia == null) {
            			
			instancia = new Clientes(); // Crear la instancia sólo si aún no se ha creado
        }
       
		return instancia;
	}

    @Override
	public void comenzar() {
		
		coleccionClientes = new ArrayList<>();
		instancia = new Clientes(); 

		leerXml();
		
	}

	private void leerXml() {
		
		Document document = UtilidadesXml.xmlToDom(RUTA_FICHERO); 
		NodeList nodeList=document.getDocumentElement().getChildNodes();

		for (int i=0; i<nodeList.getLength();i++){
			
			Node node=nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE){
				
				Element element = (Element) node;
				
				Cliente cliente = elementToCliente(element); 
				
				coleccionClientes.add(cliente);
			}
		}
    }

    private Cliente elementToCliente(Element element) {
		
		Cliente cliente = null; 
		
		String dni = element.getAttribute(DNI);
		Element nombre = (Element) element.getElementsByTagName(NOMBRE).item(0);
		Element telefono = (Element) element.getElementsByTagName(TELEFONO).item(0);
				
		cliente = new Cliente(nombre.getTextContent(), dni, telefono.getTextContent()); 
		
		return cliente; 
	}

	@Override
	public void terminar() {
		/*char opcion='a';
		do{
			String cadena=Consola.leerCadena("***GUARDAR CLIENTES***\nElige una opción:\na)Sobreescribir el documento XML. Ruta: ("+RUTA_FICHERO+")\nb)Crear documento XML nuevo.");
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
				}while(nombre.equals("atras"));

					escribirXml(ruta, nombre);
					break;

			default:
				break;
		}*/
		escribirXml();
	}

	private void escribirXml() {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Cliente cliente : coleccionClientes) {
	        
	    	clienteToElement(document, cliente); 
	    }
	    
	    UtilidadesXml.domToXml(document, RUTA_FICHERO);
	}

	/*private void escribirXml(String ruta, String nombre) {
	    
		Document document = UtilidadesXml.crearDomVacio(RAIZ);
	    
	    for (Cliente cliente : coleccionClientes) {
	        
	    	clienteToElement(document, cliente); 
	    }

		String rutaCompleta=ruta.concat("/").concat(nombre).concat(".xml");
	    
	    UtilidadesXml.domToXml(document, rutaCompleta);
	}*/

	private Element clienteToElement(Document DocDOM, Cliente cliente) {

	    Element raiz = DocDOM.getDocumentElement();

		Element elementoCliente = DocDOM.createElement(CLIENTE);
	    elementoCliente.setAttribute(DNI, cliente.getDni());

	    Element elementoNombre = DocDOM.createElement(NOMBRE);
	    elementoNombre.setAttribute(TIPO_DATO, "String");
	    elementoNombre.appendChild(DocDOM.createTextNode(cliente.getNombre()));
	    elementoCliente.appendChild(elementoNombre);

	    Element elementoTelefono = DocDOM.createElement(TELEFONO);
	    elementoTelefono.setAttribute(TIPO_DATO, "String");
	    elementoTelefono.appendChild(DocDOM.createTextNode(cliente.getTelefono()));
	    elementoCliente.appendChild(elementoTelefono);

		raiz.appendChild(elementoCliente);

	    return elementoCliente;
	}

    @Override
	public List<Cliente> get(){
		
		ArrayList<Cliente> copiaClientes = new ArrayList<>(); 
		copiaClientes.addAll(coleccionClientes);
		
		Collections.sort(copiaClientes, new Comparator<Cliente>() {
			
			   public int compare(Cliente cliente1, Cliente cliente2) {
				   
			      return cliente1.getNombre().compareTo(cliente2.getNombre());
			   }
		});
		
		return copiaClientes; 
	}

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException{
        if(cliente==null) {
			
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo."); 
			
		}else if(coleccionClientes.contains(cliente)){
				
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI."); 
		}
		
		coleccionClientes.add(cliente);
    }

	@Override
	public Cliente buscar(Cliente cliente) {
		
		if(cliente==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo."); 
		}
		
		Cliente cliente2 = null; 
		
		Iterator <Cliente> iterador=coleccionClientes.iterator(); 
		
		while (iterador.hasNext()) { 
			
			cliente2=iterador.next(); 
			
			if(cliente2.getDni().equals(cliente.getDni())) {
				
				return cliente2; 
			} 
		}
		
		return null; 
	}

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException{
        boolean borrado=false;
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
        }

		for (Alquiler alquiler : Alquileres.getInstancia().get()) {
			if((alquiler.getCliente().equals(cliente)) && (alquiler.getFechaDevolucion()==null)){
				throw new OperationNotSupportedException("ERROR: No se puede borrar un turismo que tiene alquileres en curso.");
			}
		}
        for(int i=0;i<coleccionClientes.size();i++){
            if(coleccionClientes.get(i)==cliente){
                coleccionClientes.remove(i);
                borrado=true;
            }
        }
        if(!borrado){
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
            
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{
        if(cliente==null){
            throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
        }
        if(!coleccionClientes.contains(cliente)){
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
        if(nombre==null&&telefono!=null){
            cliente.setTelefono(telefono);
        }
        if(nombre!=null&&telefono==null){
            cliente.setNombre(nombre);
        }
        if(cliente!=null&&nombre!=null&&telefono!=null){
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
        }
    }
}
