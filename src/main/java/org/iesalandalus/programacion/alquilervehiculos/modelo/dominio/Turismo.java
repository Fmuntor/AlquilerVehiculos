package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Turismo {

    private final String ER_MARCA = "^(Seat|Land Rover|KIA|Rolls-Royce|SsangYong)$";
    private final String ER_MATRICULA = "^[0-9]{4}[A-Z]{3}$";

    private String marca;
    private String modelo;
    private int cilindrada;
    private String matricula;

    private void setModelo(String modelo) {

        if (modelo == null) {
            throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
        }
        if (modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");
        }
        this.modelo = modelo;
    }

    private void setCilindrada(int cilindrada) {

        if (cilindrada <= 0 || cilindrada > 5000) {
            throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");
        }
        this.cilindrada = cilindrada;
    }

    private void setMarca(String marca) {

        if (marca == null) {
            throw new NullPointerException("ERROR: La marca no puede ser nula.");
        }
        if (marca.trim().isEmpty() || !marca.matches(ER_MARCA)) {
            throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");
        }
        this.marca = marca;
    }

    private void setMatricula(String matricula) {

        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }
        if (matricula.trim().isEmpty() || !matricula.matches(ER_MATRICULA)) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }
        
        this.matricula = matricula;

    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public String getMatricula() {
        return matricula;
    }

    public Turismo(String marca, String modelo, int cilindrada, String matricula) {
        setMarca(marca);
        setModelo(modelo);
        setCilindrada(cilindrada);
        setMatricula(matricula);
    }

    public Turismo(Turismo turismo) {
        if(turismo == null) {
            throw new NullPointerException("ERROR: No es posible copiar un turismo nulo.");
        }
        setMarca(turismo.getMarca());
        setModelo(turismo.getModelo());
        setCilindrada(turismo.getCilindrada());
        setMatricula(turismo.getMatricula());
    }

    public static Turismo getTurismoConMatricula(String matricula) {
        /* L90-91 Y L127-128 TESTs*/
        if(matricula==null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }
        if(matricula.isEmpty()) {
            throw new IllegalArgumentException("Matricula vacia.");
        }
        if(!matricula.matches("^[0-9]{4}[A-Z]{3}$")) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }

        Turismo turismoConMatricula = new Turismo(null, null, 0, matricula);
        return turismoConMatricula;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
        result = prime * result + cilindrada;
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
        Turismo other = (Turismo) obj;
        if (marca == null) {
            if (other.marca != null)
                return false;
        } else if (!marca.equals(other.marca))
            return false;
        if (modelo == null) {
            if (other.modelo != null)
                return false;
        } else if (!modelo.equals(other.modelo))
            return false;
        if (cilindrada != other.cilindrada)
            return false;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return marca+" "+modelo+" ("+cilindrada+"CV) - "+matricula;
    }

//Seat León (90CV) - 1234BCD


    
}
