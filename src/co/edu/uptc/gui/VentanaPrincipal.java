package co.edu.uptc.gui;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame{
	private ManejadorEventos       manejadorEventos;
	private Login                  login;
	private Registro               registro;
	private Carrito                carrito;
	private ListaLibros            listaLibros;
	private ModificarLibros        modificarLibros;
	private ActualizarLibros       actualizarLibros;
	private AgregarLibro           agregarLibro;
	private ActualizarDatosCliente actualizarDatosCliente;

	public VentanaPrincipal (){
		manejadorEventos = new ManejadorEventos(this);
		mostrarListaLibros();
	}

	public void cambiarVista (String vista){
	}

	public void mostrarLogin (){
		cambiarVista("login");
	}

	public void mostrarRegistro (){
		cambiarVista("registro");
	}

	public void mostrarCarrito (){
		cambiarVista("carrito");
	}

	public void mostrarListaLibros (){
		cambiarVista("listaLibros");
	}

	public void mostrarModificarLibros (){
		cambiarVista("modificarLibros");
	}

	public void mostrarActualizarLibros (){
		cambiarVista("actualizarLibros");
	}

	public void mostrarAgregarLibro (){
		cambiarVista("agregarLibro");
	}

	public void mostrarActualizarDatosCliente (){
		cambiarVista("actualizarDatosCliente");
	}

	public static void main (String[] args){
		new VentanaPrincipal();
	}
}
