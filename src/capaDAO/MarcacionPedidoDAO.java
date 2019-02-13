package capaDAO;

import java.sql.Connection;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.ExcepcionPrecio;
import capaModelo.Marcacion;
import capaModelo.MarcacionPedido;
import capaModelo.Municipio;
import capaModelo.Tienda;
import conexion.ConexionBaseDatos;

/**
 * Clase que se encarga de la implementación de toda la interacción con la base de datos para la entidad Municipio.
 * @author JuanDavid
 *
 */
public class MarcacionPedidoDAO {
	
	public static void InsertarMarcacionPedido(MarcacionPedido mar)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into marcacion_pedido (idpedido,idmarcacion,observacion) values ( " + mar.getIdPedido() + " , " + mar.getIdMarcacion() + " , '" + mar.getObservacion() +  "' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
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
			return;
		}
		return;
	}

	
	
}
