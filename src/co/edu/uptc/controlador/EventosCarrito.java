package co.edu.uptc.controlador;

import co.edu.uptc.gui.FramePrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosCarrito implements ActionListener{
	private final FramePrincipal framePrincipal;
	private       String         RUTA_CARRITOS = "persistencia/CARRITOS.json";

	public EventosCarrito (FramePrincipal framePrincipal){
		this.framePrincipal = framePrincipal;
	}

	@Override public void actionPerformed (ActionEvent e){

	}
}
