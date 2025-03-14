package co.edu.uptc.dto;

public class Libro{
	private String isbn;
	private String titulo;
	private String autores;
	private int    anioPublicacion;
	private String categoria;
	private String editorial;
	private int    numeroPaginas;
	private double precioVenta;
	private int    cantidadDisponible;
	private String formato;

	public Libro (){
	}

	public Libro (String isbn, String titulo, String autores, int anioPublicacion, String categoria, String editorial, int numeroPaginas, double precioVenta, int cantidadDisponible, String formato){
	}

	public void actualizarDatosLibro (String isbn,
	                                  String titulo,
	                                  String autores,
	                                  int anioPublicacion,
	                                  String categoria,
	                                  String editorial,
	                                  int numeroPaginas,
	                                  double precioVenta,
	                                  int cantidadDisponible,
	                                  String formato)
	{
	}

	public String getIsbn (){
		return isbn;
	}

	public void setIsbn (String paramIsbn){
		isbn = paramIsbn;
	}

	public String getTitulo (){
		return titulo;
	}

	public void setTitulo (String paramTitulo){
		titulo = paramTitulo;
	}

	public String getAutores (){
		return autores;
	}

	public void setAutores (String paramAutores){
		autores = paramAutores;
	}

	public int getAnioPublicacion (){
		return anioPublicacion;
	}

	public void setAnioPublicacion (int paramAnioPublicacion){
		anioPublicacion = paramAnioPublicacion;
	}

	public String getCategoria (){
		return categoria;
	}

	public void setCategoria (String paramCategoria){
		categoria = paramCategoria;
	}

	public String getEditorial (){
		return editorial;
	}

	public void setEditorial (String paramEditorial){
		editorial = paramEditorial;
	}

	public int getNumeroPaginas (){
		return numeroPaginas;
	}

	public void setNumeroPaginas (int paramNumeroPaginas){
		numeroPaginas = paramNumeroPaginas;
	}

	public double getPrecioVenta (){
		return precioVenta;
	}

	public void setPrecioVenta (double paramPrecioVenta){
		precioVenta = paramPrecioVenta;
	}

	public int getCantidadDisponible (){
		return cantidadDisponible;
	}

	public void setCantidadDisponible (int paramCantidadDisponible){
		cantidadDisponible = paramCantidadDisponible;
	}

	public String getFormato (){
		return formato;
	}

	public void setFormato (String paramFormato){
		formato = paramFormato;
	}
}