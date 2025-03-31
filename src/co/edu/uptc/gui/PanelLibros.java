package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelLibros extends JPanel{
	private final       String[]          nombreColumnas = {"ISBN",
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
	private             DefaultTableModel model;
	private             JTable            tableLibros;
	private             VentanaPrincipal  ventana;
	private final       Evento            evento;
	public static final String[]          FORMATOS       = {"DIGITAL", "IMPRESO"};

	public PanelLibros (VentanaPrincipal ventana, Evento evento){
		this.ventana = ventana;
		this.evento  = evento;
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
		actualizarTabla();
		tableLibros = new JTable(model);
		tableLibros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(tableLibros);
		add(scrollPane, BorderLayout.CENTER);

		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand(Evento.EVENTO.AGREGAR_LIBRO_AL_CARRITO.name());
		botonAgregar.addActionListener(evento);
		add(botonAgregar, BorderLayout.SOUTH);
	}

	private void rellenarLista (){
		model.setDataVector(ventana.obtenerListaLibros(), nombreColumnas);
	}

	private void actualizarTabla (){
		model.addTableModelListener(e -> {
			int columnaCambio = e.getColumn();
			if (columnaCambio == 10){
				rellenarLista();
			}
		});
	}

	public DefaultTableModel getTableModel (){
		return model;
	}
}
