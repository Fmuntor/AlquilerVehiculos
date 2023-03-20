package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public abstract class Vehiculo {

    private final String ER_MARCA = "^(Seat|Land Rover|KIA|Rolls-Royce|SsangYong)$";
    private final String ER_MATRICULA = "^[0-9]{4}[A-Z]{3}$";

    private String marca;
    private String modelo;
    private String matricula;

    protected Vehiculo(String marca, String modelo, String matricula) {
        setMarca(marca);
        setModelo(modelo);
        setMatricula(matricula);
    }

    protected Vehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("ERROR: No es posible copiar un vehiculo nulo.");
        }
        setMarca(vehiculo.getMarca());
        setModelo(vehiculo.getModelo());
        setMatricula(vehiculo.getMatricula());
    }

    protected abstract Vehiculo copiar(Vehiculo vehiculo);

    public static Vehiculo getVehiculoConMatricula(String matricula) {
        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }
        if (matricula.trim().isEmpty() || !matricula.matches("^[0-9]{4}[A-Z]{3}$")) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }
        if (!matricula.matches("^[0-9]{4}[BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz]{3}$")) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }

        return new Turismo("Seat", "ModeloTest", matricula, 1);
    }

    protected abstract int getFactorPrecio();

    public String getMarca() {
        return marca;
    }

    protected void setMarca(String marca) {

        if (marca == null) {
            throw new NullPointerException("ERROR: La marca no puede ser nula.");
        }
        if (marca.trim().isEmpty() || !marca.matches(ER_MARCA)) {
            throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    protected void setModelo(String modelo) {

        if (modelo == null) {
            throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
        }
        if (modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");
        }
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    protected void setMatricula(String matricula) {

        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }
        if (matricula.trim().isEmpty() || !matricula.matches(ER_MATRICULA)) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }
        if (!matricula.matches("^[0-9]{4}[BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz]{3}$")) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }

        this.matricula = matricula;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vehiculo))
            return false;
        Vehiculo other = (Vehiculo) obj;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " " + matricula;
    }

    public int compareTo(Vehiculo v){
        // Clientes igaules 0
        // Cliente 1 menor Cliente actual <0
        // Cliente 1 mayor >0
        if(marca==v.getMarca()){
            if(modelo==v.getModelo()){
                return matricula.compareTo(v.getMatricula());
            }
            return modelo.compareTo(v.getModelo());
        }
        return marca.compareTo(v.getMarca());
    }

}
