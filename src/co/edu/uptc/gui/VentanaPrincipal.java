package co.edu.uptc.gui;

import javax.swing.JFrame;
import java.awt.*;

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
		setTitle("Tienda Digital de Libros");
		setSize(800, 600);
		setBackground(Color.black);
		//pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		manejadorEventos = new ManejadorEventos(this);
		mostrarInterfaz("listaLibros"); //Por Defecto
		setVisible(true);
	}

	public void mostrarInterfaz (String nombreInterfaz){
		switch (nombreInterfaz){
			case "login":
				mostrarLogin();
				return;
			case "registro":
				mostrarRegistro();
				return;
			case "carrito":
				mostrarCarrito();
				return;
			case "listaLibros":
				mostrarListaLibros();
				return;
			case "modificarLibros":
				mostrarModificarLibros();
				return;
			case "actualizarLibros":
				mostrarActualizarLibros();
				return;
			case "agregarLibro":
				mostrarAgregarLibro();
				return;
			case "actualizarDatosCliente":
				mostrarActualizarDatosCliente();
				return;
			default:
				mostrarListaLibros();
		}
	}

	public void mostrarLogin (){
		login = new Login(this, manejadorEventos);
	}

	public void mostrarRegistro (){
		registro = new Registro(this, manejadorEventos);
	}

	public void mostrarCarrito (){
		carrito = new Carrito(this, manejadorEventos);
	}

	public void mostrarListaLibros (){
		listaLibros = new ListaLibros(this, manejadorEventos);
	}

	public void mostrarModificarLibros (){
		modificarLibros = new ModificarLibros(this, manejadorEventos);
	}

	public void mostrarActualizarLibros (){
		actualizarLibros = new ActualizarLibros(this, manejadorEventos);
	}

	public void mostrarAgregarLibro (){
		agregarLibro = new AgregarLibro(this, manejadorEventos);
	}

	public void mostrarActualizarDatosCliente (){
		actualizarDatosCliente = new ActualizarDatosCliente(this, manejadorEventos);
	}

	public static void main (String[] args){
		new VentanaPrincipal();
	}
}
