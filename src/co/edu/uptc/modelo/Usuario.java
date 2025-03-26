package co.edu.uptc.modelo;

import co.edu.uptc.controlador.EventosUsuario;

import java.util.HashMap;

public class Usuario{
	private String                 nombreCompleto;
	private String                 correoElectronico;
	private String                 direccionEnvio;
	private long                   telefonoContacto;
	private String                 claveAcceso;
	private String                 tipoCliente;
	private HashMap<Long, Integer> carritoDeCompras;
	private int                    CID; //Carrito ID

	public Usuario (){}

	public Usuario (
			String nombreCompleto,
			String correoElectronico,
			String direccionEnvio,
			long telefonoContacto,
			String claveAcceso,
			HashMap<Long, Integer> carritoDeCompras
	){
		this.nombreCompleto    = nombreCompleto;
		this.correoElectronico = correoElectronico.toUpperCase();
		this.direccionEnvio    = direccionEnvio;
		this.telefonoContacto  = telefonoContacto;
		this.claveAcceso       = claveAcceso;
		this.tipoCliente       = validarTipoDeCliente(correoElectronico);
		this.CID               = getCID_Index();
		this.carritoDeCompras  = carritoDeCompras;
	}

	private String validarTipoDeCliente (String correoElectronico){
		if (correoElectronico.matches("^(?i)admin.*$")){
			return "ADMIN";
		}else if (correoElectronico.matches("^(?i)vip.*$")){
			return "PREMIUM";
		}
		return "REGULAR";
	}

	public void actualizarDatos (String nombreCompleto, String correoElectronico, String direccionEnvio, long telefonoContacto){
		this.nombreCompleto    = nombreCompleto;
		this.correoElectronico = correoElectronico;
		this.direccionEnvio    = direccionEnvio;
		this.telefonoContacto  = telefonoContacto;
	}

	public String getNombreCompleto (){
		return nombreCompleto;
	}

	public void setNombreCompleto (String paramNombreCompleto){
		nombreCompleto = paramNombreCompleto;
	}

	public String getCorreoElectronico (){
		return correoElectronico;
	}

	public void setCorreoElectronico (String paramCorreoElectronico){
		correoElectronico = paramCorreoElectronico;
	}

	public String getDireccionEnvio (){
		return direccionEnvio;
	}

	public void setDireccionEnvio (String paramDireccionEnvio){
		direccionEnvio = paramDireccionEnvio;
	}

	public long getTelefonoContacto (){
		return telefonoContacto;
	}

	public void setTelefonoContacto (long paramTelefonoContacto){
		telefonoContacto = paramTelefonoContacto;
	}

	public String getClaveAcceso (){
		return claveAcceso;
	}

	public void setClaveAcceso (String paramClaveAcceso){
		claveAcceso = paramClaveAcceso;
	}

	public String getTipoCliente (){
		return tipoCliente;
	}

	public void setTipoCliente (String paramTipoCliente){
		tipoCliente = paramTipoCliente;
	}

	public HashMap<Long, Integer> getCarritoDeCompras (){
		return carritoDeCompras;
	}

	public void setCarritoDeCompras (HashMap<Long, Integer> paramCarritoDeCompras){
		carritoDeCompras = paramCarritoDeCompras;
	}

	public int getCID (){
		return CID;
	}

	private static int getCID_Index (){
		return EventosUsuario.getCID_Index();
	}
}