package co.edu.uptc.controlador;

import co.edu.uptc.gui.FramePrincipal;
import co.edu.uptc.gui.PanelAgregarLibro;
import co.edu.uptc.gui.PanelLibros;

import javax.json.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class EventosLibros implements ActionListener{
	private       FramePrincipal framePrincipal;
	private       PanelLibros    panelLibros;
	private final String         RUTA_LIBROS = "persistencia/LIBROS.json";

	public EventosLibros (FramePrincipal framePrincipal){
		this.framePrincipal = framePrincipal;
	}

	@Override public void actionPerformed (ActionEvent e){
	}

	public void agregarLibro (
			long ISBN,
			String titulo,
			String autor,
			int anioPublicacion,
			String genero,
			String editorial,
			int numeroPaginas,
			double precioVenta,
			int cantidadDisponible,
			String Formato
	){
		try{
			InputStream inputStream = new FileInputStream(RUTA_LIBROS);
			JsonReader  reader      = Json.createReader(inputStream);
			JsonObject  jsonObject  = reader.readObject();

			JsonArray libros = jsonObject.getJsonArray("LIBROS");
			if (libroExistente(ISBN, libros)){
				return;
			}

			JsonObject libroNuevo = Json.createObjectBuilder()
			                            .add("ISBN", ISBN)
			                            .add("titulo", titulo)
			                            .add("autor", autor)
			                            .add("añoPublicacion", anioPublicacion)
			                            .add("genero", genero)
			                            .add("editorial", editorial)
			                            .add("numeroPaginas", numeroPaginas)
			                            .add("precioVenta", precioVenta)
			                            .add("cantidadDisponible", cantidadDisponible)
			                            .add("Formato", Formato)
			                            .build();

			JsonArrayBuilder librosBuilder = Json.createArrayBuilder();
			for (JsonObject libro : libros.getValuesAs(JsonObject.class)){
				librosBuilder.add(libro);
			}
			librosBuilder.add(libroNuevo);

			try (OutputStream outputStream = new FileOutputStream(RUTA_LIBROS); JsonWriter writer = Json.createWriter(outputStream)){
				writer.writeObject(libroNuevo);
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	private boolean libroExistente (long ISBN, JsonArray libros){
		for (JsonObject libro : libros.getValuesAs(JsonObject.class)){
			if (libro.getJsonNumber("ISBN").longValue() == ISBN){
				PanelAgregarLibro.libroDuplicado();
				return true;
			}
		}
		return false;
	}

	public Object[][] obtenerListaDeLibros (){
		Object[][] listaDeLibros = new Object[0][10];
		try (InputStream inputStream = new FileInputStream(RUTA_LIBROS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  libros     = jsonObject.getJsonArray("LIBROS");
			listaDeLibros = new Object[libros.size()][10];
			for (int i = 0; i < libros.size(); i++){
				JsonObject libro = libros.getJsonObject(i);
				listaDeLibros[i][0] = libro.getString("titulo");
				listaDeLibros[i][1] = libro.getString("autores");
				listaDeLibros[i][2] = libro.getString("categoria");
				listaDeLibros[i][3] = libro.getInt("numPaginas");
				listaDeLibros[i][4] = libro.getString("editorial");
				listaDeLibros[i][5] = libro.getInt("añoPublicacion");
				listaDeLibros[i][6] = libro.getString("formato");
				listaDeLibros[i][7] = String.format("$%,.2f", libro.getJsonNumber("precioVenta").doubleValue());
				listaDeLibros[i][8] = libro.getInt("cantidadInventario");
				listaDeLibros[i][9] = false;
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return listaDeLibros;
	}
}
