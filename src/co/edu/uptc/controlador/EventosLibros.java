package co.edu.uptc.controlador;

import co.edu.uptc.gui.FramePrincipal;
import co.edu.uptc.gui.PanelLibros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosLibros implements ActionListener{
	private FramePrincipal framePrincipal;
	private PanelLibros    panelLibros;
	private final String   RUTA_LIBROS = "/co/edu/uptc/persistencia/LIBROS.json";

	public EventosLibros (FramePrincipal framePrincipal){
		this.framePrincipal = framePrincipal;
	}

	@Override public void actionPerformed (ActionEvent e){
	}

	public void agregarLibro (
			long ISBN,
			String titulo,
			String autor,
			int anioPublicacion,
			String genero,
			String editorial,
			int numeroPaginas,
			double precioVenta,
			int cantidadDisponible,
			String Formato
	){

	}
}
