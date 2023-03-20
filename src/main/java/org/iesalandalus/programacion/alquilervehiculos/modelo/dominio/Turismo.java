package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Turismo extends Vehiculo {
    private final int FACTOR_CILINDRADA = 10;
    private int cilindrada;

    public Turismo(String marca, String modelo, String matricula, int cilindrada) {
        super(marca, modelo, matricula);
        setCilindrada(cilindrada);
    }

    public Turismo(Vehiculo vehiculo) {
        super(vehiculo);
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    protected int getFactorPrecio() {
        // cilindrada / FACTOR_CILINDRADA(10)
        return cilindrada / FACTOR_CILINDRADA;
    }

    @Override
	protected Vehiculo copiar(Vehiculo vehiculo) {
		
		return new Turismo(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getMatricula(), getCilindrada()); 
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + cilindrada;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Turismo other = (Turismo) obj;
        if (cilindrada != other.cilindrada)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return " Marca: " + getMarca() + " Modelo: " + getModelo() + " Matrícula: " + getMatricula()
                + " Cilindrada: " + cilindrada;
    }

}
