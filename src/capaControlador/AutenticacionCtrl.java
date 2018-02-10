package capaControlador;

import capaModelo.Usuario;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.UsuarioDAO;

/**
 * Clase AutenticacionCtrl tiene como objetivo hacer las veces de Controlador para la autenticaci�n de usuarios
 * en el aplicatiov
 * @author Juan David Botero Duque
 * @
 */
public class AutenticacionCtrl {
	
	
	private static AutenticacionCtrl instance;
	
	//singleton controlador
	public static AutenticacionCtrl getInstance(){
		if(instance == null){
			instance = new AutenticacionCtrl();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param usuario El usuario de logueo de la aplicaci�n
	 * @param contrasena Contrase�a asociada al usuario que se est� logueando
	 * @return Se retona un valor booleano indicando si el usuario y contrase�a corresponde con alguien logueado
	 * en al aplicaci�n
	 */
	public boolean autenticarUsuario(String usuario, String contrasena){
		Usuario usu = new Usuario(usuario, contrasena, "");
		boolean resultado = UsuarioDAO.validarUsuario(usu);
		return(resultado);
	}
	
	/**
	 * 
	 * @param usuario Se recibe el usuario de aplicaci�n con el fin de validar si el usuario pasado como par�metro est�
	 * o no logueado en la aplicaci�n
	 * @return Se retorna un valor booleano indicando si el usuario se encuentra o no logueado en el aplicativo.
	 */
	public String validarAutenticacion(String usuario)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Usuario usu = new Usuario(usuario);
		String resultado = UsuarioDAO.validarAutenticacion(usu);
		if (resultado.equals(new  String ("N")) ){
			Respuesta.put("respuesta", "OK");
    		
		} 
		else if(resultado.equals(new  String ("S"))){
			Respuesta.put("respuesta", "OKA");
    	
		}else
		{
			Respuesta.put("respuesta", "NOK");
		}
		Respuesta.put("nombreusuario", usu.getNombreLargo());
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}

}
