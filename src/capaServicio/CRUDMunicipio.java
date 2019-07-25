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
 * Servlet implementation class CRUDMunicipio
 * Servicio que implementa el CRUD para la entidad Municipio.
 */
@WebServlet("/CRUDMunicipio")
public class CRUDMunicipio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRUDMunicipio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Se recibe como par�metro principal el id operaci�n de acuerdo a los siguientes valores 
	 * 1 insertar 2 editar 3 Eliminar  4 Consultar, de acuerdo a esto se pedir�n el resto del par�metros y se invocar� el m�todo correspondiente en la capa controlador.
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
					String nombre = request.getParameter("nombre");
					String dsn = request.getParameter("dsn");
					
				}else if (operacion ==2)
				{
					int idtienda= Integer.parseInt(request.getParameter("idtienda"));
					String nombre = request.getParameter("nombre");
					String dsn = request.getParameter("dsn");
					
				}else if (operacion ==3 )
				{
					int idtienda = Integer.parseInt(request.getParameter("idtienda"));
					
				}else if (operacion == 4)
				{
					int idtiecon = Integer.parseInt(request.getParameter("idtienda"));
					
				}else if(operacion == 5)
				{
					respuesta = ParametrosCtrl.retornarMunicipios();
				}else if(operacion == 6)
				{
					respuesta = ParametrosCtrl.retornarMunicipios();
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
