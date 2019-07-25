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
import capaControlador.PedidoCtrl;
import capaModelo.MarcacionPedido;;

/**
 * Servlet implementation class InsertarEspecialidad
 * Método que se encarga recibir los parámetros para la inserción de una especialidad.
 */
@WebServlet("/InsertarMarcacionPedido")
public class InsertarMarcacionPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertarMarcacionPedido() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Servicio que recibe los parámetros para llamar al método insertarEspecialidad de la capa Parametros controlador,
	 * e insertar la especialidad.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession sesion = request.getSession();
		String observacion = request.getParameter("observacion");
		int idPedido = 0;
		int idMarcacion = 0;
		double descuento = 0;
		String motivo = "";
		try{
			idPedido = Integer.parseInt(request.getParameter("idpedido"));
		}catch(Exception e)
		{
			idPedido = 0;
		}
		try{
			idMarcacion = Integer.parseInt(request.getParameter("idmarcacion"));
		}catch(Exception e)
		{
			idMarcacion = 0;
		}
		try{
			descuento = Double.parseDouble(request.getParameter("descuento"));
		}catch(Exception e)
		{
			descuento = 0;
		}
		motivo = request.getParameter("motivo");
		PedidoCtrl pedCtrl = new PedidoCtrl();
		MarcacionPedido marPed = new MarcacionPedido(idPedido, idMarcacion, observacion, descuento, motivo);
		String respuesta = pedCtrl.InsertarMarcacionPedido(marPed);
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
