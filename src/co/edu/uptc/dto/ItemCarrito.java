package co.edu.uptc.dto;

public class ItemCarrito{
	private int    cantidad;
	private double subtotal;
	private double totalImpuestos;
	private double totalCompra;
	private double descuentoPorcentaje;
	private double descuentoValor;

	public ItemCarrito (){
	}

	public ItemCarrito (int cantidad, double subtotal, double totalImpuestos, double totalCompra, double descuentoPorcentaje, double descuentoValor){
	}

	public void sumarLibro (){
	}

	public void restarLibro (){
	}

	public void actualizarCantidadItem (int cantidad){
	}
}