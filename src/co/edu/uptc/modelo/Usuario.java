package co.edu.uptc.modelo;

import java.util.ArrayList;

public class Usuario{
	private String           nombreCompleto;
	private String           correoElectronico;
	private String           direccionEnvio;
	private long             telefonoContacto;
	private String           claveAcceso;
	private String           tipoCliente;
	private ArrayList<Libro> carritoDeCompras;
	private int              CID = 0; //Carrito ID

	public Usuario (){
	}

	public Usuario (String nombreCompleto, String correoElectronico, String direccionEnvio, long telefonoContacto, String claveAcceso, String tipoCliente, int CID){
		this.nombreCompleto    = nombreCompleto;
		this.correoElectronico = correoElectronico.toUpperCase();
		this.direccionEnvio    = direccionEnvio;
		this.telefonoContacto  = telefonoContacto;
		this.claveAcceso       = claveAcceso;
		this.tipoCliente       = tipoCliente.toUpperCase();
		if (CID > 0){
			this.CID = CID;
		}
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

	public ArrayList<Libro> getCarritoDeCompras (){
		return carritoDeCompras;
	}

	public void setCarritoDeCompras (ArrayList<Libro> paramCarritoDeCompras){
		carritoDeCompras = paramCarritoDeCompras;
	}

	public int getCID (){
		return CID;
	}
}