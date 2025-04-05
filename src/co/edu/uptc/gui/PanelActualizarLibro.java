package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelActualizarLibro extends JPanel{
	private final Evento            evento;
	private       JPanel            panelCampos;
	private       JPanel            panelFooter;
	private       JTextField        boxISBN;
	private       JTextField        boxTitulo;
	private       JTextField        boxAutor;
	private       JTextField        boxAnioPublicacion;
	private       JTextField        boxGenero;
	private       JTextField        boxEditorial;
	private       JTextField        boxNumPaginas;
	private       JTextField        boxPrecioVenta;
	private       JTextField        boxCantidadInventario;
	private final JComboBox<String> comboBoxFormato = new JComboBox<>(new String[]{"DIGITAL", "IMPRESO"});
	private final JLabel            mensajeDeError  = new JLabel();

	public PanelActualizarLibro (Evento evento){
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
		JLabel labelISBN               = new JLabel("**ISBN", SwingConstants.CENTER);
		JLabel labelTitulo             = new JLabel("***Titulo", SwingConstants.CENTER);
		JLabel labelAutor              = new JLabel("***Autor(es)", SwingConstants.CENTER);
		JLabel labelAnioPublicacion    = new JLabel("***Año de Publicación", SwingConstants.CENTER);
		JLabel labelGenero             = new JLabel("***Género", SwingConstants.CENTER);
		JLabel labelEditorial          = new JLabel("***Editorial", SwingConstants.CENTER);
		JLabel labelNumPaginas         = new JLabel("***Número de Páginas", SwingConstants.CENTER);
		JLabel labelPrecioVenta        = new JLabel("Precio de Venta", SwingConstants.CENTER);
		JLabel labelCantidadInventario = new JLabel("Cantidad de Inventario", SwingConstants.CENTER);
		JLabel labelFormato            = new JLabel("Formato", SwingConstants.CENTER);

		//Text Fields
		boxISBN               = new JTextField();
		boxTitulo             = new JTextField();
		boxAutor              = new JTextField();
		boxAnioPublicacion    = new JTextField();
		boxGenero             = new JTextField();
		boxEditorial          = new JTextField();
		boxNumPaginas         = new JTextField();
		boxPrecioVenta        = new JTextField();
		boxCantidadInventario = new JTextField();

		//Centrado de JTextFields
		boxISBN.setHorizontalAlignment(JTextField.CENTER);
		boxTitulo.setHorizontalAlignment(JTextField.CENTER);
		boxAutor.setHorizontalAlignment(JTextField.CENTER);
		boxAnioPublicacion.setHorizontalAlignment(JTextField.CENTER);
		boxGenero.setHorizontalAlignment(JTextField.CENTER);
		boxEditorial.setHorizontalAlignment(JTextField.CENTER);
		boxNumPaginas.setHorizontalAlignment(JTextField.CENTER);
		boxPrecioVenta.setHorizontalAlignment(JTextField.CENTER);
		boxCantidadInventario.setHorizontalAlignment(JTextField.CENTER);
		comboBoxFormato.setSelectedItem("IMPRESO");

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

		//Fila 6, Columna 0 y 1 => Label #Páginas y Precio de Venta
		gbc.gridy = 6;
		gbc.gridx = 0;
		panelCampos.add(labelNumPaginas, gbc);
		gbc.gridx = 1;
		panelCampos.add(labelPrecioVenta, gbc);

		//Fila 7, Columna 0 y 1 => Box #Páginas y Precio de Venta
		gbc.gridy = 7;
		gbc.gridx = 0;
		panelCampos.add(boxNumPaginas, gbc);
		gbc.gridx = 1;
		panelCampos.add(boxPrecioVenta, gbc);

		//Fila 8, Columna 0 y 1 => Label Cantidad de Inventario y Formato
		gbc.gridy = 8;
		gbc.gridx = 0;
		panelCampos.add(labelCantidadInventario, gbc);
		gbc.gridx = 1;
		panelCampos.add(labelFormato, gbc);

		//Fila 9, Columna 0 y 1 => Box Cantidad de Inventario y Formato
		gbc.gridy = 9;
		gbc.gridx = 0;
		panelCampos.add(boxCantidadInventario, gbc);
		gbc.gridx = 1;
		panelCampos.add(comboBoxFormato, gbc);

		add(panelCampos, BorderLayout.CENTER);
	}

	private void inicializarPanelFooter (){
		panelFooter = new JPanel(new GridLayout(2, 1));
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.setActionCommand(Evento.EVENTO.BUSCAR_LIBRO.name());
		botonBuscar.addActionListener(evento);

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.addActionListener(e -> {
			mensajeDeError.setText(obtenerMensajeDeError());
			if (mensajeDeError.getText().isBlank()){
				botonGuardar.setActionCommand(Evento.EVENTO.ACTUALIZAR_LIBRO.name());
				botonGuardar.addActionListener(evento);
			}
		});

		panelFooter.add(botonBuscar);
		panelFooter.add(botonGuardar);

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
			if (boxNumPaginas.getText().isEmpty()){
				return "Debe rellenar el campo Número de Páginas";
			}
			if (boxPrecioVenta.getText().isEmpty()){
				return "Debe rellenar el campo Precio de Venta";
			}
			if (boxCantidadInventario.getText().isEmpty()){
				return "Debe rellenar el campo Cantidad de Inventario";
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

			//Validacion de Numero de Páginas
			try{
				int numeroPaginas = Integer.parseInt(boxNumPaginas.getText());
				if (numeroPaginas < 1){
					return "El campo Número de Páginas debe ser un entero positivo";
				}
			}catch (NumberFormatException e){
				return "El campo Número de Páginas debe ser un número entero";
			}

			//Validacion de Precio de Venta
			try{
				double precioVenta = Double.parseDouble(boxPrecioVenta.getText());
				if (precioVenta < 1.0){
					return "El campo Precio de Venta debe ser un número positivo";
				}
			}catch (NumberFormatException e){
				return "El campo Precio de Venta debe ser un número";
			}

			//Validacion de Cantidad de Inventario
			try{
				int cantidadInventario = Integer.parseInt(boxCantidadInventario.getText());
				if (cantidadInventario < 1){
					return "El campo Cantidad de Inventario debe ser un entero positivo";
				}
			}catch (NumberFormatException e){
				return "El campo Cantidad de Inventario debe ser un número entero";
			}
		}
		return "";
	}

	public long getISBN (){
		return Long.parseLong(boxISBN.getText());
	}

	public Object[] getDatosLibro (){
		return new Object[]{getISBN(),
		                    boxTitulo.getText(),
		                    boxAutor.getText(),
		                    Integer.parseInt(boxAnioPublicacion.getText()),
		                    boxGenero.getText(),
		                    boxEditorial.getText(),
		                    Integer.parseInt(boxNumPaginas.getText()),
		                    Double.parseDouble(boxPrecioVenta.getText()),
		                    Integer.parseInt(boxCantidadInventario.getText()),
		                    comboBoxFormato.getSelectedItem().toString()
		};
	}

	void setDatosLibro (Object[] datos){
		boxISBN.setText(String.valueOf(datos[0]));
		boxTitulo.setText((String) datos[1]);
		boxAutor.setText((String) datos[2]);
		boxAnioPublicacion.setText(String.valueOf(datos[3]));
		boxGenero.setText((String) datos[4]);
		boxEditorial.setText((String) datos[5]);
		boxNumPaginas.setText(String.valueOf(datos[6]));
		boxPrecioVenta.setText(String.valueOf(datos[7]));
		boxCantidadInventario.setText(String.valueOf(datos[8]));
		comboBoxFormato.setSelectedItem(datos[9]);
	}
}