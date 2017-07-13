package capaDAO;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Especialidad;
import capaModelo.EstadoPedido;
import java.sql.ResultSet;

public class EstadoPedidoDAO {
	
	public static int insertarEstadoPedido(EstadoPedido Est)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEstadoPedidoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into estado_pedido (descripcion) values ('" + Est.getDescripcion() +  "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idEstadoPedidoIns = rs.getInt(1);
				logger.info("id estado pedido insertado " + idEstadoPedidoIns);
				
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		return(idEstadoPedidoIns);
	}

	public static void eliminarEstadoPedido(int idestadopedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from estado_pedido  where idestadopedido = " + idestadopedido; 
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		
	}

	public static EstadoPedido retornarEstadoPedido(int idestadopedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEstadoPedidoEli = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		EstadoPedido Est = new EstadoPedido(0,"");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idestadopedido,descripcion from  estado_pedido  where idestadopedido = " + idestadopedido;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idesta = 0;
			String descripcion = "";
			while(rs.next()){
				idesta = rs.getInt("idestadopedido");
				descripcion = rs.getString("descripcion");
				break;
			}
			Est = new EstadoPedido(idesta,descripcion);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		return(Est);
	}

	public static String editarEstadoPedido(EstadoPedido Est)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update estado_pedido set descripcion ='" + Est.getDescripcion() +  "'  where idestadopedido = " + Est.getIdestadopedido(); 
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
	
	public static ArrayList<EstadoPedido> obtenerEstadosPedido()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<EstadoPedido> estadosPedidos = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select e.idestadopedido, e.descripcion from estado_pedido e";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idestadopedido;
			String descripcion;
			while(rs.next()){
				idestadopedido = rs.getInt("idestadopedido");
				descripcion = rs.getString("descripcion");
				EstadoPedido est = new EstadoPedido( idestadopedido, descripcion);
				estadosPedidos.add(est);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(estadosPedidos);
		
	}


}
