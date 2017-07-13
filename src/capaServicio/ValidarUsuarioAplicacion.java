package capaServicio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import capaControlador.AutenticacionCtrl;
import capaModelo.Usuario;

/**
 * Servlet implementation class ValidarUsuarioAplicacion
 */
@WebServlet("/ValidarUsuarioAplicacion")
public class ValidarUsuarioAplicacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidarUsuarioAplicacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				response.addHeader("Access-Control-Allow-Origin", "*");
				Logger logger = Logger.getLogger("log_file");
				HttpSession miSesion = (HttpSession) request.getSession();
				Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
				boolean resultado;
				//Al no existir el usuario logueado es posible que produza una excepcion
				try
				{
					String user = usuario.getNombreUsuario();
					logger.info("Validando validez de autenticacion de usuario " + user);
					//Debemos de validar la existencia del usuario
					AutenticacionCtrl aut = new AutenticacionCtrl();
					resultado = aut.validarAutenticacion(user);
					logger.info("resultado de validación de autenticación de usuario " + user + " " + resultado);
				}catch(Exception e)
				{
					logger.error(e.toString());
					resultado = false;
				}
		        PrintWriter out = response.getWriter();
		        if (resultado){
		        		out.write("OK");
		        		//response.sendRedirect("http://localhost:8080/ProyectoPizzaAmericana/Pedidos.html");
		        }
		        else{
		        	   	out.write("NOK");
		        	
		        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
