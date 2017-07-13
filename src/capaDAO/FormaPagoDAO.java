package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import capaModelo.EstadoPedido;
import capaModelo.FormaPago;
import conexion.ConexionBaseDatos;

public class FormaPagoDAO {
	
	public static int insertarFormaPago(FormaPago forma)
	{
		Logger logger = Logger.getLogger("log_file");
		int idFormaPagoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into forma_pago (nombre, tipoformapago) values ('" + forma.getNombre() + "', '" + forma.getTipoforma() + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idFormaPagoIns =rs.getInt(1);
				logger.info("Id forma pago insertada en bd " + idFormaPagoIns);
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		return(idFormaPagoIns);
	}

	public static void eliminarFormaPago(int idFormaPago)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from forma_pago  where idforma_pago = " + idFormaPago; 
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		
	}

	public static FormaPago retornarFormaPago(int idFormaPago)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		FormaPago forma = new FormaPago(0,"", "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idforma_pago,nombre, tipoformapago from  forma_pago  where idforma_pago = " + idFormaPago; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idforma = 0;
			String nombr = "";
			String tipoforma = "";
			while(rs.next()){
				idforma = rs.getInt("idforma_pago");
				nombr = rs.getString("nombre");
				tipoforma = rs.getString("tipoformapago");
				break;
			}
			forma = new FormaPago(idforma, nombr, tipoforma);
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			
		}
		return(forma);
	}

	public static String editarFormaPago(FormaPago forma)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update forma_pago set nombre ='" + forma.getNombre() + "', tipoformapago =  '" + forma.getTipoforma() + "' where idforma_pago = " + forma.getIdformapago(); 
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
	
	public static ArrayList<FormaPago> obtenerFormasPago()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<FormaPago> formaspago = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idforma_pago, a.nombre, a.tipoformapago from forma_pago a";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idformapago;
			String nombre;
			String tipoformapago;
			while(rs.next()){
				idformapago = rs.getInt("idforma_pago");
				nombre = rs.getString("nombre");
				tipoformapago = rs.getString("tipoformapago");
				FormaPago forma = new FormaPago( idformapago,nombre, tipoformapago);
				formaspago.add(forma);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(formaspago);
		
	}

}
