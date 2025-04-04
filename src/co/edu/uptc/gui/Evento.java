package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener{
	private final VentanaPrincipal ventana;

	public enum EVENTO{
		ACTUALIZAR_LIBRO,
		ACTUALIZAR_DATOS_CLIENTE,
		AGREGAR_LIBRO_AL_ARCHIVO,
		AGREGAR_LIBRO_AL_CARRITO,
		BUSCAR_LIBRO,
		ELIMINAR_LIBRO,
		INICIAR_SESION,
		LOGIN_SIGNUP,
		PAGAR_EFECTIVO,
		PAGAR_TARJETA,
		REGISTRAR
	}

	public Evento (VentanaPrincipal ventana){
		this.ventana = ventana;
	}

	@Override public void actionPerformed (ActionEvent e){
		String actionCommand = e.getActionCommand();
		EVENTO nombreEvento  = EVENTO.valueOf(actionCommand);
		switch (nombreEvento){
			case BUSCAR_LIBRO -> ventana.buscarLibro();
			case AGREGAR_LIBRO_AL_CARRITO -> ventana.agregarLibroCarrito();
			case AGREGAR_LIBRO_AL_ARCHIVO -> ventana.agregarLibroArchivo();
			//case ACTUALIZAR_DATOS_CLIENTE -> ventana.actualizarDatosCliente();
			case ACTUALIZAR_LIBRO -> ventana.actualizarLibro();
			//case ELIMINAR_LIBRO -> eliminarLibro();
			case INICIAR_SESION -> ventana.validarInicioSesion();
			case LOGIN_SIGNUP -> ventana.mostrarPanelLoginSignUp();
			//case PAGAR_EFECTIVO -> pagarEfectivo();
			//case PAGAR_TARJETA -> pagarTarjeta();
			case REGISTRAR -> ventana.validarRegistro();
		}
	}
}
