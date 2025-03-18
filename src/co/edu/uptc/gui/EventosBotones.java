package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosBotones implements ActionListener{
	private final FramePrincipal framePrincipal;

	public EventosBotones (FramePrincipal ventana){
		framePrincipal = ventana;
	}

	@Override public void actionPerformed (ActionEvent e){
		switch (e.getActionCommand()){
			case "agregarLibro" -> JOptionPane.showMessageDialog(framePrincipal, "Boton de Agregar Libro");
			default -> JOptionPane.showMessageDialog(framePrincipal, "Boton no encontrado");
		}
	}
}
