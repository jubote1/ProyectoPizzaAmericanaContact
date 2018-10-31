package capaServicio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;;

/**
 * Servlet implementation class InsertarEspecialidad
 * M�todo que se encarga recibir los par�metros para la inserci�n de una especialidad.
 */
@WebServlet("/ConsultarDireccionesPedido")
public class ConsultarDireccionesPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarDireccionesPedido() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Servicio que recibe los par�metros para llamar al m�todo insertarEspecialidad de la capa Parametros controlador,
	 * e insertar la especialidad.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession sesion = request.getSession();
		String fechaInicial = request.getParameter("fechainicial");
		String fechaFinal = request.getParameter("fechafinal");
		String idMunicipio = request.getParameter("idmunicipio");
		PedidoCtrl pedCtrl = new PedidoCtrl();
		String respuesta = pedCtrl.ConsultarDireccionesPedido(fechaInicial, fechaFinal, idMunicipio);
        PrintWriter out = response.getWriter();
		out.write(respuesta);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
