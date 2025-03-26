package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosUsuario;
import co.edu.uptc.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelPerfil extends JPanel implements InterfacePerfilListener{
	private final EventosUsuario         eventosUsuario;
	private       Usuario                usuario;
	private       String[]               rolesDeUsuario    = {"REGULAR", "PREMIUM", "ADMIN"};
	private       String[]               nombreAtributos   = {"Nombre Completo", "Correo Electronico", "Direccion", "Teléfono", "Tipo de Usuario", "Contraseña"
	};
	private       String                 nombreCompleto    = "";
	private       String                 correoElectronico = "";
	private       String                 direccion         = "";
	private       long                   telefono          = 3000000000L;
	private       String                 tipoUsuario       = "REGULAR";
	private       HashMap<Long, Integer> carritoDeCompras;
	private       char[]                 claveAcceso;

	public PanelPerfil (EventosUsuario eventosUsuario){
		this.eventosUsuario = eventosUsuario;
		eventosUsuario.setPanelPerfil(this);
		inicializarPanelPerfil();
	}

	private void refrescarDatosPerfil (Usuario datosUsuario){
		//Datos de Usuario
		usuario           = datosUsuario;
		nombreCompleto    = usuario.getNombreCompleto();
		correoElectronico = usuario.getCorreoElectronico();
		direccion         = usuario.getDireccionEnvio();
		telefono          = usuario.getTelefonoContacto();
		tipoUsuario       = usuario.getTipoCliente();
		inicializarPanelDatosUsuario();
		inicializarPanelFooter();

		String[]          tiposUsuario        = {"REGULAR", "PREMIUM", "ADMIN"};
		JComboBox<String> comboBoxTipoUsuario = new JComboBox<>(tiposUsuario);
		comboBoxTipoUsuario.setSelectedItem(usuario.getTipoCliente());
		JPasswordField boxContrasena = new JPasswordField();

		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.CENTER);
		checkBoxMostrarContrasena.addActionListener(e -> {
			if (checkBoxMostrarContrasena.isSelected()){
				boxContrasena.setEchoChar((char) 0);
			}else{
				boxContrasena.setEchoChar('•');
			}
		});
	}

	private void inicializarPanelPerfil (){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void inicializarPanelDatosUsuario (){
		//Banners de Datos
		//Banner de Nombre Completo
		JPanel panelNombreCompleto = new JPanel(new GridLayout(1, 2));
		panelNombreCompleto.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelNombreCompleto = new JLabel(nombreAtributos[0]);
		labelNombreCompleto.setHorizontalAlignment(JLabel.CENTER);
		labelNombreCompleto.setPreferredSize(new Dimension(100, 25));
		JTextField txNombreCompleto = new JTextField("");
		txNombreCompleto.setPreferredSize(new Dimension(160, 25));
		panelNombreCompleto.add(labelNombreCompleto);
		panelNombreCompleto.add(txNombreCompleto);
		add(panelNombreCompleto);

		//Banner de Correo Electronico
		JPanel panelCorreoElectronico = new JPanel(new GridLayout(1, 2));
		panelCorreoElectronico.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelCorreoElectronico = new JLabel(nombreAtributos[1]);
		labelCorreoElectronico.setPreferredSize(new Dimension(100, 25));
		labelCorreoElectronico.setHorizontalAlignment(JLabel.CENTER);
		JTextField txCorreoElectronico = new JTextField("");
		txCorreoElectronico.setPreferredSize(new Dimension(160, 25));
		panelCorreoElectronico.add(labelCorreoElectronico);
		panelCorreoElectronico.add(txCorreoElectronico);
		add(panelCorreoElectronico);

		//Banner de Direccion
		JPanel panelDireccion = new JPanel(new GridLayout(1, 2));
		panelDireccion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelDireccion = new JLabel(nombreAtributos[2]);
		labelDireccion.setPreferredSize(new Dimension(100, 25));
		labelDireccion.setHorizontalAlignment(JLabel.CENTER);
		JTextField txDireccion = new JTextField("");
		txDireccion.setPreferredSize(new Dimension(160, 25));
		panelDireccion.add(labelDireccion);
		panelDireccion.add(txDireccion);
		add(panelDireccion);

		//Banner de Teléfono
		JPanel panelTelefono = new JPanel(new GridLayout(1, 2));
		panelTelefono.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelTelefono = new JLabel(nombreAtributos[3]);
		labelTelefono.setPreferredSize(new Dimension(100, 25));
		labelTelefono.setHorizontalAlignment(JLabel.CENTER);
		JTextField txTelefono = new JTextField("");
		txTelefono.setPreferredSize(new Dimension(160, 25));
		panelTelefono.add(labelTelefono);
		panelTelefono.add(txTelefono);
		add(panelTelefono);

		//Banner de Tipo de Usuario
		JPanel panelTipoUsuario = new JPanel(new GridLayout(1, 2));
		panelTipoUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelTipoUsuario = new JLabel(nombreAtributos[4]);
		labelTipoUsuario.setPreferredSize(new Dimension(100, 25));
		labelTipoUsuario.setHorizontalAlignment(JLabel.CENTER);
		JComboBox<String> comboBoxTipoUsuario = new JComboBox<>(rolesDeUsuario);
		comboBoxTipoUsuario.setSelectedItem("");
		panelTipoUsuario.add(labelTipoUsuario);
		panelTipoUsuario.add(comboBoxTipoUsuario);
		add(panelTipoUsuario);

		//Banner de Contraseña
		JPanel panelContrasena = new JPanel(new GridLayout(1, 2));
		panelContrasena.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelContrasena = new JLabel("*" + nombreAtributos[5]);
		labelContrasena.setPreferredSize(new Dimension(100, 25));
		labelContrasena.setHorizontalAlignment(JLabel.CENTER);
		JPasswordField pswdField = new JPasswordField("");
		pswdField.setPreferredSize(new Dimension(160, 25));
		panelContrasena.add(labelContrasena);
		panelContrasena.add(pswdField);
		add(panelContrasena);
	}

	private void inicializarPanelFooter (){
		JPanel panelFooter = new JPanel(new GridLayout(2, 1));

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.setActionCommand("actualizarDatosCliente");
		botonGuardar.addActionListener(eventosUsuario);
		botonGuardar.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		JButton botonCambiarContrasena = new JButton("Cambiar Contraseña");
		botonCambiarContrasena.setActionCommand("cambiarContraseña");
		botonCambiarContrasena.addActionListener(eventosUsuario);
		botonCambiarContrasena.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		panelFooter.add(botonGuardar);
		panelFooter.add(botonCambiarContrasena);
		add(panelFooter);
	}

	@Override public void onRegistroExitoso (Object[] datosUsuario){
		Usuario usuario = new Usuario((String) datosUsuario[0],
		                              (String) datosUsuario[1],
		                              (String) datosUsuario[2],
		                              (long) datosUsuario[3],
		                              (String) datosUsuario[4],
		                              PanelCarrito.getCarritoDeComprasTemporal()
		);
		refrescarDatosPerfil(usuario);
	}

	@Override public void onSesionIniciada (Object[] datosUsuario){
		Usuario usuario = eventosUsuario.getDatosUsuario();
		nombreCompleto    = usuario.getNombreCompleto();
		correoElectronico = usuario.getCorreoElectronico();
		direccion         = usuario.getDireccionEnvio();
		telefono          = usuario.getTelefonoContacto();
		tipoUsuario       = usuario.getTipoCliente();
		carritoDeCompras  = usuario.getCarritoDeCompras();
		refrescarDatosPerfil(usuario);
	}

	public Object[] getDatosActualizados (){
		return new Object[]{nombreCompleto, correoElectronico, direccion, telefono, claveAcceso
		};
	}
}
