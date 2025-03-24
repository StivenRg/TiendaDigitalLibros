package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel{
	private       EventosCarrito eventosCarrito;
	private       EventosLibros  eventosLibros;
	private       EventosUsuario eventosUsuario;
	private       JPanel         panelAgregarLibro; //Solo para los administradores
	private       JPanel         panelCarrito;
	private       JPanel         panelCrearCuentas; //Solo para los administradores
	private       JPanel         panelEliminarLibro; //Solo para los administradores
	private       JPanel         panelHistorialCompras;
	private       JPanel         panelLibros;
	private       JDialog        dialogLoginSignup;
	private       JPanel         panelModificarLibro; //Solo para los administradores
	private       PanelPerfil    panelPerfil;
	private final JTabbedPane    panelPrincipal;
	private       FramePrincipal ventana;

	public PantallaPrincipal (FramePrincipal ventana, EventosCarrito eventosCarrito, EventosLibros eventosLibros, EventosUsuario eventosUsuario){
		this.ventana = ventana;
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		this.eventosUsuario = eventosUsuario;
		this.eventosCarrito = eventosCarrito;
		this.eventosLibros  = eventosLibros;

		inicializarPanelLibros(eventosLibros);
		panelPrincipal.addTab("Lista de Libros", panelLibros);
		inicializarPanelCarrito(eventosCarrito);
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		inicializarPanelPerfil(eventosUsuario);
		panelPrincipal.addTab("Perfil", panelPerfil);

		panelPrincipal.addChangeListener(e -> {
			if (panelPrincipal.getSelectedIndex() == 2){
				if (eventosUsuario.isLoginCorrecto()){
					inicializarPanelPerfil(eventosUsuario);
					agregarPanelesSegunRol(eventosUsuario.getRol());
				}else{
					//Solicitar inicio de sesion
					dialogLoginSignup = new DialogLoginSignup(panelPerfil, eventosUsuario);
				}
			}
		});

		add(panelPrincipal, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
		ventana.setSize(1000, 600);
	}

	private void agregarPanelesSegunRol (String rol){
		if (rol.equals("ADMIN")){
			inicalizarPanelesAdministrador();
			panelPrincipal.addTab("Agregar Libro", panelAgregarLibro);
			panelPrincipal.addTab("Modificar Libro", panelModificarLibro);
			panelPrincipal.addTab("Eliminar Libro", panelEliminarLibro);
			panelPrincipal.addTab("Crear Cuentas", panelCrearCuentas);
		}
		inicializarPanelHistorialCompras(eventosUsuario);
		panelPrincipal.addTab("Historial de Compras", panelHistorialCompras);
	}

	private void inicalizarPanelesAdministrador (){
		panelAgregarLibro   = new PanelAgregarLibro(eventosLibros);
		panelModificarLibro = new PanelActualizarLibro(eventosLibros);
		panelEliminarLibro  = new PanelEliminarLibro(eventosLibros);
		panelCrearCuentas   = new PanelCrearCuentas(eventosUsuario);
	}

	private void inicializarPanelLibros (EventosLibros eventosLibros){
		this.eventosLibros = eventosLibros;
		panelLibros        = new PanelLibros(eventosLibros);
	}

	private void inicializarPanelCarrito (EventosCarrito eventos){
		eventosCarrito = eventos;
		panelCarrito   = new PanelCarrito(eventos);
	}

	private void inicializarPanelPerfil (EventosUsuario eventos){
		eventosUsuario = eventos;
		panelPerfil    = new PanelPerfil(eventosUsuario);
	}

	private void inicializarPanelHistorialCompras (EventosUsuario eventos){
		eventosUsuario        = eventos;
		panelHistorialCompras = new PanelHistorialCompras(eventos);
	}
}