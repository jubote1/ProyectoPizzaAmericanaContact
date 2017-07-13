package capaControlador;

import capaModelo.Usuario;
import capaDAO.UsuarioDAO;

public class AutenticacionCtrl {
	
	private static AutenticacionCtrl instance;
	
	//singleton controlador
	public static AutenticacionCtrl getInstance(){
		if(instance == null){
			instance = new AutenticacionCtrl();
		}
		return instance;
	}
	
	public boolean autenticarUsuario(String usuario, String contrasena){
		Usuario usu = new Usuario(usuario, contrasena, "");
		boolean resultado = UsuarioDAO.validarUsuario(usu);
		return(resultado);
	}
	
	public boolean validarAutenticacion(String usuario)
	{
		Usuario usu = new Usuario(usuario);
		boolean resultado = UsuarioDAO.validarAutenticacion(usu);
		return(resultado);
	}

}
