package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.EstadoPedido;
import capaModelo.ExcepcionPrecio;
import capaModelo.Oferta;
import capaModelo.Tienda;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;
/**
 * Clase que implementa todos los m�todos de acceso a la base de datos para la administraci�n de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaDAO {
	
	/**
	 * M�todo que se encarga de obtener todas la excepciones de precio parametrizadas en base de datos
	 * @return Retorna un ArrayList con objetos de Modelo Oferta.
	 */
	public static ArrayList<Oferta> obtenerOfertas()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Oferta> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from oferta";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOferta;
			String nombreOferta;
			int idExcepcion;
			Oferta ofertaTemp = new Oferta(0,"",0);
			while(rs.next()){
				idOferta = rs.getInt("idoferta");
				nombreOferta = rs.getString("nombre_oferta");
				idExcepcion = rs.getInt("idexcepcion");
				ofertaTemp = new Oferta(idOferta,nombreOferta, idExcepcion);
				ofertas.add(ofertaTemp);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(ofertas);
		
	}
	
	public static ArrayList<Oferta> obtenerOfertasGrid()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Oferta> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idoferta, a.nombre_oferta, a.idexcepcion, b.descripcion from oferta a left outer join excepcion_precio b on a.idexcepcion = b.idexcepcion ";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOferta;
			String nombreOferta;
			int idExcepcion;
			String nombreExcepcion = "";
			Oferta ofertaTemp = new Oferta(0,"",0);
			while(rs.next()){
				idOferta = rs.getInt("idoferta");
				nombreOferta = rs.getString("nombre_oferta");
				idExcepcion = rs.getInt("idexcepcion");
				nombreExcepcion = rs.getString("descripcion");
				ofertaTemp = new Oferta(idOferta,nombreOferta, idExcepcion);
				ofertaTemp.setNombreExcepcion(nombreExcepcion);
				ofertas.add(ofertaTemp);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(ofertas);
		
	}
	
		
	/**
	 * M�todo que se encarga de realizar la inserci�n de una ferta con base en la informaci�n recibida como 
	 * par�metro.
	 * @param Exc Recibe como par�metro un objeto de Modelo EOferta con base en el cual se realiza la inserci�n
	 * de la informaci�n.
	 * @return Se retorna un n�mero entero con el idoferta retornado en la inserci�n a la base de datos.
	 */
	public static int insertarOferta(Oferta ofer)
	{
		Logger logger = Logger.getLogger("log_file");
		int idOfertaIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into oferta (nombre_oferta,idexcepcion) values ('" + ofer.getNombreOferta() + "' ," + ofer.getIdExcepcion() +")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idOfertaIns = rs.getInt(1);
				logger.info("idOferta insertado es " + idOfertaIns);
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(0);
		}
		return(idOfertaIns);
	}

	/**
	 * M�todo qeu se encarga de eliminar una oferta de precio con base en la informaci�n enviadad como par�metro.
	 * @param idoferta Recibe como par�metro el idexcepcion que desea ser eliminado.
	 */
	public static void eliminarOferta(int idOferta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from oferta  where idoferta = " + idOferta; 
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		
	}

	/**
	 * M�todo que se encarga de consultar una oferta con base en el par�metro recibido.
	 * @param idOferta Se recibe como par�metro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo Oferta que contiene la informaci�n el excepcion Precio consultada.
	 */
	public static Oferta retornarOferta(int idOferta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Oferta ofertaTemp = new Oferta(0,"",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from  oferta  where idoferta = " + idOferta; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			String nombreOferta = "";
			int idExcepcion = 0;
			int diasCaducidad = 0;
			String tipoCaducidad = "";
			double descuentoFijoPorcentaje = 0, descuentoFijoValor = 0;
			while(rs.next()){
				nombreOferta = rs.getString("nombre_oferta");
				idExcepcion = rs.getInt("idexcepcion");
				descuentoFijoPorcentaje = rs.getDouble("descuento_fijo_porcentaje");
				descuentoFijoValor = rs.getDouble("descuento_fijo_valor");
				try {
					diasCaducidad = rs.getInt("dias_caducidad");
					
				}catch(Exception e)
				{
					diasCaducidad = 0;
				}
				tipoCaducidad = rs.getString("tipo_caducidad");
				break;
			}
			ofertaTemp = new Oferta(idOferta, nombreOferta, idExcepcion);
			ofertaTemp.setDiasCaducidad(diasCaducidad);
			ofertaTemp.setTipoCaducidad(tipoCaducidad);
			ofertaTemp.setDescuentoFijoPorcentaje(descuentoFijoPorcentaje);
			ofertaTemp.setDescuentoFijoValor(descuentoFijoValor);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(ofertaTemp);
	}

	/**
	 * M�todo que permite editar una excepci�n Precio con base en la informaci�n enviada como par�metro.
	 * @param Esc Recibe como par�metro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edici�n.
	 * @return Retorna un string con el resultado del proceso de edici�n.
	 */
	public static String editarOferta(Oferta ofertaEdi)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update oferta set nombre_oferta = '"+ ofertaEdi.getNombreOferta() +"' , idexcepcion =" + ofertaEdi.getIdExcepcion() + "  where idoferta = " + ofertaEdi.getIdOferta(); 
			logger.info(update);
			stm.executeUpdate(update);
			resultado = "exitoso";
			stm.close();
			con1.close();
			
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			resultado = "error";
		}
		return(resultado);
	}
	
	public static boolean manejaCodigoOferta(int idOferta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select codigo_promocional from  oferta  where idoferta = " + idOferta; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			String codigoPromocional = "";
			while(rs.next()){
				codigoPromocional = rs.getString("codigo_promocional");
				if(codigoPromocional.equals(new String("S")))
				{
					respuesta = true;
				}else
				{
					respuesta = false;
				}
				break;
			}
			stm.close();
			rs.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(respuesta);
	}


}
