package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.naming.OperationNotSupportedException;

public class Alquiler {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    private static int PRECIO_DIA = 20;

    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucion;

    private Cliente cliente;
    private Vehiculo vehiculo;

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        if(fechaAlquiler.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
        }
        
        this.fechaAlquiler = fechaAlquiler;
    }

    public void setFechaDevolucion(LocalDate fechaDevolcion){
        this.fechaDevolucion = fechaDevolcion;
    }

    public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {
        if(cliente==null){
            throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
        }
        this.cliente = cliente;
        if(vehiculo==null){
            throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
        }
        this.vehiculo = vehiculo;
        if(fechaAlquiler==null){
            throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
        }
        setFechaAlquiler(fechaAlquiler);
    }

    public Alquiler(Alquiler alquiler) {
        if(alquiler==null){
            throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
        }
        this.fechaAlquiler = alquiler.getFechaAlquiler();
        this.fechaDevolucion = alquiler.getFechaDevolucion();//setFechaDevolucion(fechaDevolucion);
        
        // Instanciar Cliente y vehiculo al ser clase abstracta se llama a copiar() que ya comprueba que tipo
        // de vehículo es.
        this.cliente = new Cliente(alquiler.cliente);
        if(vehiculo.copiar(vehiculo)==null){
            throw new NullPointerException("El vehiculo no puede ser nulo.");
        }
        this.vehiculo = vehiculo.copiar(vehiculo);
    }

    public void devolver(LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
        
        if(fechaDevolucion==null){
            throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
        }
        if(fechaDevolucion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
        }
        if (!fechaDevolucion.isAfter(fechaAlquiler)){
            throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
        }
        if(fechaDevolucion==this.fechaDevolucion){
            throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
        }
        setFechaDevolucion(fechaDevolucion);
    }
    public int getPrecio() {
        // (precioDia + factorCilindrada) * numDias. El precioDia es 20, el factorCilindrada depende de la cilindrada del vehiculo alquilado 
        // y es igual a la cilindrada del vehiculo / 10 y numDias son los días transcurridos entre la fecha de alquiler y la de devolución.
        if(fechaAlquiler==null){
            throw new NullPointerException("FECHA NULA");
        }
        if(fechaDevolucion == null){
            return 0;
        }

        int numDias = (int) ChronoUnit.DAYS.between(fechaAlquiler, fechaDevolucion); // (int) se encarga de pasar de double a int.
        return (PRECIO_DIA+vehiculo.getFactorPrecio())*numDias;

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public void SetVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fechaAlquiler == null) ? 0 : fechaAlquiler.hashCode());
        result = prime * result + ((fechaDevolucion == null) ? 0 : fechaDevolucion.hashCode());
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
        Alquiler other = (Alquiler) obj;
        if (fechaAlquiler == null) {
            if (other.fechaAlquiler != null)
                return false;
        } else if (!fechaAlquiler.equals(other.fechaAlquiler))
            return false;
        if (fechaDevolucion == null) {
            if (other.fechaDevolucion != null)
                return false;
        } else if (!fechaDevolucion.equals(other.fechaDevolucion))
            return false;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (vehiculo == null) {
            if (other.vehiculo != null)
                return false;
        } else if (!vehiculo.equals(other.vehiculo))
            return false;
        return true;
    }

    @Override
    
    public String toString() {
        return String.format("%s <---> %s, %s - %s ("+getPrecio()+"€)",
                cliente, vehiculo, fechaAlquiler.format(FORMATO_FECHA), 
                (fechaDevolucion == null) ? "Aún no devuelto" : fechaDevolucion.format(FORMATO_FECHA), 
                (fechaDevolucion == null) ? LocalDate.now().format(FORMATO_FECHA) : "",
                getPrecio());
    }

    
}
