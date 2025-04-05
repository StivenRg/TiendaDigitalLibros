package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelEliminarLibro extends JPanel{
	private final Evento     evento;
	private       JPanel     panelCampos;
	private       JPanel     panelFooter;
	private       JTextField boxISBN;
	private       JTextField boxTitulo;
	private       JTextField boxAutor;
	private       JTextField boxAnioPublicacion;
	private       JTextField boxGenero;
	private       JTextField boxEditorial;
	private final JLabel     mensajeDeError = new JLabel();

	public PanelEliminarLibro (Evento evento){
		this.evento = evento;
		setLayout(new BorderLayout());
		inicializarPanel();
	}

	private void inicializarPanel (){
		inicializarPanelCampos();
		inicializarPanelFooter();
		add(panelCampos, BorderLayout.CENTER);
		add(panelFooter, BorderLayout.SOUTH);
	}

	private void inicializarPanelCampos (){
		panelCampos = new JPanel(new GridBagLayout());

		//Creacion de Labels y centrado de cada uno
		JLabel labelISBN            = new JLabel("***ISBN", SwingConstants.CENTER);
		JLabel labelTitulo          = new JLabel("**Titulo", SwingConstants.CENTER);
		JLabel labelAutor           = new JLabel("**Autor(es)", SwingConstants.CENTER);
		JLabel labelAnioPublicacion = new JLabel("**Año de Publicación", SwingConstants.CENTER);
		JLabel labelGenero          = new JLabel("**Género", SwingConstants.CENTER);
		JLabel labelEditorial       = new JLabel("**Editorial", SwingConstants.CENTER);

		//Text Fields
		boxISBN            = new JTextField();
		boxTitulo          = new JTextField();
		boxAutor           = new JTextField();
		boxAnioPublicacion = new JTextField();
		boxGenero          = new JTextField();
		boxEditorial       = new JTextField();

		//Centrado de JTextFields
		boxISBN.setHorizontalAlignment(JTextField.CENTER);
		boxTitulo.setHorizontalAlignment(JTextField.CENTER);
		boxAutor.setHorizontalAlignment(JTextField.CENTER);
		boxAnioPublicacion.setHorizontalAlignment(JTextField.CENTER);
		boxGenero.setHorizontalAlignment(JTextField.CENTER);
		boxEditorial.setHorizontalAlignment(JTextField.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.BOTH;

		//Peso Componente
		gbc.weightx = 0.45f; //Esta asignación hace que todos los layouts tengan este peso, hasta que se cambie

		//Fila 0, Columnas 0 y 1 ⇒ Labels ISBN y Titulo
		gbc.gridy = 0;
		gbc.gridx = 0;
		panelCampos.add(labelISBN, gbc);
		gbc.gridx = 1;
		panelCampos.add(labelTitulo, gbc);

		//Fila 1, Columna 0 y 1 ⇒ Box ISBN y Titulo
		gbc.gridy = 1;
		gbc.gridx = 0;
		panelCampos.add(boxISBN, gbc);
		gbc.gridx = 1;
		panelCampos.add(boxTitulo, gbc);

		//Fila 2, Columna 0 y 1 => Labels Autor y Año de Publicación
		gbc.gridy = 2;
		gbc.gridx = 0;
		panelCampos.add(labelAutor, gbc);
		gbc.gridx = 1;
		panelCampos.add(labelAnioPublicacion, gbc);

		//Fila 3, Columna 0 y 1 => Box Autor y Año de Publicación
		gbc.gridy = 3;
		gbc.gridx = 0;
		panelCampos.add(boxAutor, gbc);
		gbc.gridx = 1;
		panelCampos.add(boxAnioPublicacion, gbc);

		//Fila 4, Columna 0 y 1 => Labels Género y Editorial
		gbc.gridy = 4;
		gbc.gridx = 0;
		panelCampos.add(labelGenero, gbc);
		gbc.gridx = 1;
		panelCampos.add(labelEditorial, gbc);

		//Fila 5, Columna 0 y 1 => Box Género y Editorial
		gbc.gridy = 5;
		gbc.gridx = 0;
		panelCampos.add(boxGenero, gbc);
		gbc.gridx = 1;
		panelCampos.add(boxEditorial, gbc);

		add(panelCampos, BorderLayout.CENTER);
	}

	private void inicializarPanelFooter (){

		panelFooter = new JPanel(new GridLayout(2, 1));
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.setActionCommand(Evento.EVENTO.BUSCAR_LIBRO_ELIMINAR.name());
		botonBuscar.addActionListener(evento);

		JButton botonEliminar = new JButton("Eliminar");
		botonEliminar.addActionListener(_ -> {
			mensajeDeError.setText(obtenerMensajeDeError());
			if (mensajeDeError.getText().isBlank()){
				botonEliminar.setActionCommand(Evento.EVENTO.ELIMINAR_LIBRO.name());
				botonEliminar.addActionListener(evento);
			}
		});

		panelFooter.add(botonBuscar);
		panelFooter.add(botonEliminar);

		mensajeDeError.setForeground(Color.RED);
		mensajeDeError.setFont(new Font("Times New Roman", Font.BOLD, 20));
		mensajeDeError.setHorizontalAlignment(JLabel.CENTER);
		panelFooter.add(mensajeDeError);

		add(panelFooter, BorderLayout.SOUTH);
	}

	private String obtenerMensajeDeError (){
		//Validacion de Campos Vacios
		{
			if (boxISBN.getText().isEmpty()){
				return "Debe rellenar el campo ISBN";
			}
			if (boxTitulo.getText().isEmpty()){
				return "Debe rellenar el campo Titulo";
			}
			if (boxAnioPublicacion.getText().isEmpty()){
				return "Debe rellenar el campo Año de Publicación";
			}
			if (boxGenero.getText().isEmpty()){
				return "Debe rellenar el campo Género";
			}
			if (boxEditorial.getText().isEmpty()){
				return "Debe rellenar el campo Editorial";
			}
		}

		//Validacion de Formato Valido
		{
			//Validacion de ISBN
			if (! boxISBN.getText().matches("^[0-9]{10,13}$")){
				return "El campo ISBN debe tener de 10 a 13 caracteres numéricos";
			}

			//Validacion de año de publicacion
			try{
				int anioPublicacion = Integer.parseInt(boxAnioPublicacion.getText());
				if (anioPublicacion > 2025 || anioPublicacion < 868){
					return "El campo Año de Publicación debe estar entre 868 y 2025";
				}
			}catch (NumberFormatException e){
				return "El campo Año de Publicación debe ser un número entero";
			}
		}
		return "";
	}

	long getISBN (){
		return Long.parseLong(boxISBN.getText());
	}

	Object[] getDatosLibro (){
		return new Object[]{getISBN(),
		                    boxTitulo.getText(),
		                    boxAutor.getText(),
		                    Integer.parseInt(boxAnioPublicacion.getText()),
		                    boxGenero.getText(),
		                    boxEditorial.getText()
		};
	}

	void setDatosLibro (Object[] datos){
		boxISBN.setText(String.valueOf(datos[0]));
		boxTitulo.setText((String) datos[1]);
		boxAutor.setText((String) datos[2]);
		boxAnioPublicacion.setText(String.valueOf(datos[3]));
		boxGenero.setText((String) datos[4]);
		boxEditorial.setText((String) datos[5]);
	}
}
