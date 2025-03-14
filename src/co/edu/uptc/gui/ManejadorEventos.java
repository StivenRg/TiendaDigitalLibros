package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManejadorEventos implements ActionListener{
	private FramePrincipal ventana;

	public ManejadorEventos (FramePrincipal ventana){
		this.ventana = ventana;
	}

	public void iniciarSesion (){
		JOptionPane.showMessageDialog(ventana, "Boton de Iniciar Sesión");
		if (validarDatosLogin()){
			ventana.mostrarInterfaz(FramePrincipal.Interfaz.PANTALLA_PRINCIPAL);
		}
	}

	public void registrarUsuario (){
		JOptionPane.showMessageDialog(ventana, "Boton de Registro");
		if (validarDatosRegistro()){
			ventana.mostrarInterfaz(FramePrincipal.Interfaz.PANTALLA_PRINCIPAL);
		}
	}

	public void mostrarListaLibros (){
		JOptionPane.showMessageDialog(ventana, "Boton de Ver Libros");
	}

	public void mostrarComprarLibros (){
		JOptionPane.showMessageDialog(ventana, "Boton de Comprar Libros");
	}

	public void mostrarCarrito (){
		JOptionPane.showMessageDialog(ventana, "Boton de Ver PanelCarrito");
	}

	public void agregarUnidad (){
		JOptionPane.showMessageDialog(ventana, "Boton de Agregar Unidad");
	}

	public void removerUnidad (){
		JOptionPane.showMessageDialog(ventana, "Boton de Remover Unidad");
	}

	public void eliminarProducto (){
		JOptionPane.showMessageDialog(ventana, "Boton de Eliminar Producto");
	}

	public void pagarEfectivo (){
		JOptionPane.showMessageDialog(ventana, "Boton de Pagar Efectivo");
	}

	public void pagarTarjeta (){
		JOptionPane.showMessageDialog(ventana, "Boton de Pagar Tarjeta");
	}

	public void mostrarDatosCliente (){
		JOptionPane.showMessageDialog(ventana, "Boton de Ver Datos Cliente");
	}

	public void actualizarDatosCliente (){
		JOptionPane.showMessageDialog(ventana, "Boton de Actualizar Datos Cliente");
	}

	public void confirmarActualizacionPerfil (){
		JOptionPane.showMessageDialog(ventana, "Boton de Confirmar Actualización Perfil");
	}

	public void agregarLibro (){
		JOptionPane.showMessageDialog(ventana, "Boton de Agregar Libro");
		//Lógica agregada temporalmente para validar la funcionalidad
		StringBuilder stringBuilder = new StringBuilder("Libros Agregados\n");
		for (Object locLibro : PantallaPrincipal.obtenerLibrosSelectos()){
			stringBuilder.append(locLibro).append("\n");
		}
		JOptionPane.showMessageDialog(ventana, stringBuilder.toString());
	}

	public void modificarLibro (){
		JOptionPane.showMessageDialog(ventana, "Boton de Modificar Libro");
	}

	public void eliminarLibro (){
		JOptionPane.showMessageDialog(ventana, "Boton de Eliminar Libro");
	}

	@Override public void actionPerformed (ActionEvent e){
		switch (e.getActionCommand()){
			case "actualizarDatosCliente" -> actualizarDatosCliente();
			case "agregarLibro" -> agregarLibro();
			case "agregarUnidad" -> agregarUnidad();
			case "confirmarActualizacionPerfil" -> confirmarActualizacionPerfil();
			case "eliminarLibro" -> eliminarLibro();
			case "eliminarProducto" -> eliminarProducto();
			case "iniciarSesion" -> iniciarSesion();
			case "modificarLibro" -> modificarLibro();
			case "mostrarCarrito" -> mostrarCarrito();
			case "mostrarComprarLibros" -> mostrarComprarLibros();
			case "mostrarDatosCliente" -> mostrarDatosCliente();
			case "mostrarListaLibros" -> mostrarListaLibros();
			case "pagarEfectivo" -> pagarEfectivo();
			case "pagarTarjeta" -> pagarTarjeta();
			case "registrarUsuario" -> registrarUsuario();
			case "removerUnidad" -> removerUnidad();
			default -> JOptionPane.showMessageDialog(ventana, "Boton no encontrado");
		}
	}

	private boolean validarDatosRegistro (){
		return true;
	}

	private boolean validarDatosLogin (){
		return true;
	}
}