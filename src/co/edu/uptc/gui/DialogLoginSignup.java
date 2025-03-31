package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//REVISADO Y APROBADO
public class DialogLoginSignup extends JDialog{
	private final JTabbedPane    panelContenedor;
	private       JPanel         panelLogin;
	private       JPanel         panelRegistro;
	private       JTextField     boxNombreCompleto;
	private       JTextField     boxCorreo;
	private       JTextField     boxDireccion;
	private       JTextField     boxTelefono;
	private       JPasswordField boxContrasena;
	private       JPasswordField passwordFieldContrasena;
	private final Evento         evento;

	public DialogLoginSignup (Evento evento, VentanaPrincipal ventanaPrincipal){
		super(new JFrame(), "Login - SingUp", true);
		panelContenedor = new JTabbedPane();
		agregarLogin();
		agregarRegistro();
		this.evento = evento;
		panelContenedor.addTab("Login", panelLogin);
		panelContenedor.addTab("SingUp", panelRegistro);
		add(panelContenedor);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().add(panelContenedor);
		this.pack();
		this.setVisible(true);
	}

	private void agregarLogin (){
		//Panel de Login
		panelLogin = new JPanel(new BorderLayout());

		//Campos de Usuario y Contraseña
		JPanel panelLoginDatos = new JPanel(new GridBagLayout());
		JLabel labelUsuario    = new JLabel("Correo Electronico", SwingConstants.CENTER);
		JLabel labelContrasena = new JLabel("Contraseña", SwingConstants.CENTER);
		boxCorreo               = new JTextField("admin1@example.com");
		passwordFieldContrasena = new JPasswordField("admin");

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
			}else{
				passwordFieldContrasena.setEchoChar('•');
			}
		});
		panelLoginDatos.add(checkBoxMostrarContrasena, gbc);

		//Botones
		JPanel  botones            = new JPanel(new GridLayout(2, 1));
		JButton botonIniciarSesion = new JButton("Iniciar Sesión");
		botonIniciarSesion.setActionCommand(Evento.EVENTO.INICIAR_SESION.name());
		botonIniciarSesion.addActionListener(evento);
		botones.add(botonIniciarSesion, gbc);

		JLabel linkRegistrarse = new JLabel("Registrarse");
		linkRegistrarse.setForeground(Color.GRAY);
		linkRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkRegistrarse.setHorizontalAlignment(JLabel.CENTER);
		linkRegistrarse.addMouseListener(new MouseAdapter(){
			@Override public void mouseClicked (MouseEvent e){
				panelContenedor.setSelectedComponent(panelRegistro);
			}
		});
		botones.add(linkRegistrarse, gbc);

		//Se agrega el panel de persistencia
		panelLogin.add(panelLoginDatos, BorderLayout.CENTER);
		panelLogin.add(botones, BorderLayout.SOUTH);
	}

	private void agregarRegistro (){
		//Panel SignUp
		panelRegistro = new JPanel(new BorderLayout());

		//Labels
		JLabel labelNombreCompleto = new JLabel("*Nombre Completo", SwingConstants.CENTER);
		JLabel labelCorreo         = new JLabel("*Correo Electronico", SwingConstants.CENTER);
		JLabel labelDireccion      = new JLabel("*Direccion", SwingConstants.CENTER);
		JLabel labelTelefono       = new JLabel("*Teléfono", SwingConstants.CENTER);
		JLabel labelContrasena     = new JLabel("*Contraseña", SwingConstants.CENTER);

		//Text Fields
		boxNombreCompleto = new JTextField("");
		boxCorreo         = new JTextField("admin1@example.com");
		boxDireccion      = new JTextField("");
		boxTelefono       = new JTextField("");
		boxContrasena     = new JPasswordField("admin");
		boxNombreCompleto.setHorizontalAlignment(JTextField.CENTER);
		boxCorreo.setHorizontalAlignment(JTextField.CENTER);
		boxDireccion.setHorizontalAlignment(JTextField.CENTER);
		boxTelefono.setHorizontalAlignment(JTextField.CENTER);
		boxContrasena.setHorizontalAlignment(JPasswordField.CENTER);

		//Botones y otros elementos
		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.RIGHT);
		checkBoxMostrarContrasena.addActionListener(e -> {
			if (checkBoxMostrarContrasena.isSelected()){
				boxContrasena.setEchoChar((char) 0);
			}else{
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
		panelRegistroDatos.add(labelContrasena, gbc);

		//Fila 5, Columna 0 y 1 => Box Tipo de Usuario y Contraseña
		gbc.gridy = 5;
		gbc.gridx = 0;
		panelRegistroDatos.add(boxContrasena, gbc);
		gbc.gridx = 1;
		panelRegistroDatos.add(checkBoxMostrarContrasena, gbc);

		JPanel  panelBotones   = new JPanel(new GridLayout(2, 1));
		JButton botonRegistrar = new JButton("Crear Cuenta");
		botonRegistrar.setActionCommand(Evento.EVENTO.REGISTRAR.name());
		botonRegistrar.addActionListener(evento);
		panelBotones.add(botonRegistrar, gbc);

		JLabel linkIniciarSesion = new JLabel("Iniciar Sesión");
		linkIniciarSesion.setForeground(Color.GRAY);
		linkIniciarSesion.setHorizontalAlignment(JLabel.CENTER);
		linkIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkIniciarSesion.addMouseListener(new MouseAdapter(){
			@Override public void mouseClicked (MouseEvent e){
				panelContenedor.setSelectedComponent(panelLogin);
			}
		});
		panelBotones.add(linkIniciarSesion, gbc);

		//Se agrega el panel de persistencia y de botones
		panelRegistro.add(panelRegistroDatos, BorderLayout.CENTER);
		panelRegistro.add(panelBotones, BorderLayout.SOUTH);
	}

	public String[] getDatosRegistro (){
		StringBuilder pswd = new StringBuilder();
		for (char c : passwordFieldContrasena.getPassword()){
			pswd.append(c);
		}
		return new String[]{boxNombreCompleto.getText(), boxCorreo.getText(), boxDireccion.getText(), boxTelefono.getText(), pswd.toString()};
	}

	public String[] getDatosLogin (){
		StringBuilder pswd = new StringBuilder();
		for (char c : passwordFieldContrasena.getPassword()){
			pswd.append(c);
		}
		return new String[]{boxCorreo.getText(), pswd.toString()};
	}
}