package capaDAO;
import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Cliente;
import capaModelo.Especialidad;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
public class EspecialidadDAO {
	
	

public static int insertarEspecialidad(Especialidad Espe)
{
	Logger logger = Logger.getLogger("log_file");
	int idEspecialidadIns = 0;
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	try
	{
		Statement stm = con1.createStatement();
		String insert = "insert into especialidad (nombre, abreviatura) values ('" + Espe.getNombre() + "', '" +Espe.getAbreviatura() + "')"; 
		logger.info(insert);
		stm.executeUpdate(insert);
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()){
			idEspecialidadIns=rs.getInt(1);
			logger.info("id especialidad insertada en bd " + idEspecialidadIns);
        }
		stm.close();
		con1.close();
	}
	catch (Exception e){
		logger.error(e.toString());
		return(0);
	}
	return(idEspecialidadIns);
}

public static void eliminarEspecialidad(int idespecialidad)
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	try
	{
		Statement stm = con1.createStatement();
		String delete = "delete from especialidad  where idespecialidad = " + idespecialidad; 
		logger.info(delete);
		stm.executeUpdate(delete);
		stm.close();
		con1.close();
	}
	catch (Exception e){
		logger.error(e.toString());
		
	}
	
}

public static Especialidad retornarEspecialidad(int idespecialidad)
{
	Logger logger = Logger.getLogger("log_file");
	int idEspecialidadEli = 0;
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	Especialidad Espe = new Especialidad(0,"", "");
	try
	{
		Statement stm = con1.createStatement();
		String consulta = "select idespecialidad,nombre, abreviatura from  especialidad  where idespecialidad = " + idespecialidad; 
		logger.info(consulta);
		ResultSet rs = stm.executeQuery(consulta);
		int idesp = 0;
		String nombr = "";
		String abre = "";
		while(rs.next()){
			idesp = rs.getInt("idespecialidad");
			nombr = rs.getString("nombre");
			abre = rs.getString("abreviatura");
			break;
		}
		Espe = new Especialidad(idesp, nombr, abre);
		stm.close();
		con1.close();
	}
	catch (Exception e){
		logger.error(e.toString());
		
	}
	return(Espe);
}

public static String editarEspecialidad(Especialidad Espe)
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	String resultado = "";
	try
	{
		Statement stm = con1.createStatement();
		String update = "update especialidad set nombre ='" + Espe.getNombre() + "', abreviatura =  '" +Espe.getAbreviatura() + "' where idespecialidad = " + Espe.getIdespecialidad(); 
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
