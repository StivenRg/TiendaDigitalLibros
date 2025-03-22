package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel{
	private final JTabbedPane    panelPrincipal;
	private       EventosCarrito eventosCarrito;
	private       EventosLibros  eventosLibros;
	private       EventosUsuario eventosUsuario;
	private       JPanel         panelAgregarLibro;
	private       JPanel         panelCarrito;
	private       JPanel         panelCrearCuentas; //Solo para los administradores
	private       JPanel         panelEliminarLibro;
	private       JPanel         panelLibros;
	private       JPanel         panelModificarLibro;
	private       JPanel         panelPerfil;

	public PantallaPrincipal (FramePrincipal ventana){
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		eventosUsuario = ventana.getEventosUsuario();
		eventosCarrito = ventana.getEventosCarrito();
		eventosLibros  = ventana.getEventosLibros();

		inicializarPanelLibros(eventosLibros);
		panelPrincipal.addTab("Lista de Libros", panelLibros);
		inicializarPanelCarrito(eventosCarrito);
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		inicializarPanelPerfil(eventosUsuario);
		panelPrincipal.addTab("Perfil", panelPerfil);
		agregarPanelesSegunRol(ventana.getPanelLoginSignup());
		add(panelPrincipal, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
		ventana.pack();
	}

	private void agregarPanelesSegunRol (PanelLoginSignup panelLoginSignup){
		if (! eventosUsuario.isLoginCorrecto()) return;
		if (panelLoginSignup.getRol().equals("ADMIN")){
			inicalizarPanelesAdministrador();
			panelPrincipal.addTab("Agregar Libro", panelAgregarLibro);
			panelPrincipal.addTab("Modificar Libro", panelModificarLibro);
			panelPrincipal.addTab("Eliminar Libro", panelEliminarLibro);
			panelPrincipal.addTab("Crear Cuentas", panelCrearCuentas);
		}
	}

	private void inicalizarPanelesAdministrador (){
		panelAgregarLibro   = new PanelAgregarLibro(eventosLibros);
		panelModificarLibro = new PanelActualizarLibro(eventosLibros);
		panelEliminarLibro  = new PanelEliminarLibro(eventosLibros);
		panelCrearCuentas   = new PanelCrearCuentas(eventosUsuario);
	}

	private void inicializarPanelLibros (EventosLibros eventos){
		eventosLibros = eventos;
		panelLibros   = new PanelLibros(eventos);
	}

	private void inicializarPanelCarrito (EventosCarrito eventos){
		eventosCarrito = eventos;
		panelCarrito   = new PanelCarrito(eventos);
	}

	private void inicializarPanelPerfil (EventosUsuario eventos){
		eventosUsuario = eventos;
		panelPerfil    = new PanelPerfil(eventos);
	}
}