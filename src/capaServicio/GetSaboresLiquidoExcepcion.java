package capaServicio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import capaControlador.PedidoCtrl;

/**
 * Servlet implementation class GetSaboresLiquidoExcepcion
 * Servicio que toma par�metro el idexcepci�n pasado como par�metro y con base en este retorna en formato JSON
 * los liquidos parametrizados para la excepci�n de precio.
 */
@WebServlet("/GetSaboresLiquidoExcepcion")
public class GetSaboresLiquidoExcepcion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSaboresLiquidoExcepcion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * El servicio recibe como par�metro idExcepcion, con base en este invocando al m�todo ObtenerSaboresLiquidoExcepcion(idExcep);
	 * de la capa Pedido controlador, retorna en formato JSON los tipos liquidos asociados a la excepci�n de precio, esto
	 * con el fin de pintarlo en la capa de presentaci�n.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession sesion = request.getSession();
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/json");
			String idExcepcion = request.getParameter("idExcepcion");
			int idExcep = Integer.parseInt(idExcepcion);
			int idtienda;
			try
			{
				idtienda = Integer.parseInt(request.getParameter("idtienda"));
			}catch(Exception e)
			{
				idtienda = 0;
			}
			PedidoCtrl PedidoCtrl = new PedidoCtrl();
			String respuesta = PedidoCtrl.ObtenerSaboresLiquidoExcepcion(idExcep, idtienda);
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
