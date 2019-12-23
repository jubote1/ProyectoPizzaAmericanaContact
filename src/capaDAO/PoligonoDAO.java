package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Usuario;
import conexion.ConexionBaseDatos;
import capaModelo.Poligono;
import capaModelo.Producto;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda.
 * @author JuanDavid
 *
 */
public class PoligonoDAO {
	
/**
 * M�todo que se encarga de retornar todas las entidades Poligonos definidas en la base de datos
 * @return Se retorna un ArrayList con todas las entidades Poligono definidas en la base de datos.
 */
	public static ArrayList<Poligono> obtenerPoligonos()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Poligono> poligonos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeolocalizacion();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from poligono";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idPoligono = rs.getInt("idpoligono");
				String nombre = rs.getString("nombre_poligono");
				String ubicacion = rs.getString("ubicacion_mapa");
				Poligono poli = new Poligono(idPoligono, nombre, ubicacion);
				poligonos.add(poli);
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
		return(poligonos);
		
	}
	
	
	
	/**
	 * M�todo que se encarga de la inserci�n de un nuevo poligono, con base en la informaci�n recibida como par�metro.
	 * @param pro Se recibe como par�metro un objeto Modelo Poligono con base en el cual se realiza la inserci�n de una nueva entidad Poligono
	 * en el sistema.
	 * @return Se retorna un valor entero, que contiene el valor del idtienda asociado a la nueva tienda creada.
	 */
	public static int insertarPoligono(Poligono poli)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPoligonoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeolocalizacion();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into poligono (nombre_poligono,ubicacion_mapa) values ( '" + poli.getNombrePoligo() + "' , '" + poli.getUbicacionMapa() + "' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPoligonoIns = rs.getInt(1);
				
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
		return(idPoligonoIns);
	}

	/**
	 * M�todo que se encarga de la eliminaci�n de una poligono con base en los par�metros recibidos.
	 * @param idtienda Se revise como par�metro el idpoligono de la entidad que se desea eliminar, no se retornan valores.
	 */
	public static void eliminarPoligono(int idPoligono)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeolocalizacion();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from poligono  where idpoligono = " + idPoligono; 
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
	 * M�todo que retorna un poligono, con base en el par�metro recibido de idpoligono.
	 * @param idpoligono Se recibe como par�metro valor entero que indica el idpoligono que se desea retornar con sus valores
	 * @return Se retorna un objeto Modelo Poligono con la informaci�n de la entidad Poligono.
	 */
	public static Poligono retornarPoligono(int idPoligono)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Poligono poli = new Poligono(0, "", "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from  poligono  where idpoligono = " + idPoligono; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			String nombrePoligono ="";
			String ubicacionMapa = "";
			while(rs.next()){
				nombrePoligono = rs.getString("nombre_poligono");
				ubicacionMapa = rs.getString("ubicacion_mapa");
				poli = new Poligono(idPoligono, nombrePoligono, ubicacionMapa);
				stm.close();
				con1.close();
				break;
			}
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
		return(poli);
	}

	/**
	 * M�todo que permite la edici�n de la entidad Poligono, con base en la informaci�n recibida como par�metro.
	 * @param Pro Se recibe como par�metro un objeto Modelo Poligono con base en los par�metros de este objeto
	 * se realiza la modificaci�n.
	 * @return se retorna en una variable tipo String el resultado del proceso.
	 */
	public static String editarPoligono(Poligono poli)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeolocalizacion();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update poligono set nombre_poligono = '" + poli.getNombrePoligo() + "', ubicacion_mapa = '" + poli.getUbicacionMapa() + "'  where idpoligono = " + poli.getIdPoligono(); 
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
