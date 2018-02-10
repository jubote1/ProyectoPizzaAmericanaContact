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

/**
 * Servlet implementation class CRUDProducto
 * Servicio que se encarga de implementar el CRUD de la entidad Producto.
 */
@WebServlet("/CRUDProductoNoExistente")
public class CRUDProductoNoExistente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDProductoNoExistente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Se soportan las operaciones para el CRUD de la entidad Producto, se recibe como par�metro principal
	 * el idoperaci�n de acuerdo a los siguientes valores 1 insertar 2 editar 3 Eliminar  4 Consultar, 
	 * con base en la operaci�n se solicitar�n el resto de par�metros y se invocar� el m�todo correspondiente en la capa controladora.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Operaci�n idoperacion 1 insertar 2 editar 3 Eliminar  4 Consultar
				response.addHeader("Access-Control-Allow-Origin", "*");
				HttpSession sesion = request.getSession();
				response.addHeader("Access-Control-Allow-Origin", "*");
				String idoperacion = request.getParameter("idoperacion");
				ParametrosCtrl ParametrosCtrl = new ParametrosCtrl();
				int operacion;
				String respuesta="";
				try
				{
					operacion = Integer.parseInt(idoperacion);
				}catch(Exception e){
					operacion = 0;
				}
				if (operacion ==1)
				{
					int idtienda;
					try
					{
						idtienda = Integer.parseInt(request.getParameter("idtienda"));
					}catch(Exception e)
					{
						System.out.println(e.toString());
						idtienda = 0;
					}
					
					int idproducto;
					try
					{
						idproducto = Integer.parseInt(request.getParameter("idproducto"));
					}catch(Exception e)
					{
						idproducto = 0;
					}
					
					
					respuesta = ParametrosCtrl.insertaProductoNoExistente(idtienda, idproducto);
				}else if (operacion ==2)
				{
					
					int idtienda;
					try
					{
						idtienda = Integer.parseInt(request.getParameter("idtienda"));
					}catch(Exception e)
					{
						idtienda = 0;
					}
					
					int idproducto;
					try
					{
						idproducto = Integer.parseInt(request.getParameter("idproducto"));
					}catch(Exception e)
					{
						idproducto = 0;
					}
					respuesta = ParametrosCtrl.editarProductoNoExistente(idtienda, idproducto);
				}else if (operacion ==3 )
				{
					int idtienda;
					try
					{
						idtienda = Integer.parseInt(request.getParameter("idtienda"));
					}catch(Exception e)
					{
						idtienda = 0;
					}
					
					int idproducto;
					try
					{
						idproducto = Integer.parseInt(request.getParameter("idproducto"));
					}catch(Exception e)
					{
						idproducto = 0;
					}
					respuesta = ParametrosCtrl.eliminarProductoNoExistente(idtienda, idproducto);
				}else if (operacion == 4)
				{
					int idtienda;
					try
					{
						idtienda = Integer.parseInt(request.getParameter("idtienda"));
					}catch(Exception e)
					{
						idtienda = 0;
					}
					
					int idproducto;
					try
					{
						idproducto = Integer.parseInt(request.getParameter("idproducto"));
					}catch(Exception e)
					{
						idproducto = 0;
					}
					respuesta = ParametrosCtrl.retornarProductoNoExistente(idtienda, idproducto);
				}else if(operacion == 5)
				{
					respuesta = ParametrosCtrl.retornarProductosNoExistentes();
				}else if(operacion == 6)
				{
					System.out.println("hola pase");
					respuesta = ParametrosCtrl.retornarProductosNoExistentesGrid();
				}
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
