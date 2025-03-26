package co.edu.uptc.controlador;

import co.edu.uptc.gui.*;
import co.edu.uptc.modelo.Usuario;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class EventosUsuario implements ActionListener{
	private static final String            RUTA_USUARIOS  = "persistencia/USUARIOS.json";
	private static       boolean           LOGIN_CORRECTO = false;
	private static       String            ROL            = "REGULAR";
	private static       int               CID_Index;
	private              Usuario           usuario;
	private              DialogLoginSignup dialogLoginSignup;
	private              PanelPerfil       panelPerfil;

	public EventosUsuario (FramePrincipal framePrincipal){
		EventosBotones eventosBotones = new EventosBotones(framePrincipal);
	}

	public static int getCID_Index (){
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();
			for (String locRol : jsonObject.keySet()){
				JsonArray usuarios = jsonObject.getJsonArray(locRol);
				for (JsonObject usuario : usuarios.getValuesAs(JsonObject.class)){
					if (usuario.getInt("CID") > CID_Index){
						CID_Index = usuario.getInt("CID");
					}
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
			return - 1;
		}
		return ++ CID_Index;
	}

	public boolean validarLogin (Object[] datosUsuario){
		validarUsusarioLogin(datosUsuario);
		return isLoginCorrecto();
	}

	private void validarUsusarioLogin (Object[] datosUsuario){
		String nombreUsuario = (String) datosUsuario[0];
		char[] claveAcceso   = (char[]) datosUsuario[1];
		this.usuario = obtenerUsuario(nombreUsuario);
		if (usuario == null){
			JOptionPane.showMessageDialog(dialogLoginSignup, "Usuario no encontrado");
			return;
		}
		if (! usuario.getClaveAcceso().equals(new String(claveAcceso))){
			JOptionPane.showMessageDialog(dialogLoginSignup, "Contraseña Incorrecta");
		}
		LOGIN_CORRECTO = true;
	}

	public Usuario getDatosUsuario (){
		return obtenerUsuario(dialogLoginSignup.getBoxCorreo());
	}

	private void registrarUsuario (){
		JOptionPane.showMessageDialog(dialogLoginSignup, "Boton de Registro");
	}

	private Usuario obtenerUsuario (String nombreUsuario){
		try (InputStream inputStream = new FileInputStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();

			if (nombreUsuario.matches("^(?i)admin.*$")){
				ROL = "ADMIN";
			}else if (nombreUsuario.matches("^(?i)vip.*$")){
				ROL = "PREMIUM";
			}

			JsonArray usuariosDelRol = jsonObject.getJsonArray(ROL);
			for (JsonObject usuario : usuariosDelRol.getValuesAs(JsonObject.class)){
				if (usuario.getString("usuario").equalsIgnoreCase(nombreUsuario)){
					return new Usuario(usuario.getString("nombreCompleto"),
					                   usuario.getString("usuario"),
					                   usuario.getString("direccionEnvio"),
					                   usuario.getJsonNumber("telefonoContacto").longValue(),
					                   usuario.getString("claveAcceso"),
					                   getCarritoDeCompras(usuario.getInt("CID"))
					);
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return null;
	}

	private HashMap<Long, Integer> getCarritoDeCompras (int CID){
		return EventosCarrito.getCarritoDeCompras(CID);
	}

	private Usuario validarDatosRegistro (Object[] datosRegistro, HashMap<Long, Integer> carritoDeCompras){
		String nombreCompleto    = (String) datosRegistro[0];
		String correoElectronico = (String) datosRegistro[1];
		this.usuario = obtenerUsuario(correoElectronico);
		String direccion   = (String) datosRegistro[2];
		long   telefono    = (long) datosRegistro[3];
		String claveAcceso = (String) datosRegistro[4];

		if (this.usuario != null){
			JOptionPane.showMessageDialog(dialogLoginSignup, "Usuario ya registrado");
			return null;
		}
		return new Usuario(nombreCompleto, correoElectronico, direccion, telefono, claveAcceso, carritoDeCompras);
	}

	public boolean isLoginCorrecto (){
		return LOGIN_CORRECTO;
	}

	public String getRol (){
		return ROL;
	}

	@Override public void actionPerformed (ActionEvent e){
		switch (e.getActionCommand()){
			case "validarLogin" -> validarUsusarioLogin(dialogLoginSignup.getDatosLogin());
			case "validarRegistro" -> validarDatosRegistro(dialogLoginSignup.getDatosRegistro(), PanelCarrito.getCarritoDeComprasTemporal());
			case "actualizarDatosCliente" -> actualizarDatosCliente(panelPerfil.getDatosActualizados());
			case "cambiarContraseña" -> cambiarContrasena(dialogLoginSignup);
			default -> JOptionPane.showMessageDialog(dialogLoginSignup, "Boton no encontrado");
		}
	}

	private void cambiarContrasena (DialogLoginSignup dialogLoginSignup){

	}

	private void actualizarDatosCliente (Object[] datosActualizados){

	}

	public void setDialogLoginSignup (DialogLoginSignup dialogLoginSignup){
		this.dialogLoginSignup = dialogLoginSignup;
	}

	public void setPanelPerfil (PanelPerfil panelPerfil){
		this.panelPerfil = panelPerfil;
	}
}