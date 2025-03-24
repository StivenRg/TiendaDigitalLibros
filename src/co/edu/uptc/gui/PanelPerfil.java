package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosUsuario;
import co.edu.uptc.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelPerfil extends JPanel implements InterfacePerfilListener{
	private final EventosUsuario eventosUsuario;
	private       Usuario        usuario;
	private       String[]       nombreAtributos = {"Nombre Completo", "Correo Electronico", "Direccion", "Teléfono", "Tipo de Usuario", "Contraseña"};
	private       String[]       atributos       = {"", "", "", "", "REGULAR", "************"}; //TODO

	public PanelPerfil (EventosUsuario eventosUsuario){
		this.eventosUsuario = eventosUsuario;
		eventosUsuario.setPanelPerfil(this);
		inicializarPanelPerfil();
	}

	private void refrescarDatosPerfil (Usuario datosUsuario){
		//Datos de Usuario
		usuario      = datosUsuario;
		atributos[0] = usuario.getNombreCompleto();
		atributos[1] = usuario.getCorreoElectronico();
		atributos[2] = usuario.getDireccionEnvio();
		atributos[3] = String.valueOf(usuario.getTelefonoContacto());
		atributos[4] = usuario.getTipoCliente().toUpperCase();
		inicializarPanelDatosUsuario();
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
		for (int i = 0; i < nombreAtributos.length; i++){
			JPanel panelDatos = new JPanel(new BorderLayout(10, 5));
			panelDatos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

			JLabel label = new JLabel(nombreAtributos[i] + ": ");
			label.setPreferredSize(new Dimension(100, 25));
			if (i == 5){
				JPasswordField textField = new JPasswordField(atributos[i]);
				textField.setPreferredSize(new Dimension(200, 25));
				panelDatos.add(label, BorderLayout.WEST);
				panelDatos.add(textField, BorderLayout.CENTER);
				add(panelDatos);
				return;
			}
			JTextField textField = new JTextField(atributos[i]);
			textField.setPreferredSize(new Dimension(200, 25));

			panelDatos.add(label, BorderLayout.WEST);
			panelDatos.add(textField, BorderLayout.CENTER);
			add(panelDatos);
		}
	}

	private void inicializarPanelFooter (){
		JPanel panelFooter = new JPanel(new BorderLayout(20, 5));

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.setActionCommand("actualizarDatosCliente");
		botonGuardar.addActionListener(eventosUsuario);
		botonGuardar.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setActionCommand("cancelarModificacionPerfil");
		botonCancelar.addActionListener(eventosUsuario);
		botonCancelar.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		panelFooter.add(botonGuardar, BorderLayout.NORTH);
		panelFooter.add(botonCancelar, BorderLayout.SOUTH);

		add(Box.createVerticalGlue());
		add(panelFooter);
	}

	private void actualizarDatosPerfil (Usuario datosUsuario){
		refrescarDatosPerfil(datosUsuario);
	}

	@Override public void onRegistroExitoso (Object[] datosUsuario){
		Usuario usuario = new Usuario((String) datosUsuario[0],
		                              (String) datosUsuario[1],
		                              (String) datosUsuario[2],
		                              (long) datosUsuario[3],
		                              (String) datosUsuario[4],
		                              "REGULAR",
		                              new HashMap<Long, Integer>()
		);
		refrescarDatosPerfil(usuario);
	}

	@Override public void onSesionIniciada (Object[] datosUsuario){
		Usuario usuario = eventosUsuario.getDatosUsuario();
		atributos[0] = usuario.getNombreCompleto();
		atributos[1] = usuario.getCorreoElectronico();
		atributos[2] = usuario.getDireccionEnvio();
		atributos[3] = String.valueOf(usuario.getTelefonoContacto());
		atributos[4] = usuario.getTipoCliente();
		refrescarDatosPerfil(usuario);
	}

	public Object[] getDatosRegistro (){
		return new Object[]{atributos[0], atributos[1], atributos[2], atributos[3], atributos[4]
		};
	}
}
