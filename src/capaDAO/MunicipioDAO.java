package capaDAO;

import java.sql.Connection;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.ExcepcionPrecio;
import capaModelo.Municipio;
import conexion.ConexionBaseDatos;

public class MunicipioDAO {
	
	public static ArrayList<Municipio> obtenerMunicipios()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Municipio> municipios = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from municipio";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idMunicipio = rs.getInt("idmunicipio");
				String nombre = rs.getString("nombre");
				Municipio municipio = new Municipio(idMunicipio,nombre);
				municipios.add(municipio);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(municipios);
		
	}
	
	public static int obteneridMunicipio(String municipio)
	{
		Logger logger = Logger.getLogger("log_file");
		int idmunicipio=0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idmunicipio from municipio where nombre = '"+ municipio + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idmunicipio = rs.getInt("idmunicipio");
				break;
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(idmunicipio);
	}

}
