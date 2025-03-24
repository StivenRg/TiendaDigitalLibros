package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosUsuario;
import co.edu.uptc.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class PanelHistorialCompras extends JPanel{
	private Usuario usuario;

	public PanelHistorialCompras (EventosUsuario eventosUsuario){
		//TODO
		setLayout(new GridLayout(0, 3));
	}
}
