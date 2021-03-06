package capaDAO;

import java.sql.Connection;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.ExcepcionPrecio;
import capaModelo.Marcacion;
import capaModelo.Municipio;
import conexion.ConexionBaseDatos;

/**
 * Clase que se encarga de la implementación de toda la interacción con la base de datos para la entidad Municipio.
 * @author JuanDavid
 *
 */
public class MarcacionDAO {
	
	/**
	 * Método que se encarga de retornar la información de todos los municipios definidos en el sistema.
	 * @return Se retorna un ArrayList con todos los municipios definidos en el sistema
	 */
	public static ArrayList<Marcacion> obtenerMarcaciones()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Marcacion> marcaciones = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from marcacion where estado = 1";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idMarcacion = rs.getInt("idmarcacion");
				String nombreMarcacion = rs.getString("nombre_marcacion");
				int estado = rs.getInt("estado");
				Marcacion marcacion = new Marcacion(idMarcacion, nombreMarcacion, estado);
				marcaciones.add(marcacion);
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
		return(marcaciones);
		
	}
	
	
}
