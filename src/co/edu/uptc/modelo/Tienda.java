package co.edu.uptc.modelo;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tienda{
	private static final String           RUTA_CARRITOS = "persistencia/CARRITOS.json";
	private static final String           RUTA_LIBROS   = "persistencia/LIBROS.json";
	private static final String           RUTA_USUARIOS = "persistencia/USUARIOS.json";
	private              ArrayList<Libro> listaDeLibros;

	public Tienda (){
	}

	private static int obtenerIndiceCID (){
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  usuarios   = jsonObject.getJsonArray("USUARIOS");
			return usuarios.size() + 1;
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return 1;
	}

	public void agregarLibro (Object[] datos){
		long   ISBN               = (long) datos[0];
		String titulo             = (String) datos[1];
		String autor              = (String) datos[2];
		int    anioPublicacion    = (int) datos[3];
		String genero             = (String) datos[4];
		String editorial          = (String) datos[5];
		int    numeroPaginas      = (int) datos[6];
		double precioVenta        = (double) datos[7];
		int    cantidadDisponible = (int) datos[8];
		String formato            = (String) datos[9];
		Libro  libro              = new Libro(ISBN, titulo, autor, anioPublicacion, genero, editorial, numeroPaginas, precioVenta, cantidadDisponible, formato);
	}

	private Libro obtenerLibro (long ISBN, JsonArray libros){
		for (JsonObject libro : libros.getValuesAs(JsonObject.class)){
			if (libro.getJsonNumber("ISBN").longValue() == ISBN){
				return new Libro(libro.getJsonNumber("ISBN").longValue(),
				                 libro.getString("titulo"),
				                 libro.getString("autores"),
				                 libro.getInt("añoPublicacion"),
				                 libro.getString("genero"),
				                 libro.getString("editorial"),
				                 libro.getInt("numeroPaginas"),
				                 libro.getJsonNumber("precioVenta").doubleValue(),
				                 libro.getInt("cantidadDisponible"),
				                 libro.getString("formato")
				);
			}
		}
		return null;
	}

	private ArrayList<Libro> obtenerListaDeLibros (JsonArray librosJson){
		ArrayList<Libro> listaDeLibros = new ArrayList<>();
		for (JsonObject libroObject : librosJson.getValuesAs(JsonObject.class)){
			Libro libro = new Libro();
			libro.setIsbn(libroObject.getJsonNumber("ISBN").longValue());
			libro.setTitulo(libroObject.getString("titulo"));
			libro.setAutores(libroObject.getString("autores"));
			libro.setAnioPublicacion(libroObject.getInt("añoPublicacion"));
			libro.setCategoria(libroObject.getString("categoria"));
			libro.setEditorial(libroObject.getString("editorial"));
			libro.setNumeroPaginas(libroObject.getInt("numeroPaginas"));
			libro.setPrecioVenta(libroObject.getJsonNumber("precioVenta").doubleValue());
			libro.setCantidadDisponible(libroObject.getInt("cantidadDisponible"));
			libro.setFormato(libroObject.getString("formato"));
			listaDeLibros.add(libro);
		}
		return listaDeLibros;
	}

	public Object[][] getDataVectorLibros (){
		Object[][] dataVectorLibros = new Object[0][11];
		try (InputStream inputStream = new FileInputStream(RUTA_LIBROS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  libros     = jsonObject.getJsonArray("LIBROS");
			listaDeLibros    = obtenerListaDeLibros(libros);
			dataVectorLibros = new Object[libros.size()][11];
			for (int i = 0; i < listaDeLibros.size(); i++){
				Libro libro = listaDeLibros.get(i);
				dataVectorLibros[i][0] = libro.getIsbn();
				dataVectorLibros[i][1] = libro.getTitulo();
				dataVectorLibros[i][2] = libro.getAutores();
				dataVectorLibros[i][3] = libro.getAnioPublicacion();
				dataVectorLibros[i][4] = libro.getCategoria();
				dataVectorLibros[i][5] = libro.getEditorial();
				dataVectorLibros[i][6] = libro.getNumeroPaginas();
				dataVectorLibros[i][7] = libro.getPrecioVenta();
				dataVectorLibros[i][8] = libro.getCantidadDisponible();
				dataVectorLibros[i][9] = false;
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return dataVectorLibros;
	}

	public Object[] buscarLibro (long ISBN){
		try{
			InputStream inputStream = new FileInputStream(RUTA_LIBROS);
			JsonReader  reader      = Json.createReader(inputStream);
			JsonObject  jsonObject  = reader.readObject();

			JsonArray libros = jsonObject.getJsonArray("LIBROS");
			return new Libro[]{obtenerLibro(ISBN, libros)};
		}catch (IOException e){
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static int getCID_Index (){
		int CID_Index = 0;
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();

			for (String rolActual : jsonObject.keySet()){
				JsonArray usuarios = jsonObject.getJsonArray(rolActual);
				for (JsonObject usuarioRol : usuarios.getValuesAs(JsonObject.class)){
					if (usuarioRol.getInt("CID") > CID_Index){
						CID_Index = usuarioRol.getInt("CID");
					}
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
			return - 1;
		}
		return CID_Index;
	}

	public boolean validarUsusarioLogin (String correoElectronico, String claveAcceso){
		Usuario usuario = obtenerUsuario(correoElectronico);

		if (usuario == null){
			return false;
		}

		StringBuilder pswd = new StringBuilder();
		for (char c : usuario.getClaveAcceso()){
			pswd.append(c);
		}
		return pswd.toString().equals(claveAcceso);
	}

	private Usuario obtenerUsuario (String correoElectronico){
		if (! usuarioExiste(correoElectronico)){
			return null;
		}
		Usuario.ROLES ROL = Usuario.validarRolUsuario(correoElectronico);

		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject     = reader.readObject();
			JsonArray  usuariosDelRol = jsonObject.getJsonArray(ROL.name());

			//Se crea un usuario vacio y, se llenan los datos que ya se tienen
			Usuario usuarioLocal = new Usuario();
			usuarioLocal.setCorreoElectronico(correoElectronico);
			usuarioLocal.setRolUsuario(ROL);

			for (JsonObject usuario : usuariosDelRol.getValuesAs(JsonObject.class)){
				if (usuario.getString("correoElectronico").equals(correoElectronico)){
					usuarioLocal.setNombreCompleto(usuario.getString("nombreCompleto"));
					usuarioLocal.setDireccionEnvio(usuario.getString("direccionEnvio"));
					usuarioLocal.setTelefonoContacto(usuario.getJsonNumber("telefonoContacto").longValue());
					usuarioLocal.setClaveAcceso(usuario.getString("claveAcceso").toCharArray());
					usuarioLocal.setCID(usuario.getInt("CID"));
					usuarioLocal.setCarritoDeCompras(getCarritoDeCompras(usuario.getInt("CID")));
					return usuarioLocal;
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

	public Object[] obtenerUsuarioSeguro (String correoElectronico){
		Usuario.ROLES ROL = Usuario.validarRolUsuario(correoElectronico);
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject     = reader.readObject();
			JsonArray  usuariosDelRol = jsonObject.getJsonArray(ROL.name());
			for (JsonObject usuario : usuariosDelRol.getValuesAs(JsonObject.class)){
				if (usuario.getString("correoElectronico").equals(correoElectronico)){
					return new Object[]{usuario.getString("nombreCompleto"),
					                    usuario.getString("correoElectronico"),
					                    usuario.getString("direccionEnvio"),
					                    usuario.getJsonNumber("telefonoContacto").longValue(),
					                    usuario.getString("claveAcceso"),
					                    usuario.getInt("CID"),
					                    getCarritoDeCompras(usuario.getInt("CID"))
					};
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

	private HashMap<Long, Integer> getCarritoDeCompras (int CID){
		try (InputStream inputStream = new FileInputStream(RUTA_CARRITOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			JsonArray  carritos   = jsonObject.getJsonArray("CARRITOS");
			for (JsonObject carritoActual : carritos.getValuesAs(JsonObject.class)){
				if (carritoActual.getInt("CID") == CID){
					return obtenerMapLibrosCarrito(carritoActual);
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

	private HashMap<Long, Integer> obtenerMapLibrosCarrito (JsonObject carritoActual){
		HashMap<Long, Integer> librosCarrito  = new HashMap<>(0);
		JsonArray              librosActuales = carritoActual.getJsonArray("ARTICULOS");
		for (JsonObject libro : librosActuales.getValuesAs(JsonObject.class)){
			long ISBN     = libro.getJsonNumber("ISBN").longValue();
			int  cantidad = libro.getInt("cantidad");
			librosCarrito.put(ISBN, cantidad);
		}
		return librosCarrito;
	}

	/// @param correoElectronico: Un array de objetos con los datos del usuario a registrar. Debe tener 5 campos en total: nombre completo, correo electronico,
	///  direccion, telefono y clave de acceso
	/// @return boolean: True si el usuario se registro correctamente, false en caso contrario
	public boolean validarDatosRegistro (String correoElectronico){
		return ! usuarioExiste(correoElectronico);
	}

	private boolean usuarioExiste (String correoElectronico){
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();

			Usuario.ROLES ROL = Usuario.validarRolUsuario(correoElectronico);

			JsonArray usuariosDelRol = jsonObject.getJsonArray(ROL.name());
			for (JsonObject usuario : usuariosDelRol.getValuesAs(JsonObject.class)){
				if (usuario.getString("correoElectronico").equals(correoElectronico)){
					return true;
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return false;
	}

	public void registrarUsuario (Object[] datosUsuario){
		Usuario usuario = new Usuario((String) datosUsuario[0],
		                              (String) datosUsuario[1],
		                              (String) datosUsuario[2],
		                              (long) datosUsuario[3],
		                              (char[]) datosUsuario[4],
		                              (int) datosUsuario[5],
		                              (HashMap<Long, Integer>) datosUsuario[6]
		);
		guardarUsuario(usuario);
	}

	private void guardarUsuario (Usuario usuario){
		guardarDatosUsuario(usuario);
	}

	private void guardarCarritoDeCompras (int CID, HashMap<Long, Integer> carritoDeCompras){
		JsonObject jsonActual;
		try (InputStream inputStream = new FileInputStream(RUTA_CARRITOS); JsonReader reader = Json.createReader(inputStream)){
			jsonActual = reader.readObject();
		}catch (Exception e){
			System.err.println(e.getMessage());
			return;
		}

		JsonArrayBuilder carritosBuilder    = Json.createArrayBuilder();
		JsonArray        carritosExistentes = jsonActual.getJsonArray("CARRITOS");
		for (JsonValue carrito : carritosExistentes){
			carritosBuilder.add(carrito);
		}
		carritosBuilder.add(convertirCarritoDeComprasAJson(CID, carritoDeCompras)).build();

		JsonObject jsonFinal = Json.createObjectBuilder().add("CARRITOS", carritosBuilder).build();
		try (OutputStream outputStream = new FileOutputStream(RUTA_CARRITOS); JsonWriter writer = Json.createWriter(outputStream)){
			writer.writeObject(jsonFinal);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	private JsonObject convertirCarritoDeComprasAJson (int CID, HashMap<Long, Integer> carritoDeCompras){
		JsonArrayBuilder articulosBuilder = Json.createArrayBuilder();
		for (Map.Entry<Long, Integer> entry : carritoDeCompras.entrySet()){
			articulosBuilder.add(Json.createObjectBuilder().add("ISBN", entry.getKey()).add("cantidad", entry.getValue()));
			articulosBuilder.build();
		}
		return Json.createObjectBuilder().add("CID", CID).add("ARTICULOS", articulosBuilder).build();
	}

	private void guardarDatosUsuario (Usuario usuario){
		JsonObject jsonActual;
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			jsonActual = reader.readObject();
		}catch (Exception e){
			System.err.println(e.getMessage());
			return;
		}

		Usuario.ROLES    ROL          = Usuario.validarRolUsuario(usuario.getCorreoElectronico());
		JsonArray        usuariosRol  = jsonActual.getJsonArray(ROL.name());
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (JsonValue usuarioRol : usuariosRol){
			arrayBuilder.add(usuarioRol);
		}
		arrayBuilder.add(convertirUsuarioAJson(usuario));

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		for (String key : jsonActual.keySet()){
			if (key.equals(ROL.name())){
				jsonObjectBuilder.add(key, arrayBuilder.build());
			}else{
				jsonObjectBuilder.add(key, jsonActual.get(key));
			}
		}

		JsonObject jsonFinal = jsonObjectBuilder.build();
		try (OutputStream outputStream = new FileOutputStream(RUTA_USUARIOS); JsonWriter writer = Json.createWriter(outputStream)){
			writer.writeObject(jsonFinal);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		guardarCarritoDeCompras(obtenerIndiceCID(), usuario.getCarritoDeCompras());
	}

	private JsonObject convertirUsuarioAJson (Usuario usuario){
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add("nombreCompleto", usuario.getNombreCompleto());
		jsonObjectBuilder.add("correoElectronico", usuario.getCorreoElectronico());
		jsonObjectBuilder.add("direccionEnvio", usuario.getDireccionEnvio());
		jsonObjectBuilder.add("telefonoContacto", usuario.getTelefonoContacto());
		jsonObjectBuilder.add("claveAcceso", String.valueOf(usuario.getClaveAcceso()));
		jsonObjectBuilder.add("CID", usuario.getCID());
		return jsonObjectBuilder.build();
	}
}
