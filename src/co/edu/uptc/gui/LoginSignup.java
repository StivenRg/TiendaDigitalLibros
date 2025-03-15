package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginSignup extends JPanel{
	private final JTabbedPane panelPrincipal;
	private       JPanel      panelLogin;
	private       JPanel      panelRegistro;

	public LoginSignup (FramePrincipal ventana, ManejadorEventos eventos){
		panelPrincipal = new JTabbedPane();
		add(panelPrincipal);
		agregarLogin(eventos);
		agregarRegistro(eventos);
		panelPrincipal.addTab("Login", panelLogin);
		panelPrincipal.addTab("SingUp", panelRegistro);
		ventana.getContentPane().add(this);
		ventana.pack();
		ventana.setResizable(false);
	}

	private void agregarLogin (ManejadorEventos eventos){
		//Panel de Login
		panelLogin = new JPanel(new BorderLayout()); //Aunque no se usen las regiones, al agregar al centro, se extiende hacia los lados, lo cual mejora la UX

		//Campos de Usuario y Contraseña
		JPanel     panelLoginDatos = new JPanel(new GridBagLayout());
		JLabel     labelUsuario    = new JLabel("Correo Electronico", SwingConstants.CENTER);
		JLabel     labelContrasena = new JLabel("Contraseña", SwingConstants.CENTER);
		JTextField boxCorreo       = new JTextField("");
		boxCorreo.setSize(100, 20);
		JPasswordField passwordFieldContrasena = new JPasswordField();
		boxCorreo.setSize(20, 100);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.BOTH;

		//Peso Componente
		final float PESO_COMPONENTE = 0.2f;

		//Fila 0 y 1, Columna 0 => Usuario
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = PESO_COMPONENTE;
		panelLoginDatos.add(labelUsuario, gbc);
		gbc.gridy = 1;
		panelLoginDatos.add(boxCorreo, gbc);

		//Fila 2 y 3, Columna 0 => Contrasena
		gbc.gridx   = 0;
		gbc.gridy   = 2;
		gbc.weightx = PESO_COMPONENTE;
		panelLoginDatos.add(labelContrasena, gbc);
		gbc.gridy = 3;
		panelLoginDatos.add(passwordFieldContrasena, gbc);

		//Fila 4, columna 0=> CheckBox Mostrar Contraseña
		gbc.gridy = 4;
		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.CENTER);
		checkBoxMostrarContrasena.addActionListener(e -> {
			if (checkBoxMostrarContrasena.isSelected()){
				passwordFieldContrasena.setEchoChar((char) 0);
			} else{
				passwordFieldContrasena.setEchoChar('•');
			}
		});
		panelLoginDatos.add(checkBoxMostrarContrasena, gbc);

		//Botones
		JPanel  botones            = new JPanel(new GridLayout(2, 1));
		JButton botonIniciarSesion = new JButton("Iniciar Sesión");
		botonIniciarSesion.setActionCommand("iniciarSesion");
		botonIniciarSesion.addActionListener(eventos);

		botones.add(botonIniciarSesion, gbc);

		JLabel linkRegistrarse = new JLabel("Registrarse");
		linkRegistrarse.setForeground(Color.GRAY);
		linkRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkRegistrarse.setHorizontalAlignment(JLabel.CENTER);
		linkRegistrarse.addMouseListener(new MouseAdapter(){
			@Override public void mouseClicked (MouseEvent e){
				panelPrincipal.setSelectedComponent(panelRegistro);
			}
		});
		botones.add(linkRegistrarse, gbc);

		//Se agrega el panel de datos
		panelLogin.add(panelLoginDatos, BorderLayout.CENTER);
		panelLogin.add(botones, BorderLayout.SOUTH);
	}

	private void agregarRegistro (ManejadorEventos eventos){
		//Panel SignUp
		panelRegistro = new JPanel(new BorderLayout());

		//Labels
		JLabel labelNombreCompleto = new JLabel("*Nombre Completo", SwingConstants.CENTER);
		JLabel labelCorreo         = new JLabel("*Correo Electronico", SwingConstants.CENTER);
		JLabel labelDireccion      = new JLabel("*Direccion", SwingConstants.CENTER);
		JLabel labelTelefono       = new JLabel("*Teléfono", SwingConstants.CENTER);
		JLabel labelTipoUsuario    = new JLabel("*Tipo de Usuario", SwingConstants.CENTER);
		JLabel labelContrasena     = new JLabel("*Contraseña", SwingConstants.CENTER);

		//Text Fields
		JTextField         boxNombreCompleto   = new JTextField("");
		JTextField         boxCorreo           = new JTextField("");
		JTextField         boxDireccion        = new JTextField("");
		JTextField         boxTelefono         = new JTextField("");
		String[]           tiposUsuario        = {"Regular", "Premium", "Admin"};
		JComboBox <String> comboBoxTipoUsuario = new JComboBox <>(tiposUsuario);
		JPasswordField     boxContrasena       = new JPasswordField();
		boxNombreCompleto.setHorizontalAlignment(JTextField.CENTER);
		boxCorreo.setHorizontalAlignment(JTextField.CENTER);
		boxDireccion.setHorizontalAlignment(JTextField.CENTER);
		boxTelefono.setHorizontalAlignment(JTextField.CENTER);
		comboBoxTipoUsuario.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		boxContrasena.setHorizontalAlignment(JPasswordField.CENTER);

		//Botones y otros elementos
		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.RIGHT);
		checkBoxMostrarContrasena.addActionListener(e -> {
			if (checkBoxMostrarContrasena.isSelected()){
				boxContrasena.setEchoChar((char) 0);
			} else{
				boxContrasena.setEchoChar('•');
			}
		});

		//Layout
		JPanel             panelRegistroDatos = new JPanel(new GridBagLayout());
		GridBagConstraints gbc                = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.BOTH;

		//Peso Componente
		gbc.weightx = 0.45f; //Esta asignación hace que todos los layouts tengan este peso, hasta que se cambie

		//Fila 0, Columnas 0 y 1 ⇒ Labels Nombre y Correo
		gbc.gridy = 0;
		gbc.gridx = 0;
		panelRegistroDatos.add(labelNombreCompleto, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(labelCorreo, gbc);

		//Fila 1, Columna 0 y 1 ⇒ Box Nombre y Correo
		gbc.gridy = 1;
		gbc.gridx = 0;
		panelRegistroDatos.add(boxNombreCompleto, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(boxCorreo, gbc);

		//Fila 2, Columna 0 y 1 => Labels Direccion y Teléfono
		gbc.gridy = 2;
		gbc.gridx = 0;
		panelRegistroDatos.add(labelDireccion, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(labelTelefono, gbc);

		//Fila 3, Columna 0 y 1 => Box Direccion y Teléfono
		gbc.gridy = 3;
		gbc.gridx = 0;
		panelRegistroDatos.add(boxDireccion, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(boxTelefono, gbc);

		//Fila 4, Columna 0 y 1 => Labels Tipo de Usuario y Contraseña
		gbc.gridy = 4;
		gbc.gridx = 0;
		panelRegistroDatos.add(labelTipoUsuario, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(labelContrasena, gbc);

		//Fila 5, Columna 0 y 1 => Box Tipo de Usuario y Contraseña
		gbc.gridy = 5;
		gbc.gridx = 0;
		panelRegistroDatos.add(comboBoxTipoUsuario, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(boxContrasena, gbc);

		//Fila 6, columna 0 y 1 => Boton Registrarse
		gbc.gridy = 6;
		gbc.gridx = 0;
		panelRegistroDatos.add(Box.createGlue(), gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(checkBoxMostrarContrasena, gbc);

		JPanel  panelBotones   = new JPanel(new GridLayout(2, 1));
		JButton botonRegistrar = new JButton("Crear Cuenta");
		botonRegistrar.setActionCommand("registrarUsuario");
		botonRegistrar.addActionListener(eventos);
		panelBotones.add(botonRegistrar, gbc);

		JLabel linkIniciarSesion = new JLabel("Iniciar Sesión");
		linkIniciarSesion.setForeground(Color.GRAY);
		linkIniciarSesion.setHorizontalAlignment(JLabel.CENTER);
		linkIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkIniciarSesion.addMouseListener(new MouseAdapter(){
			@Override public void mouseClicked (MouseEvent e){
				panelPrincipal.setSelectedComponent(panelLogin);
			}
		});
		panelBotones.add(linkIniciarSesion, gbc);

		//Se agrega el panel de datos y de botones
		panelRegistro.add(panelRegistroDatos, BorderLayout.CENTER);
		panelRegistro.add(panelBotones, BorderLayout.SOUTH);
	}
}