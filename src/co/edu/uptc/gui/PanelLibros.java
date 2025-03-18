package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosLibros;
import co.edu.uptc.controlador.EventosUsuario;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.InputStream;

public class PanelLibros extends JPanel{
	private static final String            RUTA_LIBROS = "/co/edu/uptc/persistencia/LIBROS.json";
	private              JTable            tableLibros;
	private final        DefaultTableModel model       = getDefaultTableModel();

	public PanelLibros (EventosLibros eventos){
		inicializarPanelLibros(eventos);
	}

	private DefaultTableModel getDefaultTableModel (){
		String[] nombreColumnas = {"Titulo", "Autor", "Genero", "# Paginas", "Editorial", "Año", "Formato", "Precio", "Cantidad Disponible", "Agregar"};
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

	private void inicializarPanelLibros (EventosLibros eventos){
		setLayout(new BorderLayout());
		rellenarLista();
		tableLibros = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tableLibros);
		add(scrollPane, BorderLayout.CENTER);

		JButton botonAgregar = new JButton("Agregar Libro");
		botonAgregar.setActionCommand("agregarLibro");
		botonAgregar.addActionListener(eventos);
		add(botonAgregar, BorderLayout.SOUTH);
		actualizarTabla();
	}

	private void rellenarLista (){
		try (InputStream inputStream = EventosUsuario.class.getResourceAsStream(RUTA_LIBROS); JsonReader reader = Json.createReader(inputStream)){
			model.setRowCount(0);
			JsonObject jsonObject = reader.readObject();
			JsonArray  libros     = jsonObject.getJsonArray("LIBROS");
			for (int i = 0; i < libros.size(); i++){
				JsonObject libro = libros.getJsonObject(i);
				model.addRow(new Object[]{libro.getString("titulo"),
				                          libro.getString("autores"),
				                          libro.getString("categoria"),
				                          libro.getInt("numPaginas"),
				                          libro.getString("editorial"),
				                          libro.getInt("añoPublicacion"),
				                          libro.getString("formato"),
				                          String.format("$%,.2f", libro.getJsonNumber("precioVenta").doubleValue()),
				                          libro.getInt("cantidadInventario"),
				                          false
				});
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
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
