package co.edu.uptc.gui;

import co.edu.uptc.modelo.Libro;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Evento implements ActionListener{
	private       VentanaPrincipal ventana;
	public static boolean          LOGIN_CORRECTO = false;
	public enum EVENTO{
		BUSCAR_LIBRO,
		AGREGAR_LIBRO_AL_CARRITO,
		ACTUALIZAR_LIBRO,
		ELIMINAR_LIBRO,
		PAGAR_EFECTIVO,
		PAGAR_TARJETA,
		INICIAR_SESION,
		REGISTRAR,
		AGREGAR_LIBRO_AL_ARCHIVO
	}

	public Evento (VentanaPrincipal ventana){
		this.ventana = ventana;
	}

	@Override public void actionPerformed (ActionEvent e){
		String actionCommand = e.getActionCommand();
		EVENTO nombreEvento  = EVENTO.valueOf(actionCommand);
		switch (nombreEvento){
			case BUSCAR_LIBRO -> ventana.buscarLibro();
			case AGREGAR_LIBRO_AL_CARRITO -> ventana.agregarLibroCarrito();
			case AGREGAR_LIBRO_AL_ARCHIVO -> agregarLibro();
			case ACTUALIZAR_LIBRO -> actualizarLibro();
			case ELIMINAR_LIBRO -> eliminarLibro();
			case PAGAR_EFECTIVO -> pagarEfectivo();
			case PAGAR_TARJETA -> pagarTarjeta();
			case INICIAR_SESION -> ventana.validarInicioSesion();
			case REGISTRAR -> ventana.validarRegistro();
		}
	}

	private void cambiarContrasena (){
	}

	private void actualizarDatosCliente (Object[] datosActualizados){
	}

	public static ArrayList<Libro> getCarritoDeCompras (int CID){
		try (InputStream inputStream = new FileInputStream(RUTA_CARRITOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  carritos   = jsonObject.getJsonArray("CARRITOS");
			for (JsonObject carrito : carritos.getValuesAs(JsonObject.class)){
				if (carrito.getInt("CID") == CID){
					ArrayList<Libro> carritoDeCompras = new HashMap<>();
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
}
