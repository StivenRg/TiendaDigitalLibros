package co.edu.uptc.controlador;

import co.edu.uptc.gui.EventosBotones;
import co.edu.uptc.gui.FramePrincipal;
import co.edu.uptc.gui.LoginSignup;
import co.edu.uptc.gui.PantallaPrincipal;
import co.edu.uptc.modelo.Usuario;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class EventosUsuario implements ActionListener{
	private              LoginSignup    panelLoginSignup;
	private              Usuario        usuario;
	private              FramePrincipal framePrincipal;
	private              EventosBotones eventosBotones;
	private static       boolean        LOGIN_CORRECTO = false;
	private static final String         RUTA_USUARIOS  = "/co/edu/uptc/persistencia/USUARIOS.json";

	public EventosUsuario (FramePrincipal framePrincipal){
		this.framePrincipal = framePrincipal;
	}

	private void validarUsusarioLogin (JTextField txNombreUsuario, JPasswordField pwClaveAcceso){
		String nombreUsuario = txNombreUsuario.getText();
		char[] claveAcceso   = pwClaveAcceso.getPassword();
		this.usuario = obtenerUsuario(nombreUsuario);
		if (usuario == null){
			JOptionPane.showMessageDialog(panelLoginSignup, "Usuario no encontrado");
			return;
		}
		if (! usuario.getClaveAcceso().equals(new String(claveAcceso))){
			JOptionPane.showMessageDialog(panelLoginSignup, "Contraseña Incorrecta");
		}
		iniciarSesion();
	}

	private void iniciarSesion (){
		LOGIN_CORRECTO = true;
		framePrincipal.repintar(new PantallaPrincipal(framePrincipal));
	}

	private void registrarUsuario (){
		JOptionPane.showMessageDialog(panelLoginSignup, "Boton de Registro");
		if (validarDatosRegistro(panelLoginSignup.getDatosRegistro())){

		}
	}

	private static Usuario obtenerUsuario (String nombreUsuario){
		try (InputStream inputStream = EventosUsuario.class.getResourceAsStream(RUTA_USUARIOS); JsonReader reader = Json.createReader(inputStream)){
			JsonObject jsonObject = reader.readObject();

			String ROL = "REGULAR";
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
					                   usuario.getInt("telefonoContacto"),
					                   usuario.getString("claveAcceso"),
					                   ROL,
					                   ROL.equals("ADMIN") ? 0 : usuario.getInt("CID")
					);
				}
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		return null;
	}

	@Override public void actionPerformed (ActionEvent e){
		panelLoginSignup = framePrincipal.getPanelLoginSignup();
		switch (e.getActionCommand()){
			case "validarLogin" -> validarUsusarioLogin(panelLoginSignup.getBoxCorreo(), panelLoginSignup.getPasswordFieldContrasena());
			case "validarRegistro" -> validarDatosRegistro(panelLoginSignup.getDatosRegistro());
			default -> JOptionPane.showMessageDialog(panelLoginSignup, "Boton no encontrado");
		}
	}

	private boolean validarDatosRegistro (Object paramDatosRegistro){
		return true;
	}
}