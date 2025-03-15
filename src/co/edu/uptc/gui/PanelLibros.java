package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelLibros extends JPanel{
	private JTable tableLibros;
	private JPanel panelLibros;

	public PanelLibros (ManejadorEventos eventos){
		inicializarPanelLibros(eventos);
	}

	private void inicializarPanelLibros (ManejadorEventos eventos){
		panelLibros = new JPanel(new BorderLayout());
		String[] nombreColumnas = {"Titulo", "Autor", "Genero", "# Paginas", "Editorial", "Año", "Formato", "Precio", "Cantidad Disponible", "Agregar"};
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column == 9;
			}

			@Override public Class <?> getColumnClass (int columnIndex){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columnIndex == 9) ? Boolean.class : String.class;
			}
		};

		tableLibros = new

				              JTable(model);

		JScrollPane scrollPane = new JScrollPane(tableLibros);

		add(scrollPane, BorderLayout.CENTER);

		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventos);
		add(botonAgregar, BorderLayout.SOUTH);
	}
}
