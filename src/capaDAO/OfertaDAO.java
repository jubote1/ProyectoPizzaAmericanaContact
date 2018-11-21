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
 * Clase que implementa todos los métodos de acceso a la base de datos para la administración de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaDAO {
	
	/**
	 * Método que se encarga de obtener todas la excepciones de precio parametrizadas en base de datos
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
	 * Método que se encarga de realizar la inserción de una ferta con base en la información recibida como 
	 * parámetro.
	 * @param Exc Recibe como parámetro un objeto de Modelo EOferta con base en el cual se realiza la inserción
	 * de la información.
	 * @return Se retorna un número entero con el idoferta retornado en la inserción a la base de datos.
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
	 * Método qeu se encarga de eliminar una oferta de precio con base en la información enviadad como parámetro.
	 * @param idoferta Recibe como parámetro el idexcepcion que desea ser eliminado.
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
	 * Método que se encarga de consultar una oferta con base en el parámetro recibido.
	 * @param idOferta Se recibe como parámetro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo Oferta que contiene la información el excepcion Precio consultada.
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
			while(rs.next()){
				nombreOferta = rs.getString("nombre_oferta");
				idExcepcion = rs.getInt("idexcepcion");
				break;
			}
			ofertaTemp = new Oferta(idOferta, nombreOferta, idExcepcion);
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
	 * Método que permite editar una excepción Precio con base en la información enviada como parámetro.
	 * @param Esc Recibe como parámetro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edición.
	 * @return Retorna un string con el resultado del proceso de edición.
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
	
	

}
