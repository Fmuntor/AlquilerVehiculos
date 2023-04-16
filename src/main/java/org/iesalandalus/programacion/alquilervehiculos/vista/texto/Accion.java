package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

public enum Accion {
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			
			vista.terminar();
		}
	},
	INSERTAR_CLIENTE("Insertar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarCliente();
		}
	},
	INSERTAR_VEHICULO("Insertar Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarVehiculo();
		}
	},
	INSERTAR_ALQUILER("Insertar Alquiler") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarAlquiler();
		}
	},
	BUSCAR_CLIENTE("Buscar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.buscarCliente();
		}
	},
	BUSCAR_VEHICULO("Buscar Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.buscarVehiculo();
		}
	},
	BUSCAR_ALQUILER("Buscar Alquiler") {
		@Override
		public void ejecutar() throws Exception {

			vista.buscarAlquiler();
		}
	},
	MODIFICAR_CLIENTE("Modificar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.modificarCliente();
		}
	},
	BORRAR_CLIENTE("Borrar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarCliente();
		}
	},
	BORRAR_VEHICULO("Borrar Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarVehiculo();
		}
	},
	BORRAR_ALQUILER("Borrar Alquiler") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarAlquiler();
		}
	},
	LISTAR_CLIENTES("Listar Clientes") {
		@Override
		public void ejecutar() throws Exception {

			vista.listarClientes();
		}
	},
	LISTAR_VEHICULOS("Listar Vehículos") {
		@Override
		public void ejecutar() throws Exception {

			vista.listarVehiculos();
		}
	},
	LISTAR_ALQUILERES("Listar Alquileres") {
		@Override
		public void ejecutar() throws Exception {

			vista.listarAlquileres();
		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar Alquileres Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.listarAlquileresCliente();
		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar Alquileres Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.listarAlquileresVehiculo();
		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver Alquiler del Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.devolverAlquilerCliente();
		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver Alquiler del Vehiculo") {
		@Override
		public void ejecutar() throws Exception {

			vista.devolverAlquilerVehiculo();
		}
	},
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar Estadísticas Mensuales") {
		@Override
		public void ejecutar() throws Exception {

			vista.mostrarEstadisticasMensualesTipoVehiculo();
		}
	};

    private String txt;
    public static VistaTexto vista;

    public static void setVista(VistaTexto v){
        vista=v;
    }

    private Accion(String txt) {

        this.txt = txt;
    }

    public abstract void ejecutar() throws Exception;

    public static boolean esOrdinalValido(int ordinal){
        if(ordinal<0||ordinal>Accion.values().length){
            throw new ArrayIndexOutOfBoundsException("Inserta un numero mayor que 0 y menor que 16.");
        }
        return true;
    }

    public static Accion get(int ordinal){
        if(!esOrdinalValido(ordinal)){
            throw new NullPointerException("El ordinal introducido es inválido.");
        }
        return Accion.values()[ordinal];
    }

    public String toString(){
        return String.format("%d.- %s", ordinal(), txt);
    }

}
