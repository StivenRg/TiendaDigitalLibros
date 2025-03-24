package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelLibros extends JPanel{
	private final String[]          nombreColumnas = {"ISBN",
	                                                  "Titulo",
	                                                  "Autor",
	                                                  "Genero",
	                                                  "# Paginas",
	                                                  "Editorial",
	                                                  "Año",
	                                                  "Formato",
	                                                  "Precio",
	                                                  "Cantidad Disponible",
	                                                  "Agregar"
	};
	private       DefaultTableModel model;
	private       JTable            tableLibros;
	private       EventosLibros     eventosLibros;
	private final JButton           botonAgregar   = new JButton("Agregar Libro");

	public PanelLibros (EventosLibros eventosLibros){
		this.eventosLibros = eventosLibros;
		inicializarPanelLibros();
	}

	private DefaultTableModel getDefaultTableModel (){
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column == 10;
			}

			@Override public Class<?> getColumnClass (int columnIndex){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columnIndex == 10) ? Boolean.class : String.class;
			}
		};
		return model;
	}

	private void inicializarPanelLibros (){
		setLayout(new BorderLayout());
		model = getDefaultTableModel();
		rellenarLista();
		tableLibros = new JTable(model);
		tableLibros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(tableLibros);
		add(scrollPane, BorderLayout.CENTER);

		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventosLibros);
		add(botonAgregar, BorderLayout.SOUTH);
		actualizarTabla();
	}

	private void rellenarLista (){
		model.setDataVector(eventosLibros.obtenerListaDeLibros(), nombreColumnas);
	}

	private void actualizarTabla (){
		model.addTableModelListener(e -> {
			int columnaCambio = e.getColumn();
			if (columnaCambio == 10){
				return;
			}
			rellenarLista();
		});
	}
}
