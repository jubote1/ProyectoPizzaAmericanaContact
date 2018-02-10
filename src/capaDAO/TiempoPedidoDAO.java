package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import conexion.ConexionBaseDatos;

import org.apache.log4j.Logger;

import capaModelo.TiempoPedido;


public class TiempoPedidoDAO {
	
	public static ArrayList<TiempoPedido> retornarTiemposPedidos()
	{
		ArrayList <TiempoPedido> tiemposTienda = new ArrayList();
		int tiempo = 0;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idtienda, a.tiempoentrega, b.nombre from tiempo_pedido_tienda a, tienda b where a.idtienda = b.idtienda";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtienda;
			int minutosPedido;
			String tienda;
			TiempoPedido tie;
			while(rs.next()){
				
				try{
					idtienda = Integer.parseInt(rs.getString("idtienda"));
					
				}catch(Exception e){
					logger.error(e.toString());
					idtienda = 0;
				}
				try{
					minutosPedido = Integer.parseInt(rs.getString("tiempoentrega"));
					
				}catch(Exception e){
					logger.error(e.toString());
					minutosPedido = 0;
				}
				tienda = rs.getString("nombre");
				tie = new TiempoPedido(idtienda, tienda, minutosPedido);
				tiemposTienda.add(tie);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(tiemposTienda);
	}
	
	public static boolean actualizarTiempoPedido(int nuevotiempo, int idtienda, String user)
	{
		boolean respuesta;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String actualizacion = "update tiempo_pedido_tienda set tiempoentrega = " + nuevotiempo +  " where idtienda =" + idtienda ;
			logger.info(actualizacion);
			stm.executeUpdate(actualizacion);
			String insercionLog = "insert into log_tiempo_tienda (idtienda, usuario, nuevotiempo) values (" + idtienda +" , '" + user + "' , " + nuevotiempo + ")" ;
			logger.info(insercionLog);
			stm.executeUpdate(insercionLog);
			return(true);
			
		}catch (Exception e)
		{
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
				logger.error(e.toString());
			}
			return(false);
		}
		
	}
	
	public static int retornarTiempoPedidoTienda(int idtienda)
	{
		int tiempo = 0;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select  a.tiempoentrega from tiempo_pedido_tienda a where a.idtienda =" + idtienda;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			TiempoPedido tie;
			while(rs.next()){
				
				try{
					tiempo = Integer.parseInt(rs.getString("tiempoentrega"));
					
				}catch(Exception e){
					logger.error(e.toString());
					idtienda = 0;
				}
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(tiempo);
	}

}
