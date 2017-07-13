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
import capaControlador.PedidoCtrl;

/**
 * Servlet implementation class InsertarDetalleAdicion
 */
@WebServlet("/InsertarDetalleAdicion")
public class InsertarDetalleAdicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertarDetalleAdicion() {
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
				HttpSession sesion = request.getSession();
				int iddetallepedidopadre = Integer.parseInt(request.getParameter("iddetallepedidopadre"));
		        int iddetallepedidoadicion = Integer.parseInt(request.getParameter("iddetallepedidoadicion"));
		        int  idespecialidad1;
		        int  idespecialidad2;
		        try
		        {
		        	idespecialidad1 = Integer.parseInt(request.getParameter("idespecialidad1"));
		        }catch(Exception e)
		        {
		        	idespecialidad1 = 0;
		        }
		        try
		        {
		        	idespecialidad2 = Integer.parseInt(request.getParameter("idespecialidad2"));
		        }catch(Exception e)
		        {
		        	idespecialidad2 = 0;
		        }
		        double cantidad1;
		        double cantidad2;
		        try
		        {
		        	cantidad1 =  Double.parseDouble(request.getParameter("cantidad1"));
		        }catch(Exception e)
		        {
		        	cantidad1 = 0;
		        }
		        try
		        {
		        	cantidad2 =  Double.parseDouble(request.getParameter("cantidad2"));
		        }catch(Exception e)
		        {
		        	cantidad2 = 0;
		        }
		        logger.info("Llamado a servicio InsertarDetalleAdicion con parámetros iddetallepedidopadre: "
		        		+ iddetallepedidopadre + " iddetallepedidoadicion:  " + iddetallepedidoadicion + " idespecialidad1: " + idespecialidad1
		        		+ " idespecialidad2: " + idespecialidad2 + " cantidad1: " + cantidad1 + " cantidad2: " + cantidad2 );
		        PedidoCtrl PedidoCtrl = new PedidoCtrl();
		        String respuesta = PedidoCtrl.InsertarDetalleAdicion(iddetallepedidopadre, iddetallepedidoadicion,idespecialidad1, idespecialidad2, cantidad1, cantidad2);
		        System.out.println(respuesta);
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
