package capaServicio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import capaControlador.ClienteCtrl;
import capaControlador.PromocionesCtrl;
import capaModelo.Usuario;;

/**
 * Servlet implementation class GetCliente
 * Servicio que se encarga de consultar todos los registros que tiene asociado un cliente en la tabla de clientes, dando 
 * como par�metro un tel�fono determinado.
 */
@WebServlet("/GetUsarCodigoPromocional")
public class GetUsarCodigoPromocional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsarCodigoPromocional() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * El servicio recibe como par�metro del tel�fono el cual es manejado como un String, con base en esto se retorna
	 * en formato JSON todos los registros que tiene asociado el cliente en la tabla de clientes con el tel�fono indicado.
	 * Lo anterior invocando el m�todo obtenerCliente(tel) de la capa controlador cliente.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			HttpSession sesion = request.getSession();
			String strIdOfertaCliente = request.getParameter("idofertacliente");
			int idOfertaCliente = 0;
			try
			{
				idOfertaCliente = Integer.parseInt(strIdOfertaCliente);
			}catch(Exception e)
			{
				idOfertaCliente = 0;
			}
			//Capturamos la informaci�n de la sesion del usuario que est� ejecutando la inserci�n
			Usuario usuario = (Usuario) sesion.getAttribute("usuario");
			String usuarioUso = usuario.getNombreUsuario();
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/json");
			PromocionesCtrl promoCtrl = new PromocionesCtrl();
			String respuesta = promoCtrl.actualizarUsoOferta(idOfertaCliente, usuarioUso);
			PrintWriter out = response.getWriter();
			out.write(respuesta);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
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
