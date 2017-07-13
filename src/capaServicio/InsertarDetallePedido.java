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
 * Servlet implementation class InsertarDetallePedido
 */
@WebServlet("/InsertarDetallePedido")
public class InsertarDetallePedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertarDetallePedido() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession sesion = request.getSession();
		int idproducto = Integer.parseInt(request.getParameter("idproducto"));
        int idpedido = Integer.parseInt(request.getParameter("idpedido"));
        String  especialidad1;
        String  especialidad2;
        String modespecialidad1;
        String modespecialidad2;
        especialidad1 = request.getParameter("especialidad1");
        especialidad2 = request.getParameter("especialidad2");
        double cantidad =  Double.parseDouble(request.getParameter("cantidad"));
        double valorUnitario = Double.parseDouble(request.getParameter("valorunitario"));
        double valorTotal = Double.parseDouble(request.getParameter("valortotal"));
        String adicion = request.getParameter("adicion");
        String observacion = request.getParameter("observacion");
        modespecialidad1 = request.getParameter("modespecialidad1");
        modespecialidad2 = request.getParameter("modespecialidad2");
        int idsabortipoliquido = Integer.parseInt(request.getParameter("idsabortipoliquido"));
        int idexcepcion;
        try
        {
        	idexcepcion = Integer.parseInt(request.getParameter("idexcepcion"));
        }catch(Exception e)
        {
        	idexcepcion = 0;
        }
        PedidoCtrl PedidoCtrl = new PedidoCtrl();
        String respuesta = PedidoCtrl.InsertarDetallePedido(idproducto, idpedido,cantidad, especialidad1, especialidad2, valorUnitario, valorTotal,adicion, observacion, idsabortipoliquido, idexcepcion, modespecialidad1, modespecialidad2);
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
