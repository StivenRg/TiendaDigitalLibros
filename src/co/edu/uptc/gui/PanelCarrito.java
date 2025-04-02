package co.edu.uptc.gui;

import javax.swing.*;
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

			@Override public Object getValueAt (int row, int column){
				Object value = super.getValueAt(row, column); // Obtiene el valor original
				if ((column == 3 || column == 4 || column == 6)){
					return String.format("$%.2f", (double) value);
				}
				return value; // Para otras columnas, se devuelve el valor normal
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
		JTable      tableCarrito = new JTable(model);
		JScrollPane scrollPane   = new JScrollPane(tableCarrito);
		add(scrollPane, BorderLayout.CENTER);
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
				default -> {
					return;
				}
			}
			actualizarPrecioVenta();
			actualizarPrecioImpuesto();
			actualizarPrecioTotal();
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

	public void actualizarCarritoLocal (){
		for (long ISBN : carritoDeCompras.keySet()){
			model.addRow(ventanaPrincipal.obtenerDatosLibroCarrito(ISBN));
		}
	}

	private void actualizarPrecioVenta (){
		final int columnaPrecioVenta = 6;
		for (int fila = 0; fila < model.getRowCount(); fila++){
			double valorUnitario = (Double) model.getValueAt(fila, 2);
			int    cantidad      = (int) model.getValueAt(fila, 4);
			model.setValueAt(ventanaPrincipal.obtenerPrecioTotalProducto(valorUnitario, cantidad), fila, columnaPrecioVenta);
		}
	}

	private void actualizarPrecioImpuesto (){
		final int columnaValorImpuesto = 4;
		for (int fila = 0; fila < model.getRowCount(); fila++){
			double valorUnitario = (Double) model.getValueAt(fila, 2);
			model.setValueAt(ventanaPrincipal.obtenerPrecioImpuesto(valorUnitario), fila, columnaValorImpuesto);
		}
	}

	private void actualizarPrecioTotal (){
		for (int fila = 0; fila < model.getRowCount(); fila++){
			model.setValueAt(ventanaPrincipal.obtenerrPrecioVentaTotal(model), fila, 6);
		}
	}
}