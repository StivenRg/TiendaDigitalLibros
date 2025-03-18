package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;
import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel{
	private final JTabbedPane    panelPrincipal;
	private       JPanel         panelLibros;
	private       JPanel         panelCarrito;
	private       JPanel         panelPerfil;
	private       JPanel         panelCrearCuentas; //Solo para los administradores
	private       EventosUsuario eventosUsuario;
	private       EventosCarrito eventosCarrito;
	private       EventosLibros  eventosLibros;

	public PantallaPrincipal (FramePrincipal ventana){
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		inicializarPanelLibros(eventosLibros);
		panelPrincipal.addTab("Lista de Libros", panelLibros);
		inicializarPanelCarrito(eventosCarrito);
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		inicializarPanelPerfil(eventosUsuario);
		panelPrincipal.addTab("Perfil", panelPerfil);
		add(panelPrincipal, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
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

	private void inicializarPanelCrearCuentas (EventosUsuario eventos){
		eventosUsuario    = eventos;
		panelCrearCuentas = new PanelCrearCuentas(eventos);
	}
}