package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.BBDD.FuenteDatosBBDD;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.FuenteDatosMemoria;

public enum FactoriaFuenteDatos {
    MEMORIA {
        // Al existir un método abstracto crear(), debemos implementarlo en todos los
        // elementos que tenga el enumerado.
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    },
    FICHEROS {
        // Al existir un método abstracto crear(), debemos implementarlo en todos los
        // elementos que tenga el enumerado.
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosFicheros();
        }
    },
    BBDD {
        // Al existir un método abstracto crear(), debemos implementarlo en todos los
        // elementos que tenga el enumerado.
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosBBDD();
            }
    };

    public abstract IFuenteDatos crear();
}

