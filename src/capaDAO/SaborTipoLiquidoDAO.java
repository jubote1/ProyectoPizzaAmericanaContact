package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.SaborLiquido;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;
public class SaborTipoLiquidoDAO {
	
	public static ArrayList<SaborLiquido> obtenerSaborLiquidos()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saborliquidos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from sabor_x_tipo_liquido";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idsaborxtipoliquido = rs.getInt("idsabor_x_tipo_liquido");
				String descripcion = rs.getString("descripcion");
				int idtipoliquido = rs.getInt("idtipo_liquido");
				SaborLiquido saborliquido = new SaborLiquido(idsaborxtipoliquido,descripcion,idtipoliquido);
				saborliquidos.add(saborliquido);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(saborliquidos);
		
	}
	
	public static ArrayList<SaborLiquido> obtenerSaborLiquidosGrid()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saborliquidos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idsabor_x_tipo_liquido, a.descripcion, a.idtipo_liquido, b.nombre   from  sabor_x_tipo_liquido a, tipo_liquido b where a.idtipo_liquido = b.idtipo_liquido";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idsaborxtipoliquido = rs.getInt("idsabor_x_tipo_liquido");
				String descripcion = rs.getString("descripcion");
				int idtipoliquido = rs.getInt("idtipo_liquido");
				String nombreliquido = rs.getString("nombre");
				SaborLiquido saborliquido = new SaborLiquido(idsaborxtipoliquido,descripcion,idtipoliquido,nombreliquido);
				saborliquidos.add(saborliquido);
			}
		}catch (Exception e){
			logger.info(e.toString());
		}
		return(saborliquidos);
		
	}
	
	public static int insertarSaborTipoLiquido(SaborLiquido pro)
	{
		Logger logger = Logger.getLogger("log_file");
		int idSaborLiquidoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into sabor_x_tipo_liquido (idsabor_x_tipo_liquido,descripcion,idtipo_liquido) values (" + pro.getIdSaborTipoLiquido() + ", '" + pro.getDescripcionSabor() + "' , "  + pro.getIdLiquido()  +  ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idSaborLiquidoIns = rs.getInt(1);
				
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		return(idSaborLiquidoIns);
	}

	public static void eliminarSaborTipoLiquido(int idsabortipoliquido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from sabor_x_tipo_liquido  where idsabor_x_tipo_liquido = " + idsabortipoliquido; 
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		
	}

	public static SaborLiquido retornarSaborTipoLiquido(int idsabortipoliquido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		SaborLiquido Pro = new SaborLiquido(0,"",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idsabor_x_tipo_liquido,descripcion,idtipo_liquido from  sabor_x_tipo_liquido  where idsabor_x_tipo_liquido = " + idsabortipoliquido; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idsabor = 0;
			String descripcion = "";
			int idtipo_liquido = 0;
			while(rs.next()){
				idsabor = rs.getInt("idsabor_x_tipo_liquido");
				descripcion = rs.getString("descripcion");
				idtipo_liquido = rs.getInt("idtipo_liquido");
				break;
			}
			Pro = new SaborLiquido(idsabor,descripcion,idtipo_liquido);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		return(Pro);
	}

	public static String editarSaborTipoLiquido(SaborLiquido Pro)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update sabor_x_tipo_liquido set  descripcion = '" + Pro.getDescripcionSabor() + "', idtipo_liquido =" + Pro.getIdLiquido() +"  where idsabor_x_tipo_liquido = " + Pro.getIdSaborTipoLiquido(); 
			logger.info(update);
			stm.executeUpdate(update);
			resultado = "exitoso";
			stm.close();
			con1.close();
			System.out.println(update);
		}
		catch (Exception e){
			logger.error(e.toString());
			resultado = "error";
		}
		return(resultado);
	}

}
