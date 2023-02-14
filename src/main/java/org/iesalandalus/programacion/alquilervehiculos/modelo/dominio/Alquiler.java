package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Alquiler {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    private static int PRECIO_DIA = 20;

    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucion;

    private Cliente cliente;
    private Turismo turismo;

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        /*if(fechaAlquiler.isAfter(LocalDate.now())) {
            // Permite asignar un alquiler pasado o en el presente, pero no en el futuro.
            throw new IllegalArgumentException("No puedes alquilar en el futuro.");
        }*/
        /*
        try {
            // Analizar la fecha de alquiler con el formato especificado
            FORMATO_FECHA.parse(fechaAlquiler.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato de la fecha de alquiler no es correcto.");
        }
        */
        this.fechaAlquiler = fechaAlquiler;
    }

    public void setFechaDevolucion(LocalDate fechaDevolcion) {
        if(fechaDevolcion.isEqual(fechaAlquiler) || (fechaDevolcion.isBefore(fechaAlquiler)) || fechaDevolcion.isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("No puedes devolver un alquiler.");
        }
        try {
            // Analizar la fecha de alquiler con el formato especificado
            FORMATO_FECHA.parse(fechaDevolcion.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato de la fecha de devolucion no es correcto.");
        }
        this.fechaDevolucion = fechaDevolcion;
    }

    public Alquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) {
        this.cliente = cliente;
        this.turismo = turismo;
        setFechaAlquiler(fechaAlquiler);
    }

    public Alquiler(Alquiler alquiler) {
        setFechaAlquiler(fechaAlquiler);
        setFechaDevolucion(fechaDevolucion);
        
        // Instanciar Cliente y Turismo
        this.cliente = new Cliente(alquiler.cliente);
        this.turismo = new Turismo(alquiler.turismo);
    }

    public void devolver(LocalDate fechaDevolucion) {
        setFechaDevolucion(fechaDevolucion);
    }

    public int getPrecio() {
        // (precioDia + factorCilindrada) * numDias. El precioDia es 20, el factorCilindrada depende de la cilindrada del turismo alquilado 
        // y es igual a la cilindrada del turismo / 10 y numDias son los días transcurridos entre la fecha de alquiler y la de devolución.
        if(fechaAlquiler==null){
            throw new NullPointerException("FECHA NULA");
        }
        if(fechaDevolucion == null){
            return 0;
        }
        int factorCilindrada = turismo.getCilindrada()/10;
        int numDias = (int) ChronoUnit.DAYS.between(fechaAlquiler, fechaDevolucion); // (int) se encarga de pasar de double a int.
        return (PRECIO_DIA+factorCilindrada)*numDias;

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Turismo getTurismo() {
        return turismo;
    }

    public void setTurismo(Turismo turismo) {
        this.turismo = turismo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((turismo == null) ? 0 : turismo.hashCode());
        result = prime * result + ((fechaAlquiler == null) ? 0 : fechaAlquiler.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Alquiler alquilerOtro = (Alquiler) obj;
        if (cliente == null) {
            if (alquilerOtro.cliente != null)
                return false;
        } else if (!cliente.equals(alquilerOtro.cliente))
            return false;
        if (turismo == null) {
            if (alquilerOtro.turismo != null)
                return false;
        } else if (!turismo.equals(alquilerOtro.turismo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alquiler [fechaAlquiler=" + fechaAlquiler + ", fechaDevolucion=" + fechaDevolucion + ", cliente="
                + cliente + ", turismo=" + turismo + "]";
    }

    




    

    
}
