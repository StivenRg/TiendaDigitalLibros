package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;

public class FramePrincipal extends JFrame{
	private EventosUsuario eventosUsuario;
	private EventosCarrito eventosCarrito;
	private EventosLibros  eventosLibros;

	public FramePrincipal (){
		eventosCarrito = new EventosCarrito(this);
		eventosUsuario = new EventosUsuario(this);
		eventosLibros  = new EventosLibros(this);
		inicializarFrame();
	}

	private void inicializarFrame (){
		setTitle("Tienda Digital de Libros");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new PantallaPrincipal(this, eventosCarrito, eventosLibros, eventosUsuario);
		setVisible(true);
		setResizable(true);
		setSize(1000, 600);
	}
}
