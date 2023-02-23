package org.iesalandalus.programacion.alquilervehiculos.vista;

public enum Opcion {
    SALIR("Salir"),
    INSERTAR_CLIENTE("Insertar cliente"),
    INSERTAR_TURISMO("Insertar Turismo"),
    INSERTAR_ALQUILER("Insertar Alquiler"),
    BUSCAR_CLIENTE("Buscar Cliente"),
    BUSCAR_TURISMO("Buscar Turismo"),
    BUSCAR_ALQUILER("Buscar Alquiler"),
    MODIFICAR_CLIENTE("Modificar Cliente"),
    DEVOLVER_ALQUILER("Devolver Alquiler"),
    BORRAR_CLIENTE("Borrar Cliente"),
    BORRAR_TURISMO("Borrar Turismo"),
    BORRAR_ALQUILER("Borrar Alquiler"),
    LISTAR_CLIENTES("Listar Clientes"),
    LISTAR_TURISMOS("Listar Turismos"),
    LISTAR_ALQUILERES("Listar Alquileres"),
    LISTAR_ALQUILERES_CLIENTE("Listar Alquileres Cliente"),
    LISTAR_ALQUILERES_TURISMO("Listar Alquileres Turismo");

    private String txt;

    private Opcion(String txt) {

        this.txt = txt;
    }

    public static boolean esOrdinalValido(int ordinal){
        if(ordinal<0||ordinal>Opcion.values().length){
            throw new ArrayIndexOutOfBoundsException("Inserta un numero mayor que 0 y menor que 16.");
        }
        return true;
    }

    public static Opcion get(int ordinal){
        if(!esOrdinalValido(ordinal)){
            throw new NullPointerException("El ordinal introducido es inválido.");
        }
        return Opcion.values()[ordinal];
    }

    public String toString(){
        return String.format("%d.- %s", ordinal(), txt);
    }

}
