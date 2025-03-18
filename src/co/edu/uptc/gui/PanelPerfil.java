package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosUsuario;

import javax.swing.*;
import java.awt.*;

public class PanelPerfil extends JPanel{
	private EventosUsuario eventos;
	private JPanel         panelActualizarDatos;

	public PanelPerfil (EventosUsuario eventos){
		setLayout(new BorderLayout());
		this.eventos = eventos;
		inicializarPanelPerfil();
	}

	private void inicializarPanelPerfil (){
		//Labels
		JLabel labelNombreCompleto = new JLabel("Nombre Completo", SwingConstants.CENTER);
		JLabel labelCorreo         = new JLabel("Correo Electronico", SwingConstants.CENTER);
		JLabel labelDireccion      = new JLabel("Direccion", SwingConstants.CENTER);
		JLabel labelTelefono       = new JLabel("Teléfono", SwingConstants.CENTER);
		JLabel labelTipoUsuario    = new JLabel("Tipo de Usuario", SwingConstants.CENTER);
		JLabel labelContrasena     = new JLabel("*Contraseña", SwingConstants.CENTER);

		//Text Fields
		JTextField        boxNombreCompleto   = new JTextField("");
		JTextField        boxCorreo           = new JTextField("");
		JTextField        boxDireccion        = new JTextField("");
		JTextField        boxTelefono         = new JTextField("");
		String[]          tiposUsuario        = {"Regular", "Premium", "Admin"};
		JComboBox<String> comboBoxTipoUsuario = new JComboBox<>(tiposUsuario);
		JPasswordField    boxContrasena       = new JPasswordField();

		boxNombreCompleto.setHorizontalAlignment(JTextField.CENTER);
		boxCorreo.setHorizontalAlignment(JTextField.CENTER);
		boxDireccion.setHorizontalAlignment(JTextField.CENTER);
		boxTelefono.setHorizontalAlignment(JTextField.CENTER);
		comboBoxTipoUsuario.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		boxContrasena.setHorizontalAlignment(JPasswordField.CENTER);

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

		JPanel             panelActualizarDatos = new JPanel(new GridBagLayout());
		GridBagConstraints gbc                  = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.BOTH;

		//Peso Componente
		gbc.weightx = 0.45f; //Esta asignación hace que todos los layouts tengan este peso, hasta que se cambie

		//Fila 0, Columnas 0 y 1 ⇒ Labels Nombre y Correo
		gbc.gridy = 0;
		gbc.gridx = 0;
		panelActualizarDatos.add(labelNombreCompleto, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(labelCorreo, gbc);

		//Fila 1, Columna 0 y 1 ⇒ Box Nombre y Correo
		gbc.gridy = 1;
		gbc.gridx = 0;
		panelActualizarDatos.add(boxNombreCompleto, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(boxCorreo, gbc);

		//Fila 2, Columna 0 y 1 => Labels Direccion y Teléfono
		gbc.gridy = 2;
		gbc.gridx = 0;
		panelActualizarDatos.add(labelDireccion, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(labelTelefono, gbc);

		//Fila 3, Columna 0 y 1 => Box Direccion y Teléfono
		gbc.gridy = 3;
		gbc.gridx = 0;
		panelActualizarDatos.add(boxDireccion, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(boxTelefono, gbc);

		//Fila 4, Columna 0 y 1 => Labels Tipo de Usuario y Contraseña
		gbc.gridy = 4;
		gbc.gridx = 0;
		panelActualizarDatos.add(labelTipoUsuario, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(labelContrasena, gbc);

		//Fila 5, Columna 0 y 1 => Box Tipo de Usuario y Contraseña
		gbc.gridy = 5;
		gbc.gridx = 0;
		panelActualizarDatos.add(comboBoxTipoUsuario, gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(boxContrasena, gbc);

		gbc.gridy = 6;
		gbc.gridx = 0;
		panelActualizarDatos.add(Box.createGlue(), gbc);
		gbc.gridx = 1;
		panelActualizarDatos.add(checkBoxMostrarContrasena, gbc);

		add(panelActualizarDatos, BorderLayout.CENTER);

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.setActionCommand("actualizarDatosCliente");
		botonGuardar.addActionListener(eventos);

		add(botonGuardar, BorderLayout.SOUTH);
	}
}
