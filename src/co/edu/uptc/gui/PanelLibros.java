package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelLibros extends JPanel{
	private final String[]          nombreColumnas = {"Titulo", "Autor", "Genero", "# Paginas", "Editorial", "Año", "Formato", "Precio", "Cantidad Disponible", "Agregar"};
	private       DefaultTableModel model;
	private       JTable            tableLibros;
	private       EventosLibros     eventos;
	private final JButton           botonAgregar   = new JButton("Agregar Libro");

	public PanelLibros (EventosLibros eventos){
		this.eventos = eventos;
		inicializarPanelLibros();
	}

	private DefaultTableModel getDefaultTableModel (){
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column == 9;
			}

			@Override public Class<?> getColumnClass (int columnIndex){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columnIndex == 9) ? Boolean.class : String.class;
			}
		};
		return model;
	}

	private void inicializarPanelLibros (){
		setLayout(new BorderLayout());
		model       = getDefaultTableModel();
		rellenarLista();
		tableLibros = new JTable(model);
		tableLibros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(tableLibros);
		add(scrollPane, BorderLayout.CENTER);

		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventos);
		add(botonAgregar, BorderLayout.SOUTH);
		actualizarTabla();
	}

	private void rellenarLista (){
		model.setDataVector(eventos.obtenerListaDeLibros(), nombreColumnas);
	}

	private void actualizarTabla (){
		model.addTableModelListener(e -> {
			int columnaCambio = e.getColumn();
			if (columnaCambio == 9){
				return;
			}
			rellenarLista();
		});
	}
}
