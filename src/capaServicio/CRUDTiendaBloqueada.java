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
import capaControlador.TiendaBloqueadaCtrl;

/**
 * Servlet implementation class CRUDTienda
 * Servicio que implementa los m�todos para soportar el CRUD de la entidad Tienda.
 */
@WebServlet("/CRUDTiendaBloqueada")
public class CRUDTiendaBloqueada extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDTiendaBloqueada() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Se recibe como par�metro principal el idoperacion con base en lo siguiente: 
	 * 1 insertar 2 editar 3 Eliminar  4 Consultar, de acuerdo a la operaci�n seleccionada se 
	 * solicitar�n ciertos par�metros y posteriormente se invocar� al m�todo correspondiente en la capa controlador.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Operaci�n idoperacion 1 insertar 3 Eliminar  4 Consultar
		HttpSession sesion = request.getSession();
		response.addHeader("Access-Control-Allow-Origin", "*");
		String idoperacion = request.getParameter("idoperacion");
		TiendaBloqueadaCtrl TiendaCtrl = new TiendaBloqueadaCtrl();
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
			int idtienda = Integer.parseInt(request.getParameter("idtienda"));
			String comentario = request.getParameter("comentario");
			respuesta = TiendaCtrl.insertarTiendaBloqueada(idtienda, comentario);
		}else if (operacion ==2)
		{
			
		}else if (operacion ==3 )
		{
			int idtienda = Integer.parseInt(request.getParameter("idtienda"));
			respuesta = TiendaCtrl.eliminarTiendaBloqueada(idtienda);
		}else if (operacion == 4)
		{
			
		}else if(operacion == 5)
		{
			respuesta = TiendaCtrl.retornarTiendasBloqueadas();
		}else if(operacion == 6)
		{
			
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
