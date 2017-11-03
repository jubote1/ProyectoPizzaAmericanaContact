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
 * Servlet implementation class FinalizarPedido
 * Servicio que se encarga de cerrar un pedido, totalizar el valor del pedido,y cambiar el estado del pedido a finalizado.
 * 
 */
@WebServlet("/FinalizarPedidoFinalizarPedido.java")
public class FinalizarPedidoReenvio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizarPedidoReenvio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * El servicio recibe como parámetro el idpedido, el idformapgo, el idcliente asociado al pedido, un marcador que nos
	 * indica si el cliente fue insertado o por el contrario actualizado, valor de la forma pago del cliente, con los datos
	 * anteriores se invocará el método de la capa controlador pedido FinalizarPedido.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession sesion = request.getSession();
		int idpedido = Integer.parseInt(request.getParameter("idpedido"));
		int idformapago;
		int idcliente;
		int insertado;
		double valorformapago;
		double valortotal;
		try
		{
			idformapago = Integer.parseInt(request.getParameter("idformapago"));
		}catch(Exception e)
		{
			idformapago = 0;
		}
		try{
			valorformapago = Double.parseDouble(request.getParameter("valorformapago"));
		}catch(Exception e)
		{
			valorformapago = 0;
		}
		try{
			valortotal = Double.parseDouble(request.getParameter("valortotal"));
		}catch(Exception e)
		{
			valortotal = 0;
		}
		try{
			idcliente = Integer.parseInt(request.getParameter("idcliente"));
		}catch(Exception e)
		{
			idcliente = 0;
		}
		try{
			insertado = Integer.parseInt(request.getParameter("insertado"));
		}catch(Exception e)
		{
			insertado = 0;
		}
		PedidoCtrl PedidoCtrl = new PedidoCtrl();
        String respuesta = PedidoCtrl.FinalizarPedidoReenvio(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado);
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
