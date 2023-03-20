package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Autobus extends Vehiculo{
    private final int FACTOR_PLAZAS = 2;
    private int plazas;

    public Autobus(String marca, String modelo, String matricula, int plazas) {
        super(marca, modelo, matricula);
        setPlazas(plazas);
    }

    public Autobus(Vehiculo vehiculo) {
        super(vehiculo);
    }
    public int getPlazas() {
        return plazas;
    }
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    protected int getFactorPrecio(){
        // plazas / FACTOR_PLAZAS(2)
        return plazas * FACTOR_PLAZAS;
    }

    @Override
    protected Vehiculo copiar(Vehiculo vehiculo) {
		
		return new Autobus(vehiculo.getMarca(), vehiculo.getModelo(),vehiculo.getMatricula(), getPlazas()); 
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + plazas;
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
        Autobus other = (Autobus) obj;
        if (plazas != other.plazas)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "marca: "+getMarca()+" Modelo: "+getModelo()+" Matrícula: "+getMatricula()
        +" Plazas: "+plazas;
    }

    

    
}