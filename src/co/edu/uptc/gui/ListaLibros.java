package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListaLibros extends JPanel{
	private JPanel panelCabecera;
	private JPanel panelContenido;

	public ListaLibros (VentanaPrincipal ventana, ManejadorEventos eventos){
		setLayout(new BorderLayout());

		inicializarCabecera(eventos);
		add(panelCabecera, BorderLayout.NORTH);

		inicializarContenido(eventos);
		add(panelContenido, BorderLayout.CENTER);

		ventana.getContentPane().add(this);
	}

	private void inicializarCabecera (ManejadorEventos eventos){
		panelCabecera = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.fill   = GridBagConstraints.HORIZONTAL;

		final float pesoEspacio = 0.01f;
		final float pesoBoton   = 0.14f;

		//Columna 1
		gridBagConstraints.gridx   = 0;
		gridBagConstraints.weightx = 0.7;
		JLabel labelCabecera = new JLabel("Lista de Libros Disponibles", SwingConstants.LEFT);
		labelCabecera.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 20));
		labelCabecera.setHorizontalAlignment(JLabel.CENTER);
		labelCabecera.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(0, 2, 0, 2)));
		panelCabecera.add(labelCabecera, gridBagConstraints);

		//Columna 2
		gridBagConstraints.gridx   = 1;
		gridBagConstraints.weightx = pesoEspacio;
		panelCabecera.add(Box.createGlue(), gridBagConstraints);

		//Columna 3
		gridBagConstraints.gridx   = 2;
		gridBagConstraints.weightx = pesoBoton;
		JButton botonCarrito = new JButton("Ver Carrito");
		botonCarrito.setActionCommand("mostrarCarrito");
		botonCarrito.addActionListener(eventos);
		panelCabecera.add(botonCarrito, gridBagConstraints);

		//Columna 4
		gridBagConstraints.gridx   = 3;
		gridBagConstraints.weightx = pesoEspacio;
		panelCabecera.add(Box.createGlue(), gridBagConstraints);

		//Columna 5
		gridBagConstraints.gridx   = 4;
		gridBagConstraints.weightx = pesoBoton;
		JButton botonPerfil = new JButton("Perfil");
		botonPerfil.setActionCommand("mostrarDatosCliente");
		botonPerfil.addActionListener(eventos);
		panelCabecera.add(botonPerfil, gridBagConstraints);
	}

	private void inicializarContenido (ManejadorEventos eventos){
		panelContenido = new JPanel();
		panelContenido.setLayout(new BorderLayout());

		String[] nombreColumnas = {"Titulo", "Autor", "Género", "#Páginas", "Editorial", "Año", "Formato", "Precio", "Cantidad", "Agregar"};

		int totalFilas = 10;
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column == 9;
			}

			@Override public Class <?> getColumnClass (int column){
				return (column == 9) ? Boolean.class : String.class;
			}
		};

		for (int i = 0; i < totalFilas; i++){
			Object[] libro = new Object[] {i};
			model.addRow(libro);
		}

		JTable      table      = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		panelContenido.add(scrollPane, BorderLayout.CENTER);

		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventos);
		panelContenido.add(botonAgregar, BorderLayout.SOUTH);
	}
}