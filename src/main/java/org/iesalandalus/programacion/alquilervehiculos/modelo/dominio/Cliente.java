package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Cliente {
    private final String ER_NOMBRE = "^[A-Z][a-z]+( [A-Z][a-z]+){0,3}$"; // Acepta nombre compuesto de hasta 4 palabras
    private final String ER_DNI = "^(\\d{8})([-]?)([A-Za-z])$";
    private final String ER_TELEFONO = "^\\d{9}$";

    private String nombre;
    private String Dni;
    private String telefono;

    public Cliente(String nombre, String Dni, String telefono) {
        setNombre(nombre);
        setDni(Dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        if(cliente == null) {
            throw new NullPointerException("ERROR: No es posible copiar un cliente nulo.");
        }
        setNombre(cliente.getNombre());
        setDni(cliente.getDni());
        setTelefono(cliente.getTelefono());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {  
        if(nombre==null)
        {
            throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
        }
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
        }
        if(!nombre.matches(ER_NOMBRE)){
            throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
        }
        nombre = nombre.trim();

        // Formateo del nombre.
        String[] palabras = nombre.split("\\s+");

        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();
        }
    
        nombre = String.join(" ", palabras);
        nombre = nombre.trim();

        this.nombre = nombre;

    }

    public String getDni() {
        return Dni;
    }

    public boolean comprobarLetraDni(String Dni){
        // Definir las posibles letras
        String mapaLetraDni = "TRWAGMYFPDXBNJZSQVHLCKE";

        // Comprobar si la cadena de entrada coincide con la expresión regular
        if (!Dni.matches(ER_DNI)) {
            return false;
        }

         // Extraer las partes número y letra del Dni asignarlas a dos variables
         String numero = Dni.substring(0, 8);
         String letra = Dni.substring(8, 9);
        
        // Calcular la letra correcta para el número de Dni
        int numeroDni = Integer.parseInt(numero);
        char letraEsperada = mapaLetraDni.charAt(numeroDni % 23);

        // Compara la letra calculada con la proporcionada en la cadena de entrada
        return letra.equalsIgnoreCase(String.valueOf(letraEsperada));
    }

    public void setDni(String Dni) {
        if (Dni == null){
            throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
        }
        if(Dni.trim().isEmpty() || Dni.length() != 9 || !Dni.matches(ER_DNI)){
            throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
        }
        if(!comprobarLetraDni(Dni)){
            throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
        }
        this.Dni = Dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null){
            throw new NullPointerException("ERROR: El teléfono no puede ser nulo.");
        }
        if(telefono.trim().isEmpty() || !telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
        }

        this.telefono = telefono;
    }

    public static Cliente getClienteConDni(String Dni) {

        if (Dni == null){
            throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
        }
        if(Dni.trim().isEmpty() || Dni.length() != 9 || !Dni.matches("^(\\d{8})([-]?)([A-Za-z])$")){
            throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
        }
        
        String mapaLetraDni = "TRWAGMYFPDXBNJZSQVHLCKE";

        String numero = Dni.substring(0, 8);
        String letra = Dni.substring(8, 9);
        
        // Calcular la letra correcta para el número de Dni
        int numeroDni = Integer.parseInt(numero);
        char letraEsperada = mapaLetraDni.charAt(numeroDni % 23);

        if(!letra.equalsIgnoreCase(String.valueOf(letraEsperada))){
            throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
        }
        
        Cliente ClienteConDNI = new Cliente("Nombre_DNI",Dni,"000000000");
        return ClienteConDNI;
        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((Dni == null) ? 0 : Dni.hashCode());
        result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (Dni == null) {
            if (other.Dni != null)
                return false;
        } else if (!Dni.equals(other.Dni))
            return false;
        if (telefono == null) {
            if (other.telefono != null)
                return false;
        } else if (!telefono.equals(other.telefono))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return nombre +" - "+Dni+" ("+telefono+")" ;
    }

}
