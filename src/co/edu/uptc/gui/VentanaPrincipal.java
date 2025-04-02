package co.edu.uptc.gui;

import co.edu.uptc.modelo.ROLES;
import co.edu.uptc.modelo.Tienda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class VentanaPrincipal extends JFrame{
	private final  Evento            evento;
	private final  Tienda            tienda;
	private static PanelLoginSignup  staticPanelLoginSignup;
	private        PantallaPrincipal pantallaPrincipal;
	public static  boolean           LOGIN_CORRECTO = false;

	public VentanaPrincipal (){
		evento = new Evento(this);
		tienda = new Tienda();
		inicializarFrame();
	}

	public Object[] obtenerDatosLibroCarrito (long ISBN){
		Object[] datosObtenidos = tienda.buscarLibro(ISBN);
		String   titulo         = (String) datosObtenidos[1];
		String   autores        = (String) datosObtenidos[2];
		double   precioUnitario = (double) datosObtenidos[7];
		int      cantidad       = 1;

		return new Object[]{ISBN, titulo, autores, precioUnitario, cantidad};
	}

	private void inicializarFrame (){
		setTitle("Tienda Digital de Libros");
		setLocationRelativeTo(null);

		//Esto para que no se cierre la ventana de golpe, sino que primero guarde y luego cierre
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override public void windowClosing (WindowEvent e){
				salirFormaSegura();
			}
		});
		this.pantallaPrincipal = new PantallaPrincipal(this, evento);
		setVisible(true);
		setResizable(true);
		setSize(1000, 600);
	}

	void validarInicioSesion (){
		String[] datosUsuario      = getDatosLogin();
		String   correoElectronico = datosUsuario[0];
		String   claveAcceso       = datosUsuario[1];
		if (tienda.validarUsuarioLogin(correoElectronico, claveAcceso)){
			pantallaPrincipal.iniciarSesion(tienda.obtenerUsuarioSeguro(correoElectronico));
			obtenerPanelLoginSignUp();
			LOGIN_CORRECTO = true;
		}else{
			JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String[] getDatosLogin (){
		System.out.println("Obteniendo Datos Login");//Temporal
		return obtenerPanelLoginSignUp().getDatosLogin();
	}

	private PanelLoginSignup obtenerPanelLoginSignUp (){
		return staticPanelLoginSignup;
	}

	static void setPanelgLoginSignup (PanelLoginSignup paramPanelLoginSignup){
		staticPanelLoginSignup = paramPanelLoginSignup;
	}

	void validarRegistro (){
		Object[] datosUsuario      = getDatosSignUp();
		String   correoElectronico = (String) datosUsuario[1];
		if (! tienda.usuarioExiste(correoElectronico)){
			String                 nombreCompleto = (String) datosUsuario[0];
			String                 direccion      = (String) datosUsuario[2];
			long                   telefono       = (long) datosUsuario[3];
			char[]                 claveAcceso    = (char[]) datosUsuario[4];
			int                    CID            = Tienda.obtenerNuevoCID();
			HashMap<Long, Integer> carrito        = pantallaPrincipal.getPanelCarrito().getCarritoDeComprasTemporal();

			Object[] datos = new Object[]{nombreCompleto, correoElectronico, direccion, telefono, claveAcceso, CID, carrito};
			tienda.registrarUsuario(datos);
			pantallaPrincipal.iniciarSesion(tienda.obtenerUsuarioSeguro(correoElectronico));
			LOGIN_CORRECTO = true;
		}
	}

	private Object[] getDatosSignUp (){
		return obtenerPanelLoginSignUp().getDatosRegistro();
	}

	Object[][] obtenerListaLibros (){
		return tienda.getDataVectorLibros();
	}

	void agregarLibroCarrito (){
		DefaultTableModel      tablaLibros      = pantallaPrincipal.getPanelLibros().getTableModel();
		HashMap<Long, Integer> carritoDeCompras = pantallaPrincipal.getPanelCarrito().getCarritoDeComprasTemporal();
		final int              columnaAgregar   = 10;
		for (int fila = 0; fila < tablaLibros.getRowCount(); fila++){
			Boolean valorAgregar = (Boolean) tablaLibros.getValueAt(fila, columnaAgregar);
			if (valorAgregar){
				Long ISBN = Long.parseLong(tablaLibros.getValueAt(fila, 0).toString());
				if (! carritoDeCompras.containsKey(ISBN)){
					carritoDeCompras.put(ISBN, 1);
					pantallaPrincipal.getPanelCarrito().agregarArticulo(ISBN);
				} else{
					pantallaPrincipal.getPanelCarrito().incrementarCantidad(ISBN);
				}
				tablaLibros.setValueAt(false, fila, columnaAgregar);
			}
		}
	}

	public static void guardarCarritoDeCompras (int CID, HashMap<Long, Integer> carritoDeCompras){
		Tienda.guardarCarritoDeCompras(CID, carritoDeCompras);
	}

	void agregarLibroArchivo (){
		if (tienda.agregarLibroArchivo(pantallaPrincipal.getDatosLibroNuevo())){
			JOptionPane.showMessageDialog(this, "Libro agregado correctamente", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(this, "Libro ya existe", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void salirFormaSegura (){
		//pantallaPrincipal.getPanelCarrito().actualizarCarritoArchivo();
		dispose();
		System.exit(0);
	}

	void mostrarPanelLoginSignUp (){
		new PanelLoginSignup(evento);
	}

	static ROLES obtenerTipoUsuario (String correoElectronico){
		return Tienda.obtenerTipoUsuario(correoElectronico);
	}

	void buscarLibro (){
		long     ISBN  = pantallaPrincipal.getPanelActualizarLibro().getISBN();
		Object[] datos = tienda.buscarLibro(ISBN);
		if (datos != null){
			pantallaPrincipal.getPanelActualizarLibro().setDatosLibro(datos);
		}
	}

	void actualizarLibro (){
		Object[] datos = pantallaPrincipal.getPanelActualizarLibro().getDatosLibro();
		if (tienda.actualizarLibro(datos)){
			JOptionPane.showMessageDialog(this, "Libro actualizado correctamente", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(this, "Libro no actualizado", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	double obtenerPrecioVentaTotal (DefaultTableModel model){
		return tienda.calcularSubTotalVenta(model);
	}

	double obtenerPrecioTotalProducto (double valorUnitario, int cantidad){
		return tienda.calcularPrecioVenta(valorUnitario, cantidad);
	}

	double obtenerPrecioImpuesto (double valorUnitario){
		return tienda.calcularValorImpuesto(valorUnitario);
	}
}
