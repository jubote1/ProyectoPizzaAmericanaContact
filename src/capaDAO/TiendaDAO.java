package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Usuario;
import conexion.ConexionBaseDatos;
import capaModelo.Producto;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda.
 * @author JuanDavid
 *
 */
public class TiendaDAO {
	
/**
 * M�todo que se encarga de retornar todas las entidades Tiendas definidas en la base de datos
 * @return Se retorna un ArrayList con todas las entidades Tiendas definidas en la base de datos.
 */
	public static ArrayList<Tienda> obtenerTiendas()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Tienda> tiendas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tienda";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idTienda = rs.getInt("idtienda");
				String nombre = rs.getString("nombre");
				String dsn = rs.getString("dsn");
				String url = rs.getString("url");
				int pos = rs.getInt("pos");
				Tienda tien = new Tienda(idTienda, nombre, dsn, url, pos);
				tiendas.add(tien);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(tiendas);
		
	}
	
	/**
	 * M�todo que se encarga de la consulta de un idtienda con base en nombre de la tienda recibido como par�metro.
	 * @param nombreTienda Se recibe como par�metro un valor String con el nombre de la tienda.
	 * @return Se retorna el idtienda asociado al nombre de la tienda recibido como par�metro.
	 */
	public static int obteneridTienda(String nombreTienda)
	{
		Logger logger = Logger.getLogger("log_file");
		int idTienda=0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtienda from tienda where nombre = '"+nombreTienda + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idTienda = rs.getInt("idtienda");
				break;
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
		return(idTienda);
	}
	
	
	/**
	 * M�todo que se encarga de la inserci�n de una nueva tienda, con base en la informaci�n recibida como par�metro.
	 * @param pro Se recibe como par�metro un objeto Modelo Tienda con base en el cual se realiza la inserci�n de una nueva entidad tienda
	 * en el sistema.
	 * @return Se retorna un valor entero, que contiene el valor del idtienda asociado a la nueva tienda creada.
	 */
	public static int insertarTienda(Tienda pro)
	{
		Logger logger = Logger.getLogger("log_file");
		int idTiendaIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into tienda (nombre,dsn) values ( '" + pro.getNombreTienda() + "' , '" + pro.getDsnTienda() + "' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idTiendaIns = rs.getInt(1);
				
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
		return(idTiendaIns);
	}

	/**
	 * M�todo que se encarga de la eliminaci�n de una tienda con base en los par�metros recibidos.
	 * @param idtienda Se revise como par�metro el idtienda de la entidad que se desea eliminar, no se retornan valores.
	 */
	public static void eliminarTienda(int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tienda  where idtienda = " + idtienda; 
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
	 * M�todo que retorna una tienda, con base en el par�metro recibido de idtienda.
	 * @param idtienda Se recibe como par�metro valor entero que indica el idtienda que se desea retornar con sus valores
	 * @return Se retorna un objeto Modelo Tienda con la informaci�n de la entidad Tienda.
	 */
	public static Tienda retornarTienda(int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Tienda Pro = new Tienda(0,"","", "",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtienda,nombre,dsn,url,pos from  tienda  where idtienda = " + idtienda; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtien = 0;
			String nombre ="";
			String dsn = "";
			String url = "";
			int pos = 0;
			while(rs.next()){
				idtien = rs.getInt("idtienda");
				nombre = rs.getString("nombre");
				dsn = rs.getString("dsn");
				url = rs.getString("url");
				pos = rs.getInt("pos");
				break;
			}
			Pro = new Tienda(idtien,nombre,dsn,url,pos);
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
		return(Pro);
	}

	/**
	 * M�todo que permite la edici�n de la entidad tienda, con base en la informaci�n recibida como par�metro.
	 * @param Pro Se recibe como par�metro un objeto Modelo Tienda con base en los par�metros de este objeto
	 * se realiza la modificaci�n.
	 * @return se retorna en una variable tipo String el resultado del proceso.
	 */
	public static String editarTienda(Tienda Pro)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set nombre = '" + Pro.getNombreTienda() + "', dsn = '" + Pro.getDsnTienda() + "'  where idtienda = " + Pro.getIdTienda(); 
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
	
	/**
	 * M�todo que retorna un objeto de la clase tienda con la informaci�n de la URL y del dsn asociado a la tienda
	 * enviada como par�metro.
	 * @param idtienda Se recibe como par�metro el idtienda con base en el cual se realizar� la consulta
	 * @return Se retorna variable String con el valor del URL Servicio de la tienda.
	 */
	public static Tienda obtenerUrlTienda(int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Tienda tienda = new Tienda();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select url, dsn from tienda where idtienda = " + idtienda; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				tienda.setUrl(rs.getString("url"));
				tienda.setDsnTienda(rs.getString("dsn"));
				tienda.setIdTienda(idtienda);
				break;
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
			
		}
		return(tienda);
	}

	
}
