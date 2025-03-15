package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PantallaPrincipal extends JPanel{
	private        JTabbedPane panelPrincipal;
	private        JPanel      panelLibros;
	private        JPanel      panelCarrito;
	private        JPanel      panelPerfil;
	private static JTable      tableCarrito; //Temporalmente estático para validar la funcionalidad
	private static JTable      tableLibros; //Temporalmente estático para validar la funcionalidad

	public PantallaPrincipal (FramePrincipal ventana, ManejadorEventos eventos){
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		inicializarPanelLibros(eventos);
		inicializarPanelCarrito(eventos);
		inicializarPanelPerfil(eventos);

		panelPrincipal.addTab("Lista de Libros", panelLibros);
		panelPrincipal.addTab("PanelCarrito", panelCarrito);
		panelPrincipal.addTab("Perfil", panelPerfil);
		add(panelPrincipal, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
	}

	//Metodo agregado temporalmente para validar la funcionalidad
	public static ArrayList <Object> obtenerLibrosSelectos (){
		ArrayList <Object> librosSeleccionados = new ArrayList <>();
		//DefaultTableModel  model               = (DefaultTableModel) tableLibros.getModel();
		//Vector <Vector>    datos               = model.getDataVector();
		for (int i = 0; i < tableLibros.getRowCount(); i++){
			try{
				if (tableLibros.getValueAt(i, 9).equals(true)){
					librosSeleccionados.add(tableLibros.getValueAt(i, 0));
				}
			} catch (NullPointerException e){
				System.err.println("Error en obtener Libros Selectos" + e.getMessage());
			}
		}
		return librosSeleccionados;
	}

	private void inicializarPanelLibros (ManejadorEventos eventos){

	}

	private void inicializarPanelCarrito (ManejadorEventos eventos){
		panelCarrito = new PanelCarrito(eventos);
	}

	private void inicializarPanelPerfil (ManejadorEventos eventos){
		panelPerfil = new JPanel(new BorderLayout());
		JPanel panelBotones = new JPanel(new GridLayout(2, 1));

		//Labels
		JLabel labelNombreCompleto = new JLabel("Nombre Completo", SwingConstants.CENTER);
		JLabel labelCorreo         = new JLabel("Correo Electronico", SwingConstants.CENTER);
		JLabel labelDireccion      = new JLabel("Direccion", SwingConstants.CENTER);
		JLabel labelTelefono       = new JLabel("Teléfono", SwingConstants.CENTER);
		JLabel labelTipoUsuario    = new JLabel("Tipo de Usuario", SwingConstants.CENTER);
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

		JCheckBox checkBoxMostrarContrasena = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarContrasena.setSelected(false);
		checkBoxMostrarContrasena.setHorizontalAlignment(JCheckBox.CENTER);
		checkBoxMostrarContrasena.addActionListener(e -> {
			if (checkBoxMostrarContrasena.isSelected()){
				boxContrasena.setEchoChar((char) 0);
			} else{
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

		//Panel de botones
		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.setActionCommand("actualizarDatosCliente");
		botonGuardar.addActionListener(eventos);

		JLabel linkCancelar = new JLabel("Cancelar");
		linkCancelar.setForeground(Color.GRAY);
		linkCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkCancelar.setHorizontalAlignment(JLabel.CENTER);
		linkCancelar.addMouseListener(new MouseAdapter(){
			@Override public void mouseClicked (MouseEvent e){
				panelPrincipal.setSelectedComponent(panelLibros);
			}
		});
		panelBotones.add(botonGuardar);
		panelBotones.add(linkCancelar);

		//Se agrega el panel de datos y de botones
		panelPerfil.add(panelActualizarDatos, BorderLayout.CENTER);
		panelPerfil.add(panelBotones, BorderLayout.SOUTH);
	}
}