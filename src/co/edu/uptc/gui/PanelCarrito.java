package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class PanelCarrito extends JPanel{
	private static final String[]               nombreColumnas = {"ISBN",
	                                                              "Titulo",
	                                                              "Autor",
	                                                              "Precio C/u",
	                                                              "Valor Impuesto",
	                                                              "Cantidad",
	                                                              "Precio Total",
	                                                              "+",
	                                                              "-",
	                                                              "X"
	};
	private              HashMap<Long, Integer> carritoDeCompras;
	public               DefaultTableModel      model;
	private              Evento                 evento;
	private              VentanaPrincipal       ventanaPrincipal;
	private              JLabel                 labelTotal;
	private static       int                    identificadorCarrito;

	public PanelCarrito (VentanaPrincipal ventanaPrincipal, Evento evento){
		this.evento           = evento;
		this.ventanaPrincipal = ventanaPrincipal;
		inicializarPanelCarrito();
		carritoDeCompras = new HashMap<>();
		inicializarPanelFooter();
	}

	public DefaultTableModel getDefaultTableModel (){
		// La última columna es de tipo Boolean para mostrar un JCheckBox
		return new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column >= 7 && column < 10;
			}

			@Override public Class<?> getColumnClass (int columna){
				return switch (columna){
					case 0 -> long.class;
					case 3, 4, 6 -> double.class;
					case 5 -> int.class;
					case 7, 8, 9 -> boolean.class;
					default -> String.class;
				};
			}
		};
	}

	private DefaultTableCellRenderer celdasDoubleFormateadas (){
		return new DefaultTableCellRenderer(){
			@Override public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
				if (column == 3 || column == 4 || column == 6){
					value = String.format("$%.2f", (double) value);
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
	}

	public HashMap<Long, Integer> getCarritoDeComprasTemporal (){
		return carritoDeCompras;
	}

	private void inicializarPanelCarrito (){
		setLayout(new BorderLayout());
		model = getDefaultTableModel();
		modificacionesCarrito(model);
		JTable tableCarrito = new JTable(model);
		formatearColumnas(tableCarrito);
		JScrollPane scrollPane = new JScrollPane(tableCarrito);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void formatearColumnas (JTable tableCarrito){
		tableCarrito.getColumnModel().getColumn(3).setCellRenderer(celdasDoubleFormateadas());
		tableCarrito.getColumnModel().getColumn(4).setCellRenderer(celdasDoubleFormateadas());
		tableCarrito.getColumnModel().getColumn(6).setCellRenderer(celdasDoubleFormateadas());
	}

	private void inicializarPanelFooter (){
		//Footer (incluye el label de precio total)
		JButton botonPagarEfectivo = new JButton("Pagar en Efectivo");
		botonPagarEfectivo.setActionCommand(Evento.EVENTO.PAGAR_EFECTIVO.name());
		botonPagarEfectivo.addActionListener(evento);

		JButton botonPagarTarjeta = new JButton("Pagar con Tarjeta");
		botonPagarTarjeta.setActionCommand(Evento.EVENTO.PAGAR_TARJETA.name());
		botonPagarTarjeta.addActionListener(evento);

		labelTotal = new JLabel("Total: $0.00");
		labelTotal.setHorizontalAlignment(JLabel.CENTER);
		labelTotal.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill   = GridBagConstraints.HORIZONTAL;
		float[] pesoComponentes = {0.5f, 0.15f, 0.15f};
		JPanel  footer          = new JPanel(new GridBagLayout());

		gbc.gridx   = 0;
		gbc.weightx = pesoComponentes[0];
		footer.add(labelTotal, gbc);

		gbc.gridx   = 1;
		gbc.weightx = pesoComponentes[1];
		footer.add(botonPagarEfectivo, gbc);

		gbc.gridx   = 2;
		gbc.weightx = pesoComponentes[2];
		footer.add(botonPagarTarjeta, gbc);
		this.add(footer, BorderLayout.SOUTH);
	}

	private void modificacionesCarrito (DefaultTableModel model){
		model.addTableModelListener(event -> {
			// Obtenemos la fila y columna que cambiaron
			int fila    = event.getFirstRow();
			int columna = event.getColumn();
			//Validamos el aumento o disminución de cantidad. También si se descarta un producto
			switch (columna){
				case 7 -> sumarAlCarrito(model, fila);
				case 8 -> quitarAlCarrito(model, fila);
				case 9 -> descartarDelCarrito(model, fila);
				default -> actualizarPrecioTotal();
			}
			if (VentanaPrincipal.LOGIN_CORRECTO){
				actualizarCarritoArchivo();
			}
		});
	}

	private void sumarAlCarrito (DefaultTableModel model, int fila){
		final int columnaAgregar = 6;
		if (! ((boolean) model.getValueAt(fila, columnaAgregar))){
			return;
		}
		final int columnaCantidad = 4;
		try{
			int cantidad = (int) (model.getValueAt(fila, columnaCantidad)); //Posible error en la conversion de tipo de dato, en ese caso usar parseInt()
			model.setValueAt(cantidad + 1, fila, columnaCantidad);
		}catch (NullPointerException e){
			System.err.println(e.getMessage());
		}finally{
			model.setValueAt(false, fila, columnaAgregar);
		}
		actualizarCarrito(model);
	}

	private void quitarAlCarrito (DefaultTableModel model, int fila){
		final int columnaQuitar = 7;
		if (! (boolean) model.getValueAt(fila, columnaQuitar)){
			return;
		}
		final int columnaCantidad = 5;
		try{
			int cantidad = Integer.parseInt(model.getValueAt(fila, columnaCantidad).toString());
			if (cantidad > 1){
				model.setValueAt(cantidad - 1, fila, columnaCantidad);
			}else{
				model.removeRow(fila);
			}
		}catch (NullPointerException e){
			System.err.println(e.getMessage());
		}finally{
			model.setValueAt(false, fila, columnaQuitar);
		}
		actualizarCarrito(model);
	}

	private void actualizarCarrito (DefaultTableModel model){
		final int columnaISBN     = 0;
		final int columnaCantidad = 5;
		for (int fila = 0; fila < model.getRowCount(); fila++){
			long ISBN     = (long) model.getValueAt(fila, columnaISBN);
			int  cantidad = (int) model.getValueAt(fila, columnaCantidad);
			if (cantidad < 1){
				carritoDeCompras.remove(ISBN);
			}
			carritoDeCompras.put(ISBN, cantidad);
		}
	}

	private void descartarDelCarrito (DefaultTableModel model, int fila){
		final int columnaISBN = 0;
		carritoDeCompras.remove((long) model.getValueAt(fila, columnaISBN));
		model.removeRow(fila);
	}

	public void actualizarCarritoArchivo (){
		HashMap<Long, Integer> carritoDeCompras = getCarritoDeComprasTemporal();
		VentanaPrincipal.guardarCarritoDeCompras(identificadorCarrito, carritoDeCompras);
	}

	public static void setIdentificadorCarrito (int paramIdentificadorCarrito){
		identificadorCarrito = paramIdentificadorCarrito;
	}

	void agregarArticulo (long ISBN){
		model.addRow(formatearArticulo(ISBN));
	}

	private Object[] formatearArticulo (long ISBN){
		Object[] datosImportados = ventanaPrincipal.obtenerDatosLibroCarrito(ISBN);
		String   titulo          = (String) datosImportados[1];
		String   autores         = (String) datosImportados[2];
		double   precioUnitario  = (double) datosImportados[3];
		double   precioImpuesto  = obtenerPrecioImpuesto(precioUnitario);
		int      cantidad        = 1;
		double   precioTotal     = obtenerPrecioVenta(precioUnitario, cantidad);
		boolean  agregar         = false;
		boolean  quitar          = false;
		boolean  descartar       = false;
		return new Object[]{ISBN, titulo, autores, precioUnitario, precioImpuesto, cantidad, precioTotal, agregar, quitar, descartar};
	}

	private double obtenerPrecioVenta (double valorUnitario, int cantidad){
		return ventanaPrincipal.obtenerPrecioTotalProducto(valorUnitario, cantidad);
	}

	private double obtenerPrecioImpuesto (double valorUnitario){
		return ventanaPrincipal.obtenerPrecioImpuesto(valorUnitario);
	}

	private void actualizarPrecioTotal (){
		double precioTotal = ventanaPrincipal.obtenerPrecioVentaTotal(model);
		labelTotal.setText(String.format("$%.2f", precioTotal));
	}

	void incrementarCantidad (Long ISBN){
		int fila = 0;
		for (int i = 0; i < model.getRowCount(); i++){
			if (model.getValueAt(i, 0).equals(ISBN)){
				fila = i;
				break;
			}
		}
		final int columnaCantidad = 5;
		int       cantidad        = ((int) model.getValueAt(fila, columnaCantidad)) + 1;
		model.setValueAt(cantidad, fila, columnaCantidad);
		actualizarPrecioTotal();
	}
}