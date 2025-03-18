package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class FramePrincipal extends JFrame{
	private EventosUsuario         eventosUsuario;
	private EventosCarrito         eventosCarrito;
	private EventosLibros          eventosLibros;
	private LoginSignup            panelLoginSignup;
	private PanelCarrito           panelCarrito;
	private PantallaPrincipal      pantallaPrincipal;
	private ActualizarDatosCliente panelActualizarDatosCliente;
	private JPanel                 panelActual;

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
		switch (nombreInterfaz){
			case LOGIN_SIGNUP_PROFILE -> {
				eventosUsuario   = new EventosUsuario(this);
				panelLoginSignup = new LoginSignup(this, eventosUsuario);
				repintar(panelLoginSignup);
			}
			case PANTALLA_PRINCIPAL -> {
				eventosCarrito    = new EventosCarrito(this);
				eventosLibros     = new EventosLibros(this);
				pantallaPrincipal = new PantallaPrincipal(this);
				repintar(pantallaPrincipal);
			}
			case CRUD_LIBRO -> {
				//panelActualizarDatosCliente = new ActualizarDatosCliente(this, eventosLibros);
			}
			default -> JOptionPane.showMessageDialog(this, "Interfaz no encontrada");
		}
	}

	public LoginSignup getPanelLoginSignup (){
		return panelLoginSignup;
	}

	public enum Interfaz{
		PANTALLA_PRINCIPAL, CRUD_LIBRO, LOGIN_SIGNUP_PROFILE
	}
}
