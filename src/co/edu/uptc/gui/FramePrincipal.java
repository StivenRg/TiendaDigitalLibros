package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class FramePrincipal extends JFrame{
	private EventosUsuario              eventosUsuario;
	private EventosCarrito              eventosCarrito;
	private EventosLibros               eventosLibros;
	private PanelLoginSignup            panelLoginSignup;
	private PanelCarrito                panelCarrito;
	private PantallaPrincipal           pantallaPrincipal;
	private PanelActualizarDatosCliente panelActualizarDatosCliente;
	private JPanel                      panelActual;

	public FramePrincipal (){
		inicializarFrame();
	}

	private void inicializarFrame (){
		setTitle("Tienda Digital de Libros");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mostrarInterfaz(Interfaz.LOGIN_SIGNUP_PROFILE);
		setVisible(true);
		setResizable(true);
		pack();
	}

	public void repintar (JPanel nuevoPanel){
		if (panelActual != null){
			remove(panelActual);
		}
		panelActual = nuevoPanel;
		add(panelActual, BorderLayout.CENTER);
		revalidate();
		repaint();
		pack();
	}

	public void mostrarInterfaz (Interfaz nombreInterfaz){
		eventosUsuario = new EventosUsuario(this);
		eventosCarrito = new EventosCarrito(this);
		eventosLibros  = new EventosLibros(this);
		switch (nombreInterfaz){
			case LOGIN_SIGNUP_PROFILE -> {
				panelLoginSignup = new PanelLoginSignup(this, eventosUsuario);
				repintar(panelLoginSignup);
			}
			case PANTALLA_PRINCIPAL -> {
				pantallaPrincipal = new PantallaPrincipal(this);
				repintar(pantallaPrincipal);
			}
			case CRUD_LIBRO -> {
				//panelActualizarDatosCliente = new PanelActualizarDatosCliente(this, eventosLibros);
			}
			default -> JOptionPane.showMessageDialog(this, "Interfaz no encontrada");
		}
	}

	public PanelLoginSignup getPanelLoginSignup (){
		return panelLoginSignup;
	}

	public String getRol (){
		return panelLoginSignup.getRol();
	}

	public enum Interfaz{
		PANTALLA_PRINCIPAL, CRUD_LIBRO, LOGIN_SIGNUP_PROFILE
	}

	public EventosUsuario getEventosUsuario (){
		return eventosUsuario;
	}

	public EventosCarrito getEventosCarrito (){
		return eventosCarrito;
	}

	public EventosLibros getEventosLibros (){
		return eventosLibros;
	}
}
