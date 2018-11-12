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
 * Servlet implementation class CRUDExcepcionPrecio
 * Servicio que implementa los servicios que responden al CRUD de la entidad Excepcion Precio.
 */
@WebServlet("/CRUDExcepcionPrecio")
public class CRUDExcepcionPrecio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDExcepcionPrecio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Se implementa el CRUD para la entidad Excepci�n Precio, se recibe como par�metro principal el idoperacion
	 * 1 insertar 2 editar 3 Eliminar  4 Consultar, con base en el idoperacion se pediran el resto de par�metros.
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
		System.out.println("operacion " + idoperacion) ;
		try
		{
			operacion = Integer.parseInt(idoperacion);
		}catch(Exception e){
			operacion = 0;
		}
		if (operacion ==1)
		{
			int idproducto = Integer.parseInt(request.getParameter("idproducto"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			String descripcion = request.getParameter("descripcion");
			String incluye_liquido = request.getParameter("incluye_liquido");
			int idtipoliquido = Integer.parseInt(request.getParameter("idtipoliquido"));
			String habilitado = request.getParameter("habilitado");
			respuesta = ParametrosCtrl.insertarExcepcionPrecio(idproducto, precio, descripcion, incluye_liquido, idtipoliquido,habilitado);
		}else if (operacion ==2)
		{
			int idexcedit= Integer.parseInt(request.getParameter("idexcepcion"));
			int idproducto = Integer.parseInt(request.getParameter("idproducto"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			String descripcion = request.getParameter("descripcion");
			String incluye_liquido = request.getParameter("incluye_liquido");
			int idtipoliquido = Integer.parseInt(request.getParameter("idtipoliquido"));
			String habilitado = request.getParameter("habilitado");
			respuesta = ParametrosCtrl.editarExcepcionPrecio(idexcedit, idproducto, precio, descripcion, incluye_liquido, idtipoliquido,habilitado);
		}else if (operacion ==3 )
		{
			int idexceli = Integer.parseInt(request.getParameter("idexcepcion"));
			respuesta = ParametrosCtrl.eliminarExcepcionPrecio(idexceli);
		}else if (operacion == 4)
		{
			int idexccon = Integer.parseInt(request.getParameter("idexcepcion"));
			respuesta = ParametrosCtrl.retornarExcepcionPrecio(idexccon);
		}else if(operacion == 5)
		{
			respuesta = ParametrosCtrl.retornarExcecionesPrecio();
		}else if(operacion == 6)
		{
			respuesta = ParametrosCtrl.retornarExcecionesPrecioGrid();
		}
		//System.out.println(respuesta);
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
