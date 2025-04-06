package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelCrearCuentas extends JPanel{
	private final Evento     evento;
	private       JPanel     panelDatos;
	private       JPanel     panelFooter;
	private       JTextField boxCorreo;
	private       JTextField boxClave;
	private       JTextField boxNombre;
	private       JTextField boxDireccion;
	private       JTextField boxTelefono;
	private       JLabel     mensajeDeError = new JLabel();

	public PanelCrearCuentas (Evento evento){
		this.evento = evento;
		inicializarPanel();
	}

	private void inicializarPanel (){
		setLayout(new BorderLayout());
		inicializarPanelCampos();
		inicializarPanelFooter();
	}

	private void inicializarPanelCampos (){
		panelDatos = new JPanel(new GridLayout(10, 1));
		//Creacion de Labels
		JLabel labelNombre    = new JLabel("Nombre");
		JLabel labelCorreo    = new JLabel("Correo Electronico");
		JLabel labelDireccion = new JLabel("Dirección");
		JLabel labelTelefono  = new JLabel("Teléfono");
		JLabel labelClave     = new JLabel("Contraseña");

		//Centrado de Labels
		labelNombre.setHorizontalAlignment(JLabel.CENTER);
		labelCorreo.setHorizontalAlignment(JLabel.CENTER);
		labelDireccion.setHorizontalAlignment(JLabel.CENTER);
		labelTelefono.setHorizontalAlignment(JLabel.CENTER);
		labelClave.setHorizontalAlignment(JLabel.CENTER);

		//Creacion de Text Fields
		boxNombre    = new JTextField();
		boxCorreo    = new JTextField();
		boxDireccion = new JTextField();
		boxTelefono  = new JTextField();
		boxClave     = new JPasswordField();

		//Centrado de Text Fields
		boxNombre.setHorizontalAlignment(JTextField.CENTER);
		boxCorreo.setHorizontalAlignment(JTextField.CENTER);
		boxDireccion.setHorizontalAlignment(JTextField.CENTER);
		boxTelefono.setHorizontalAlignment(JTextField.CENTER);
		boxClave.setHorizontalAlignment(JPasswordField.CENTER);

		//Agregacion de Labels y Text Fields a Panel
		panelDatos.add(labelNombre);
		panelDatos.add(boxNombre);
		panelDatos.add(labelCorreo);
		panelDatos.add(boxCorreo);
		panelDatos.add(labelDireccion);
		panelDatos.add(boxDireccion);
		panelDatos.add(labelTelefono);
		panelDatos.add(boxTelefono);
		panelDatos.add(labelClave);
		panelDatos.add(boxClave);

		add(panelDatos, BorderLayout.CENTER);
	}

	private void inicializarPanelFooter (){
		panelFooter = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill   = GridBagConstraints.BOTH;

		final float pesoBotones = 0.4f;
		final float pesoMensaje = 0.2f;
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = pesoBotones;
		JButton botonValidar = new JButton("Validar Usuario");
		botonValidar.addActionListener(_ -> {
			if (boxCorreo.getText().isBlank()){
				mensajeDeError.setText("Debe rellenar el campo Correo Electronico para validarlo");
				return;
			}
			botonValidar.setActionCommand(Evento.EVENTO.VALIDAR_USUARIO.name());
			botonValidar.addActionListener(evento);
		});
		botonValidar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panelFooter.add(botonValidar, gbc);

		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = pesoBotones;
		JButton botonCrear = new JButton("Crear Usuario");
		botonCrear.addActionListener(_ -> {
			mensajeDeError.setText(obtenerMensajeDeError());
			if (! mensajeDeError.getText().isBlank()){
				botonCrear.setActionCommand(Evento.EVENTO.CREAR_CUENTA.name());
				botonCrear.addActionListener(evento);
			}
		});
		botonCrear.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panelFooter.add(botonCrear, gbc);

		gbc.gridx   = 0;
		gbc.gridy   = 2;
		gbc.weightx = pesoMensaje;
		mensajeDeError.setForeground(Color.RED);
		mensajeDeError.setHorizontalAlignment(JLabel.CENTER);
		panelFooter.add(mensajeDeError, gbc);

		add(panelFooter, BorderLayout.SOUTH);
	}

	private String obtenerMensajeDeError (){
		//Validacion de Campos Vacios
		{
			if (boxNombre.getText().isBlank()){
				return "Debe rellenar el campo Nombre";
			}
			if (boxCorreo.getText().isBlank()){
				return "Debe rellenar el campo Correo Electronico";
			}
			if (boxDireccion.getText().isBlank()){
				return "Debe rellenar el campo Dirección";
			}
			if (boxTelefono.getText().isBlank()){
				return "Debe rellenar el campo Teléfono";
			}
			if (boxClave.getText().isBlank()){
				return "Debe rellenar el campo Contraseña";
			}
		}

		//Validacion de formato valido Correo, Direccion y Teléfono
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

	public String getCorreo (){
		return boxCorreo.getText();
	}

	public Object[] getDatosUsuario (){
		return new Object[]{boxNombre.getText(), boxCorreo.getText(), boxDireccion.getText(), boxTelefono.getText(), boxClave.getText()};
	}

	public void setMensajeDeError (String mensaje){
		mensajeDeError.setText(mensaje);
	}
}
