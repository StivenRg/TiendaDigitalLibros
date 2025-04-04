package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
	private final VentanaPrincipal  ventana;
	private final Evento            evento;

	public PanelLibros (VentanaPrincipal ventana, Evento evento){
		this.ventana = ventana;
		this.evento  = evento;
		inicializarPanelLibros();
	}

	private DefaultTableModel getDefaultTableModel (){
		return new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column == 10;
			}

			// La última columna es de tipo Boolean para mostrar un JCheckBox
			@Override public Class<?> getColumnClass (int columnIndex){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columnIndex == 10) ? Boolean.class : String.class;
			}
		};
	}

	private DefaultTableCellRenderer celdasDoubleFormateadas (){
		return new DefaultTableCellRenderer(){
			@Override public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
				if (column == 8){
					value = String.format("$%,.2f", (double) value);
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
	}

	private void inicializarPanelLibros (){
		setLayout(new BorderLayout());
		model = getDefaultTableModel();
		refrescarLista();
		JTable locTableLibros = new JTable(model);
		formatearColumnas(locTableLibros);
		locTableLibros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(locTableLibros);
		add(scrollPane, BorderLayout.CENTER);

		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand(Evento.EVENTO.AGREGAR_LIBRO_AL_CARRITO.name());
		botonAgregar.addActionListener(evento);
		add(botonAgregar, BorderLayout.SOUTH);
	}

	private void formatearColumnas (JTable tableLibros){
		tableLibros.getColumnModel().getColumn(8).setCellRenderer(celdasDoubleFormateadas());
	}

	private void refrescarLista (){
		model.setDataVector(ventana.obtenerListaLibros(), nombreColumnas);
	}

	public DefaultTableModel getTableModel (){
		return model;
	}
}
