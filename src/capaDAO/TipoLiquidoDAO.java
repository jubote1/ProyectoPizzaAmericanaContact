package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.TipoLiquido;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;	

public class TipoLiquidoDAO {
	
	public static ArrayList<TipoLiquido> obtenerTiposLiquido()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<TipoLiquido> tipos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_liquido";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idtipoliquido = rs.getInt("idtipo_liquido");
				String nombre = rs.getString("nombre");
				String capacidad = rs.getString("capacidad");
				TipoLiquido liquido = new TipoLiquido(idtipoliquido,nombre,capacidad);
				tipos.add(liquido);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(tipos);
		
	}
	
		
	public static int insertarTipoLiquido(TipoLiquido pro)
	{
		Logger logger = Logger.getLogger("log_file");
		int idTipoLiquidoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into tipo_liquido (nombre,capacidad) values ( '" + pro.getNombre() + "' , '" + pro.getCapacidad() + "' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idTipoLiquidoIns = rs.getInt(1);
				
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		return(idTipoLiquidoIns);
	}

	public static void eliminarTipoLiquido(int idtipoliquido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tipo_liquido  where idtipo_liquido = " + idtipoliquido; 
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		
	}

	public static TipoLiquido retornarTipoProducto(int idtipoliquido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		TipoLiquido Pro = new TipoLiquido(0, "","");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtipo_liquido,nombre,capacidad from  tipo_liquido  where idtipo_liquido = " + idtipoliquido; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtipliq = 0;
			String nombre ="";
			String capacidad = "";
			while(rs.next()){
				idtipliq = rs.getInt("idtipo_liquido");
				nombre = rs.getString("nombre");
				capacidad = rs.getString("capacidad");
				break;
			}
			Pro = new TipoLiquido(idtipliq,nombre,capacidad);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		return(Pro);
	}

	public static String editarTipoLiquido(TipoLiquido Pro)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tipo_liquido set nombre = '" + Pro.getNombre() + "', capacidad = '" + Pro.getCapacidad() + "' where idtipo_liquido = " + Pro.getIdtipo_liquido(); 
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
