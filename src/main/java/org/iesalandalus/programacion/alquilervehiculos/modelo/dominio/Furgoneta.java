package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Furgoneta extends Vehiculo{
    private final int FACTOR_PMA=100;
    private final int FACTOR_PLAZAS=1;
    private int pma;
    private int plazas;
    
    public Furgoneta(String marca, String modelo, String matricula, int pma, int plazas) {
        super(marca, modelo, matricula);
        setPma(pma);
        setPlazas(plazas);
    }
    
    public Furgoneta(Vehiculo vehiculo) {
        super(vehiculo);
    }

    public int getPma() {
        return pma;
    }

    public void setPma(int pma) {
        this.pma = pma;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    protected int getFactorPrecio(){
        // (pma / FACTOR_PMA(100)) + (plazas*FACTOR_PLAZAS(1))
        return (pma/FACTOR_PMA)+(plazas*FACTOR_PLAZAS);
    }

    @Override
    protected Vehiculo copiar(Vehiculo vehiculo) {
		
		return new Furgoneta(vehiculo.getMarca(), vehiculo.getModelo(),vehiculo.getMatricula(), getPma(), getPlazas()); 
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pma;
        result = prime * result + plazas;
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
        Furgoneta other = (Furgoneta) obj;
        if (pma != other.pma)
            return false;
        if (plazas != other.plazas)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return " Marca: "+getMarca()+" Modelo: "+getModelo()+" Matrícula: "+getMatricula()
        +" P.M.A: "+pma+" Plazas: "+plazas;
    }
}
