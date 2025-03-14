package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{
	private final ManejadorEventos       manejadorEventos;
	private       LoginSignup            fieldLoginSignup;
	private       Carrito                carrito;
	private       PantallaPrincipal      fieldPantallaPrincipal;
	private       ModificarLibros        modificarLibros;
	private       ActualizarLibros       actualizarLibros;
	private       AgregarLibro           agregarLibro;
	private       ActualizarDatosCliente actualizarDatosCliente;
	private       JComponent             panelActual;

	public VentanaPrincipal (){
		setTitle("Tienda Digital de Libros");
		setBackground(Color.black);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		manejadorEventos = new ManejadorEventos(this);
		mostrarInterfaz(Interfaz.PANTALLA_PRINCIPAL);
		setVisible(true);
		pack();
	}

	public static void main (String[] args){
		new VentanaPrincipal();
	}

	private void repintar (JComponent nuevoPanel){
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
			case CRUD_LIBRO -> repintar(new AgregarLibro(this, manejadorEventos));
			case PANTALLA_PRINCIPAL -> repintar(new PantallaPrincipal(this, manejadorEventos));
			case LOGIN_SIGNUP_PROFILE -> repintar(new LoginSignup(this, manejadorEventos));
		}
	}

	enum Interfaz{
		PANTALLA_PRINCIPAL, CRUD_LIBRO, LOGIN_SIGNUP_PROFILE
	}
}
