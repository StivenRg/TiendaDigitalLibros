package co.edu.uptc.gui;

import co.edu.uptc.controlador.EventosCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCarrito extends JPanel{
	private final JLabel         labelTotal = new JLabel("Total: $0.00");
	private final EventosCarrito eventos;

	public PanelCarrito (EventosCarrito eventos){
		this.eventos = eventos;
		inicializarPanelCarrito();
		inicializarPanelFooter();
	}

	private static DefaultTableModel getDefaultTableModel (){
		String[] nombreColumnas = {"Titulo", "Autor", "Precio C/u", "Valor Impuesto", "Cantidad", "Precio Total", "+", "-", "X"};
		DefaultTableModel model = new DefaultTableModel(nombreColumnas, 0){
			@Override public boolean isCellEditable (int row, int column){
				return column >= 6 && column < 9;
			}

			@Override public Class<?> getColumnClass (int columna){
				// La última columna es de tipo Boolean para mostrar un JCheckBox
				return (columna >= 6 && columna < 9) ? Boolean.class : String.class;
			}
		};
		return model;
	}

	private void inicializarPanelCarrito (){
		setLayout(new BorderLayout());

		DefaultTableModel model = getDefaultTableModel();

		eventosCarrito(model);
		rellenarCarrito(model);

		JTable locTableCarrito = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(locTableCarrito);

		this.add(scrollPane, BorderLayout.CENTER);
	}

	private void inicializarPanelFooter (){
		//Footer (incluye el label de precio total)
		JButton botonPagarEfectivo = new JButton("Pagar en Efectivo");
		botonPagarEfectivo.setActionCommand("pagarEfectivo");
		botonPagarEfectivo.addActionListener(eventos);

		JButton botonPagarTarjeta = new JButton("Pagar con Tarjeta");
		botonPagarTarjeta.setActionCommand("pagarTarjeta");
		botonPagarTarjeta.addActionListener(eventos);

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

	private void rellenarCarrito (DefaultTableModel model){
		//Agregamos 10 filas para validar la funcionalidad
		for (int i = 0; i < 10; i++){
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
		calcularTotal(model, labelTotal);//Parcialmente validar
	}

	private void eventosCarrito (DefaultTableModel model){
		model.addTableModelListener(event -> {
			// Obtenemos la fila y columna que cambiaron
			int fila    = event.getFirstRow();
			int columna = event.getColumn();
			//Validamos el aumento o disminución de cantidad. También si se descarta un producto
			switch (columna){
				case 6 -> sumarAlCarrito(model, fila);
				case 7 -> quitarAlCarrito(model, fila);
				case 8 -> descartarAlCarrito(model, fila);
				default -> {
					return;
				}
			}

			actualizarPrecioVenta(model, fila);
			calcularTotal(model, labelTotal);
		});
	}

	private void calcularTotal (DefaultTableModel model, JLabel labelTotal){
		double totalVenta = 0;
		for (int fila = 0; fila < model.getDataVector().size(); fila++){
			double subTotal = (Double.parseDouble(model.getValueAt(fila, 5).toString()));
			totalVenta += subTotal;
		}
		labelTotal.setText(String.format("$%,.2f", totalVenta));
		revalidate();
		repaint();
	}

	private void actualizarPrecioVenta (DefaultTableModel model, int fila){
		if (fila > model.getRowCount() - 1){
			return;
		}

		try{
			double precioUnidad  = (Double) (model.getValueAt(fila, 2));
			double valorImpuesto = calcularValorImpuesto(precioUnidad);
			int    cantidad      = Integer.parseInt(model.getValueAt(fila, 4).toString());
			double precioVenta   = ((precioUnidad + valorImpuesto) * cantidad);
			model.setValueAt(precioVenta, fila, 5);
		}catch (NullPointerException error){
			model.setValueAt(0, fila, 5);
		}
	}

	private double calcularValorImpuesto (double precioUnidad){
		if (precioUnidad >= 50000){
			return precioUnidad * 0.19;
		}else{
			return precioUnidad * 0.05;
		}
	}

	private double calcularPrecioVenta (double precioUnidad, int cantidad){
		double valorImpuesto = calcularValorImpuesto(precioUnidad);
		return ((precioUnidad + valorImpuesto) * cantidad);
	}

	private void sumarAlCarrito (DefaultTableModel model, int fila){
		boolean agregarPulsado = (Boolean) model.getValueAt(fila, 6);
		if (! agregarPulsado){
			return;
		}
		try{
			int cantidad = Integer.parseInt(model.getValueAt(fila, 4).toString());
			model.setValueAt(cantidad + 1, fila, 4);
		}catch (NullPointerException error){
			model.setValueAt(0, fila, 4);
		}finally{
			model.setValueAt(false, fila, 6);
		}
		actualizarPrecioVenta(model, fila);
		calcularTotal(model, labelTotal);
	}

	private void quitarAlCarrito (DefaultTableModel model, int fila){
		boolean quitarPulsado = (Boolean) model.getValueAt(fila, 7);
		if (! quitarPulsado){
			return;
		}
		try{
			int cantidad = Integer.parseInt(model.getValueAt(fila, 4).toString());
			if (cantidad > 0){
				model.setValueAt(cantidad - 1, fila, 4);
				model.setValueAt(false, fila, 7);
				actualizarPrecioVenta(model, fila);
			}else{
				model.removeRow(fila);
			}
		}catch (NullPointerException error){
			model.setValueAt(0, fila, 4);
			model.setValueAt(false, fila, 7);
		}
		calcularTotal(model, labelTotal);
	}

	private void descartarAlCarrito (DefaultTableModel model, int fila){
		model.removeRow(fila);
		calcularTotal(model, labelTotal);
	}
}