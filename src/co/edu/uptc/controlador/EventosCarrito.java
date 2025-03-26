package co.edu.uptc.controlador;

import co.edu.uptc.gui.FramePrincipal;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class EventosCarrito implements ActionListener{
	private final  FramePrincipal framePrincipal;
	private static String         RUTA_CARRITOS = "persistencia/CARRITOS.json";

	public EventosCarrito (FramePrincipal framePrincipal){
		this.framePrincipal = framePrincipal;
	}

	public static HashMap<Long, Integer> getCarritoDeCompras (int CID){
		try (InputStream inputStream = new FileInputStream(RUTA_CARRITOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  carritos   = jsonObject.getJsonArray("CARRITOS");
			for (JsonObject carrito : carritos.getValuesAs(JsonObject.class)){
				if (carrito.getInt("CID") == CID){
					HashMap<Long, Integer> carritoDeCompras = new HashMap<>();
					for (JsonObject articulo : carrito.getJsonArray("ARTICULOS").getValuesAs(JsonObject.class)){
						long tempISBNArticulo     = articulo.getJsonNumber("ISBN").longValue();
						int  tempCantidadArticulo = articulo.getInt("cantidad");
						carritoDeCompras.put(tempISBNArticulo, tempCantidadArticulo);
					}
					return carritoDeCompras;
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return new HashMap<>();
	}

	@Override public void actionPerformed (ActionEvent e){

	}
}
