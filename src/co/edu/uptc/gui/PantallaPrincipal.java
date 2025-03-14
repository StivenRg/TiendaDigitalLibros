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
	private static JTable      table; //Temporalmente estático para validar la funcionalidad

	public PantallaPrincipal (VentanaPrincipal ventana, ManejadorEventos eventos){
		setLayout(new BorderLayout());
		panelPrincipal = new JTabbedPane();

		inicializarPanelLibros(eventos);
		inicializarPanelCarrito(eventos);
		inicializarPanelPerfil(eventos);

		panelPrincipal.addTab("Lista de Libros", panelLibros);
		panelPrincipal.addTab("Carrito", panelCarrito);
		panelPrincipal.addTab("Perfil", panelPerfil);
		add(panelPrincipal, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
	}

	//Metodo agregado temporalmente para validar la funcionalidad
	public static ArrayList <Object> obtenerLibrosSelectos (){
		ArrayList <Object> librosSeleccionados = new ArrayList <>();
		for (int i = 0; i < table.getRowCount(); i++){
			try{
				if (table.getValueAt(i, 9).equals(true)){
					librosSeleccionados.add(table.getValueAt(i, 0));
				}
			} catch (NullPointerException e){
				System.err.println("Error en obtener Libros Selectos" + e.getMessage());
			}
		}
		return librosSeleccionados;
	}

	private void inicializarPanelLibros (ManejadorEventos eventos){
		panelLibros = new JPanel(new BorderLayout());
		String[] nombreColumnas = {"Titulo", "Autor", "Precio C/u", "% Impuesto", "Cantidad", "Precio Total", "+", "-", "X"};

		int totalFilas = 10;
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column >= 6 && column < 9;
			}
		};

		for (int i = 0; i < totalFilas; i++){
			Object[] libro = new Object[] {"Titulo " + (i + 1)};
			model.addRow(libro);
		}

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		panelLibros.add(scrollPane, BorderLayout.CENTER);
		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventos);
		panelLibros.add(botonAgregar, BorderLayout.SOUTH);
	}

	private void inicializarPanelCarrito (ManejadorEventos eventos){
		panelCarrito = new JPanel(new BorderLayout());

		DefaultTableModel model      = getDefaultTableModel();
		JLabel            labelTotal = new JLabel("Total: $0.00");
		eventosCarrito(model, labelTotal);
		rellenarCarrito(model);

		table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);

		panelCarrito.add(scrollPane, BorderLayout.CENTER);

		//Footer
		labelTotal.setHorizontalAlignment(JLabel.CENTER);
		JButton botonPagarEfectivo = new JButton("Pagar en Efectivo");
		botonPagarEfectivo.setActionCommand("pagarEfectivo");
		botonPagarEfectivo.addActionListener(eventos);

		JButton botonPagarTarjeta = new JButton("Pagar con Tarjeta");
		botonPagarTarjeta.setActionCommand("pagarTarjeta");
		botonPagarTarjeta.addActionListener(eventos);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.HORIZONTAL;
		float[] pesoComponentes = {0.5f, 0.15f, 0.15f};
		JPanel  botones         = new JPanel(new GridBagLayout());

		gbc.gridx   = 0;
		gbc.weightx = pesoComponentes[0];
		botones.add(botonPagarEfectivo, gbc);

		gbc.gridx   = 1;
		gbc.weightx = pesoComponentes[1];
		botones.add(botonPagarTarjeta, gbc);

		gbc.gridx   = 2;
		gbc.weightx = pesoComponentes[2];
		botones.add(Box.createGlue(), gbc);
		panelCarrito.add(botones, BorderLayout.SOUTH);
	}

	private void rellenarCarrito (DefaultTableModel model){
		int totalFilas = 10; //Esta constante debe ser eliminada cuando se agregue la funcionalidad de agregar libros
		//Agregamos 10 filas para validar la funcionalidad
		for (int i = 0; i < totalFilas; i++){
			Object[] libro = new Object[9];
			libro[0] = "Titulo " + (i + 1);
			libro[1] = "Autor " + (i + 1);
			double precioUnidad = ((i + 1) * 1000.0);
			libro[2] = precioUnidad;
			libro[3] = calcularValorImpuesto(precioUnidad);
			int cantidad = i + 2;
			libro[4] = cantidad;
			libro[5] = calcularPrecioVenta(precioUnidad, cantidad);
			libro[6] = false;
			libro[7] = false;
			libro[8] = false;
			model.addRow(libro);
		}
	}

	private static DefaultTableModel getDefaultTableModel (){
		String[] nombreColumnas = {"Titulo", "Autor", "Precio C/u", "Valor Impuesto", "Cantidad", "Precio Total", "+", "-", "X"};

		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column >= 6 && column < 9;
			}

			@Override public Class <?> getColumnClass (int columnIndex){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columnIndex >= 6 && columnIndex < 9) ? Boolean.class : String.class;
			}
		};
		return model;
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

	private void calcularTotal (DefaultTableModel model, JLabel labelTotal){
		model.addTableModelListener(event -> {
			// Obtenemos la fila y columna que cambiaron
			int columna = event.getColumn();

			//Validamos si los precios de venta de los libros cambian
			if (columna == 5){
				double total = 0;
				for (int i = 0; i < model.getRowCount(); i++){
					double subTotal = (Double.parseDouble(model.getValueAt(i, 5).toString()));
					total += subTotal;
				}
				labelTotal.setText(String.format("$%,.2f", total));
			}
		});
	}

	private double calcularValorImpuesto (double precioUnidad){
		if (precioUnidad >= 50000){
			return precioUnidad * 0.19;
		} else{
			return precioUnidad * 0.05;
		}
	}

	private double calcularPrecioVenta (double precioUnidad, int cantidad){
		double valorImpuesto = calcularValorImpuesto(precioUnidad);
		return ((precioUnidad + valorImpuesto) * cantidad);
	}

	private void eventosCarrito (DefaultTableModel model, JLabel labelTotal){
		model.addTableModelListener(event -> {
			// Obtenemos la fila y columna que cambiaron
			int fila    = event.getFirstRow();
			int columna = event.getColumn();

			double precioVenta;
			//Validamos el aumento o disminucion de cantidad
			if (columna == 4){
				try{
					double precioUnidad  = Double.parseDouble(model.getValueAt(fila, 2).toString());
					double valorImpuesto = calcularValorImpuesto(precioUnidad);
					int    cantidad      = Integer.parseInt(model.getValueAt(fila, 4).toString());
					precioVenta = ((precioUnidad + valorImpuesto) * cantidad);
					model.setValueAt(precioVenta, fila, 5);
				} catch (NullPointerException error){
					model.setValueAt(0, fila, 5);
				}
			}

			//Validamos si se agrego, se quito o se descarto un producto
			boolean agregarPulsado, quitarPulsado, descartarPulsado;
			if (columna == 6){
				agregarPulsado = (Boolean) model.getValueAt(fila, columna);
				if (agregarPulsado){
					int cantidad;
					try{
						cantidad = Integer.parseInt(model.getValueAt(fila, 4).toString());
						model.setValueAt(cantidad + 1, fila, 4);
					} catch (NullPointerException error){
						model.setValueAt(0, fila, 4);
						return;
					} finally{
						model.setValueAt(false, fila, 6);
					}
				}
				return;
			}

			if (columna == 7){
				int cantidad;
				quitarPulsado = (Boolean) model.getValueAt(fila, 7);
				if (quitarPulsado){
					try{
						cantidad = Integer.parseInt(model.getValueAt(fila, 4).toString());
						if (cantidad > 0){
							model.setValueAt(cantidad - 1, fila, 4);
						} else{
							model.removeRow(fila);
						}
					} catch (NullPointerException error){
						model.setValueAt(0, fila, 4);
						return;
					} finally{
						model.setValueAt(false, fila, 7);
					}
				}
				return;
			}

			if (columna == 8){
				descartarPulsado = (Boolean) model.getValueAt(fila, 8);
				if (descartarPulsado){
					model.removeRow(fila);
				}
			}
			calcularTotal(model, labelTotal);
		});
	}
}