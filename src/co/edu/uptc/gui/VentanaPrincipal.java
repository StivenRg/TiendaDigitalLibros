package co.edu.uptc.gui;

import co.edu.uptc.modelo.Tienda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class VentanaPrincipal extends JFrame{
	private final Evento            evento;
	private final Tienda            tienda;
	private       DialogLoginSignup dialogLoginSignup;
	private       PantallaPrincipal pantallaPrincipal;

	public VentanaPrincipal (){
		evento = new Evento(this);
		tienda = new Tienda();
		inicializarFrame();
	}

	private void inicializarFrame (){
		setTitle("Tienda Digital de Libros");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pantallaPrincipal = new PantallaPrincipal(this, evento);
		setVisible(true);
		setResizable(true);
		setSize(1000, 600);
	}

	void validarInicioSesion (){
		String[] datosUsuario      = getDatosLogin();
		String   correoElectronico = datosUsuario[0];
		String   claveAcceso       = datosUsuario[1];
		if (tienda.validarUsusarioLogin(correoElectronico, claveAcceso)){
			obtenerPanelLoginSignUp().dispose();
			pantallaPrincipal.iniciarSesion(tienda.obtenerUsuarioSeguro(correoElectronico));
		}
	}

	private String[] getDatosLogin (){
		return obtenerPanelLoginSignUp().getDatosLogin();
	}

	void agregarLibro (Object[] libro){
		tienda.agregarLibro(libro);
	}

	private DialogLoginSignup obtenerPanelLoginSignUp (){
		return dialogLoginSignup;
	}

	void setDialogLoginSignup (DialogLoginSignup dialogLoginSignup){
		this.dialogLoginSignup = dialogLoginSignup;
	}

	void validarRegistro (){
		String[] datosTextoUsuario = getDatosSignUp();
		String   correoElectronico = datosTextoUsuario[1];
		if (tienda.validarDatosRegistro(correoElectronico)){
			HashMap<Long, Integer> carritoDeCompras = PanelCarrito.getCarritoDeComprasTemporal();
			Object[]               datosUsuario     = new Object[7];
			datosUsuario[0] = datosTextoUsuario[0];
			datosUsuario[1] = datosTextoUsuario[1];
			datosUsuario[2] = datosTextoUsuario[2];
			datosUsuario[3] = datosTextoUsuario[3];
			datosUsuario[4] = datosTextoUsuario[4];
			datosUsuario[5] = Tienda.getCID_Index();
			datosUsuario[6] = carritoDeCompras;

			tienda.registrarUsuario(datosUsuario);
			obtenerPanelLoginSignUp().dispose();
			pantallaPrincipal.iniciarSesion(tienda.obtenerUsuarioSeguro(correoElectronico));
		}
	}

	private String[] getDatosSignUp (){
		return obtenerPanelLoginSignUp().getDatosRegistro();
	}

	Object[][] obtenerListaLibros (){
		return tienda.getDataVectorLibros();
	}

	void agregarLibroCarrito (){
		DefaultTableModel      tablaLibros      = pantallaPrincipal.getPanelLibros().getTableModel();
		HashMap<Long, Integer> carritoDeCompras = PanelCarrito.getCarritoDeComprasTemporal();
		for (int fila = 0; fila < tablaLibros.getRowCount(); fila++){
			if (tablaLibros.getValueAt(fila, 10).equals(true)){
				Long ISBN = Long.parseLong(tablaLibros.getValueAt(fila, 0).toString());
				if (! carritoDeCompras.containsKey(ISBN)){
					carritoDeCompras.put(ISBN, 1);
				}
			}
		}
	}
}
