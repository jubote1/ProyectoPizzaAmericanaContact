package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Usuario;
import conexion.ConexionBaseDatos;
import capaModelo.Producto;
import capaModelo.Tienda;
import capaModelo.TiendaBloqueada;

import org.apache.log4j.Logger;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda Bloqueada.
 * @author JuanDavid
 *
 */
public class TiendaBloqueadaDAO {
	
/**
 * M�todo que se encarga de retornar todas las entidades Tiendas Bloqueadas definidas en la base de datos
 * @return Se retorna un ArrayList con todas las entidades Tiendas Bloqueadas definidas en la base de datos.
 */
	public static ArrayList<TiendaBloqueada> retornarTiendasBloqueadas()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<TiendaBloqueada> tiendas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idtienda, b.nombre, a.comentario, a.fecha_bloqueo from tienda_bloqueada a, tienda b where a.idtienda = b.idtienda";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idTienda = rs.getInt("idtienda");
				String nombre = rs.getString("nombre");
				String comentario = rs.getString("comentario");
				String fecha_auditoria = rs.getString("fecha_bloqueo");
				TiendaBloqueada tien = new TiendaBloqueada(idTienda, nombre, comentario,fecha_auditoria);
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
	 * M�todo que se encarga de la inserci�n de una tienda bloqueada, con base en la informaci�n recibida como par�metro.
	 * @param pro Se recibe como par�metro un objeto Modelo TiendaBloqueada con base en el cual se realiza la inserci�n de una nueva entidad tienda
	 * en el sistema.
	 * @return no hay retorno.
	 */
	public static void insertarTiendaBloqueada(TiendaBloqueada tienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into tienda_bloqueada (idtienda, comentario) values ( " + tienda.getIdtienda() + " , '" + tienda.getComentario() + "' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
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
	 * M�todo que se encarga de la eliminaci�n de una tiendaBloqueada con base en los par�metros recibidos.
	 * @param idtienda Se revise como par�metro el idtienda de la entidad que se desea eliminar, no se retornan valores.
	 */
	public static void eliminarTiendaBloqueada(int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tienda_bloqueada  where idtienda = " + idtienda; 
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

	
	
}
