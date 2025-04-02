package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel{
	private final Evento                evento;
	private final VentanaPrincipal      ventanaPrincipal;
	private       PanelLibros           panelLibros;
	private       PanelCarrito          panelCarrito;
	private       PanelPerfil           panelPerfil;
	private       PanelAgregarLibro     panelAgregarLibro; //Solo para los administradores
	private       PanelActualizarLibro  panelActualizarLibro; //Solo para los administradores
	private       PanelEliminarLibro    panelEliminarLibro; //Solo para los administradores
	private       PanelCrearCuentas     panelCrearCuentas; //Solo para los administradores
	private       PanelHistorialCompras panelHistorialCompras; //TODO
	private final JTabbedPane           panelPrincipal;

	public PantallaPrincipal (VentanaPrincipal ventana, Evento evento){
		this.evento           = evento;
		this.ventanaPrincipal = ventana;
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		inicializarPanelLibros(ventana);
		panelPrincipal.addTab("Lista de Libros", panelLibros);
		inicializarPanelCarrito();
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		inicializarPanelPerfil();
		panelPrincipal.addTab("Perfil", panelPerfil);

		add(panelPrincipal, BorderLayout.CENTER);
		ventana.add(this);
	}

	public void agregarPanelesSegunRol (String rol){
		if (rol.equals("ADMIN")){
			inicalizarPanelesAdministrador();
			panelPrincipal.addTab("Agregar Libro", panelAgregarLibro);
			panelPrincipal.addTab("Modificar Libro", panelActualizarLibro);
			panelPrincipal.addTab("Eliminar Libro", panelEliminarLibro);
			panelPrincipal.addTab("Crear Cuentas", panelCrearCuentas);
		}
		inicializarPanelHistorialCompras();
		panelPrincipal.addTab("Historial de Compras", panelHistorialCompras);
	}

	private void inicalizarPanelesAdministrador (){
		panelAgregarLibro    = new PanelAgregarLibro(evento);
		panelActualizarLibro = new PanelActualizarLibro(evento);
		panelEliminarLibro   = new PanelEliminarLibro(evento);
		panelCrearCuentas    = new PanelCrearCuentas(evento);
	}

	private void inicializarPanelLibros (VentanaPrincipal ventana){
		panelLibros = new PanelLibros(ventana, evento);
	}

	private void inicializarPanelCarrito (){
		panelCarrito = new PanelCarrito(ventanaPrincipal, evento);
	}

	private void inicializarPanelPerfil (){
		panelPerfil = new PanelPerfil(evento, this);
	}

	private void inicializarPanelHistorialCompras (){
		panelHistorialCompras = new PanelHistorialCompras(evento);
	}

	public PanelLibros getPanelLibros (){
		return panelLibros;
	}

	Object[] getDatosLibroNuevo (){
		return panelAgregarLibro.getDatosLibro();
	}

	void iniciarSesion (Object[] datosUsuario){
		panelPerfil.setDatosUsuario(datosUsuario);
	}

	PanelActualizarLibro getPanelActualizarLibro (){
		return panelActualizarLibro;
	}

	public PanelCarrito getPanelCarrito (){
		return panelCarrito;
	}
}