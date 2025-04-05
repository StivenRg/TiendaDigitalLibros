package co.edu.uptc.gui;

import co.edu.uptc.modelo.ROLES;

import javax.swing.*;
import java.awt.*;

public class PanelPerfil extends JPanel{
	private final Evento            evento;
	private final PantallaPrincipal pantallaPrincipal;
	private       Object[]          datosUsuario           = new Object[7];
	private       String            nombreCompleto         = "";
	private final JTextField        boxNombreCompleto      = new JTextField();
	private       String            correoElectronico      = "";
	private final JTextField        boxCorreo              = new JTextField();
	private       String            direccion              = "";
	private final JTextField        boxDireccion           = new JTextField();
	private       long              telefono               = 0;
	private final JTextField        boxTelefono            = new JTextField();
	private       ROLES             tipoUsuario            = ROLES.REGULAR;
	private final JLabel            labelTipoUsuarioActual = new JLabel(tipoUsuario.name());
	private       JPasswordField    boxContrasena          = new JPasswordField();
	private final JLabel            mensajeDeError         = new JLabel();
	private final String[]          NOMBRE_ATRIBUTOS       = {"Nombre Completo", "Correo Electronico", "Dirección", "Teléfono", "Tipo de Usuario", "Contraseña"
	};

	public PanelPerfil (PantallaPrincipal pantallaPrincipal, Evento evento){
		this.evento            = evento;
		this.pantallaPrincipal = pantallaPrincipal;
		inicializarPanelPerfil();
	}

	private void inicializarPanelPerfil (){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		refrescarDatosPerfil(datosUsuario);
	}

	private void refrescarDatosPerfil (Object[] datosUsuario){
		if (datosUsuario.length < 5 || ! VentanaPrincipal.LOGIN_CORRECTO){
			rellenarDatosVacios();
			return;
		}

		//Datos de Usuario
		nombreCompleto    = (String) datosUsuario[0];
		correoElectronico = (String) datosUsuario[1];
		direccion         = (String) datosUsuario[2];
		telefono          = (long) datosUsuario[3];
		tipoUsuario       = VentanaPrincipal.obtenerTipoUsuario(correoElectronico);

		inicializarPanelDatosUsuario();
		inicializarPanelFooter();

		revalidate();
		repaint();
	}

	private void rellenarDatosVacios (){
		inicializarPanelDatosUsuario();
		inicializarPanelFooter();
	}

	private void inicializarPanelDatosUsuario (){
		if (VentanaPrincipal.LOGIN_CORRECTO){
			removeAll();
		}
		//Banners de Datos
		//Banner de Nombre Completo
		JPanel panelNombreCompleto = new JPanel(new GridLayout(1, 2));
		panelNombreCompleto.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelNombreCompleto = new JLabel(NOMBRE_ATRIBUTOS[0]);
		labelNombreCompleto.setHorizontalAlignment(JLabel.CENTER);
		labelNombreCompleto.setPreferredSize(new Dimension(100, 25));
		boxNombreCompleto.setText(nombreCompleto);
		boxNombreCompleto.setPreferredSize(new Dimension(160, 25));
		panelNombreCompleto.add(labelNombreCompleto);
		panelNombreCompleto.add(boxNombreCompleto);
		add(panelNombreCompleto);

		//Banner de Correo Electronico
		JPanel panelCorreoElectronico = new JPanel(new GridLayout(1, 2));
		panelCorreoElectronico.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelCorreoElectronico = new JLabel(NOMBRE_ATRIBUTOS[1]);
		labelCorreoElectronico.setPreferredSize(new Dimension(100, 25));
		labelCorreoElectronico.setHorizontalAlignment(JLabel.CENTER);
		boxCorreo.setText(correoElectronico);
		boxCorreo.setPreferredSize(new Dimension(160, 25));
		panelCorreoElectronico.add(labelCorreoElectronico);
		panelCorreoElectronico.add(boxCorreo);
		add(panelCorreoElectronico);

		//Banner de Direccion
		JPanel panelDireccion = new JPanel(new GridLayout(1, 2));
		panelDireccion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelDireccion = new JLabel(NOMBRE_ATRIBUTOS[2]);
		labelDireccion.setPreferredSize(new Dimension(100, 25));
		labelDireccion.setHorizontalAlignment(JLabel.CENTER);
		boxDireccion.setText(direccion);
		boxDireccion.setPreferredSize(new Dimension(160, 25));
		panelDireccion.add(labelDireccion);
		panelDireccion.add(boxDireccion);
		add(panelDireccion);

		//Banner de Teléfono
		JPanel panelTelefono = new JPanel(new GridLayout(1, 2));
		panelTelefono.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelTelefono = new JLabel(NOMBRE_ATRIBUTOS[3]);
		labelTelefono.setPreferredSize(new Dimension(100, 25));
		labelTelefono.setHorizontalAlignment(JLabel.CENTER);
		boxTelefono.setText(String.valueOf(telefono));
		boxTelefono.setPreferredSize(new Dimension(160, 25));
		panelTelefono.add(labelTelefono);
		panelTelefono.add(boxTelefono);
		add(panelTelefono);

		//Banner de Tipo de Usuario
		JPanel panelTipoUsuario = new JPanel(new GridLayout(1, 2));
		panelTipoUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelTipoUsuario = new JLabel(NOMBRE_ATRIBUTOS[4]);
		labelTipoUsuario.setPreferredSize(new Dimension(100, 25));
		labelTipoUsuario.setHorizontalAlignment(JLabel.CENTER);
		labelTipoUsuarioActual.setText(tipoUsuario.name());
		panelTipoUsuario.add(labelTipoUsuario);
		panelTipoUsuario.add(labelTipoUsuarioActual);
		add(panelTipoUsuario);

		//Banner de Contraseña
		JPanel panelContrasena = new JPanel(new GridLayout(2, 2));
		panelContrasena.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel labelContrasena = new JLabel("*" + NOMBRE_ATRIBUTOS[5]);
		labelContrasena.setPreferredSize(new Dimension(100, 25));
		labelContrasena.setHorizontalAlignment(JLabel.CENTER);
		boxContrasena = new JPasswordField();
		boxContrasena.setPreferredSize(new Dimension(160, 25));
		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.CENTER);
		checkBoxMostrarContrasena.addActionListener(_ -> {
			if (checkBoxMostrarContrasena.isSelected()){
				boxContrasena.setEchoChar((char) 0);
			}else{
				boxContrasena.setEchoChar('•');
			}
		});
		panelContrasena.add(labelContrasena);
		panelContrasena.add(boxContrasena);
		panelContrasena.add(new JLabel());
		panelContrasena.add(checkBoxMostrarContrasena);
		add(panelContrasena);

		revalidate();
		repaint();
	}

	private void inicializarPanelFooter (){
		JPanel panelFooter = new JPanel(new GridLayout(2, 1));
		if (VentanaPrincipal.LOGIN_CORRECTO){
			JButton botonGuardar = new JButton("Guardar");
			botonGuardar.addActionListener(_ -> {
				mensajeDeError.setText(obtenerMensajeDeError());
				if (! mensajeDeError.getText().isBlank()){
					botonGuardar.setActionCommand(Evento.EVENTO.ACTUALIZAR_DATOS_CLIENTE.name());
					botonGuardar.addActionListener(evento);
				}
			});
			botonGuardar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			panelFooter.add(botonGuardar);
			mensajeDeError.setForeground(Color.RED);
			mensajeDeError.setFont(new Font("Times New Roman", Font.BOLD, 20));
			mensajeDeError.setHorizontalAlignment(JLabel.CENTER);
			panelFooter.add(mensajeDeError);
			pantallaPrincipal.agregarPanelesSegunRol(tipoUsuario.name());
		}else{
			JButton botonLoginSignUp = new JButton("Login / SignUp");
			botonLoginSignUp.setActionCommand(Evento.EVENTO.LOGIN_SIGNUP.name());
			botonLoginSignUp.addActionListener(evento);
			botonLoginSignUp.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			panelFooter.add(botonLoginSignUp);
		}
		add(panelFooter);
		revalidate();
		repaint();
	}

	private String obtenerMensajeDeError (){
		//Validacion de Campos Vacios
		{
			if (boxNombreCompleto.getText().isEmpty()){
				return "Debe rellenar el campo Nombre Completo";
			}
			if (boxCorreo.getText().isEmpty()){
				return "Debe rellenar el campo Correo Electronico";
			}
			if (boxDireccion.getText().isEmpty()){
				return "Debe rellenar el campo Dirección";
			}
			if (boxTelefono.getText().isEmpty()){
				return "Debe rellenar el campo Teléfono";
			}
			if (boxContrasena.getPassword().length < 8){
				return "El campo contraseña debe tener mínimo 8 digitos";
			}
		}

		//Validacion de Formato Valido
		{
			final String regexCorreo = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
			if (! boxCorreo.getText().matches(regexCorreo)){
				return "El campo Correo Electronico tiene un formato inválido";
			}

			final String regexDireccion = "^(Calle|Carrera|Avenida|Diagonal|Transversal|Circunvalar)\\s\\d+\\s*(#|No\\.)\\s*\\d+(-\\d+)?(\\s*,\\s*[\\w\\s]+)?$\n";
			if (! boxDireccion.getText().matches(regexDireccion)){
				return "El campo Dirección debe tener la siguiente forma: (Calle / Carrera / Avenida / Diagonal / Transversal / Circunvalar) número (# / No.) " +
				       "número - numero, Texto Adicional";
			}

			final String regexTelefono = "^3[0-9]{9}$";
			if (! boxTelefono.getText().matches(regexTelefono)){
				return "El campo Teléfono tiene un formato inválido";
			}
		}
		return "";
	}

	public void setDatosUsuario (Object[] datosUsuario){
		this.datosUsuario = datosUsuario;
		refrescarDatosPerfil(this.datosUsuario);
	}
}
