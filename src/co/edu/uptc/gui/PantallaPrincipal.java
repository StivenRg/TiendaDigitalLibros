package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel{
	private       Evento                evento;
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
		this.evento = evento;
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		inicializarPanelLibros(ventana, evento);
		panelPrincipal.addTab("Lista de Libros", panelLibros);
		inicializarPanelCarrito(evento);
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		inicializarPanelPerfil(evento);
		panelPrincipal.addTab("Perfil", panelPerfil);

		panelPrincipal.addChangeListener(e -> {
			if (panelPrincipal.getSelectedIndex() == 2){
				if (evento.isLoginCorrecto()){
					inicializarPanelPerfil(evento);
					agregarPanelesSegunRol(evento.getRol());
				}else{
					//Solicitar inicio de sesion
					new DialogLoginSignup(evento, ventana);
				}
			}
		});

		add(panelPrincipal, BorderLayout.CENTER);
		this.getRootPane().add(this);
	}

	private void agregarPanelesSegunRol (String rol){
		if (rol.equals("ADMIN")){
			inicalizarPanelesAdministrador();
			panelPrincipal.addTab("Agregar Libro", panelAgregarLibro);
			panelPrincipal.addTab("Modificar Libro", panelActualizarLibro);
			panelPrincipal.addTab("Eliminar Libro", panelEliminarLibro);
			panelPrincipal.addTab("Crear Cuentas", panelCrearCuentas);
		}
		inicializarPanelHistorialCompras(evento);
		panelPrincipal.addTab("Historial de Compras", panelHistorialCompras);
	}

	private void inicalizarPanelesAdministrador (){
		panelAgregarLibro    = new PanelAgregarLibro(evento);
		panelActualizarLibro = new PanelActualizarLibro(evento);
		panelEliminarLibro   = new PanelEliminarLibro(evento);
		panelCrearCuentas    = new PanelCrearCuentas(evento);
	}

	private void inicializarPanelLibros (VentanaPrincipal ventana, Evento evento){
		panelLibros = new PanelLibros(ventana, evento);
	}

	private void inicializarPanelCarrito (Evento evento){
		panelCarrito = new PanelCarrito(evento);
	}

	private void inicializarPanelPerfil (Evento evento){
		this.evento = evento;
		panelPerfil = new PanelPerfil(this.evento);
	}

	private void inicializarPanelHistorialCompras (Evento evento){
		this.evento           = evento;
		panelHistorialCompras = new PanelHistorialCompras(evento);
	}

	public void iniciarSesion (Object[] datosUsuario){
		panelPerfil.iniciarSesion(datosUsuario);
	}

	public PanelLibros getPanelLibros (){
		return panelLibros;
	}
}