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
public class TiendaDAO {

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
				Tienda tien = new Tienda(idTienda, nombre, dsn);
				tiendas.add(tien);
			}
		}catch (Exception e){
			logger.info(e.toString());
		}
		return(tiendas);
		
	}
	
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
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(idTienda);
	}
	
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
			return(0);
		}
		return(idTiendaIns);
	}

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
			
		}
		
	}

	public static Tienda retornarTienda(int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Tienda Pro = new Tienda(0,"","");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtienda,nombre,dsn from  tienda  where idtienda = " + idtienda; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtien = 0;
			String nombre ="";
			String dsn = "";
			while(rs.next()){
				idtien = rs.getInt("idtienda");
				nombre = rs.getString("nombre");
				dsn = rs.getString("dsn");
				break;
			}
			Pro = new Tienda(idtien,nombre,dsn);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		return(Pro);
	}

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
			resultado = "error";
		}
		return(resultado);
	}

	
}
