package capaDAO;
import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Cliente;
import capaModelo.Especialidad;
import capaModelo.OrigenPqrs;

import java.sql.ResultSet;
import org.apache.log4j.Logger;
/**
 * Clase que se encarga de implementar todos aquellos m�todos que tienen una interacci�n directa con la base de datos
 * @author JuanDavid
 *
 */
public class OrigenPqrsDAO {
	
	
/**
 * M�todo que se encarga de insertar en base de datos la informaci�n de la entidad Origen PQRS
 * @param Espe recibe como par�metro un objeto Modelo OrigenPqrs con base en el cual se realiza la inserci�n de la
 * especialidad.
 * @return Se retonra valor entero con el id del origen insertada.
 */
public static int insertarOrigenPqrs(OrigenPqrs origen)
{
	Logger logger = Logger.getLogger("log_file");
	int idOrigenIns = 0;
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	try
	{
		Statement stm = con1.createStatement();
		String insert = "insert into origen (nombre_origen) values ('" + origen.getNombreOrigen() + "')"; 
		logger.info(insert);
		stm.executeUpdate(insert);
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()){
			idOrigenIns=rs.getInt(1);
			logger.info("id origen insertada en bd " + idOrigenIns);
        }
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
		return(0);
	}
	return(idOrigenIns);
}

/**
 * M�todo que se encarga de eliminar un origen en la base de datos.
 * @param idespecialidad Se recibe como par�metro el id Origen qeu se desea eliminar.
 */
public static void eliminarOrigenPqrs(int idOrigen)
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	try
	{
		Statement stm = con1.createStatement();
		String delete = "delete from origen_prqs  where idorigen = " + idOrigen; 
		logger.info(delete);
		stm.executeUpdate(delete);
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
	}
	
}

/**
 * M�todo que se encarga de retornar un origen de pqrs dado un idOrigen
 * @param idespecialidad recibe como par�metro un entero id Origen y con base en esto, realiza la consulta
 * en base de datos y retorna la informaci�n.
 * @return Se retorna la informaci�n del Origen en un objeto Modelo Origen.
 */
public static OrigenPqrs retornarOrigen(int idOrigen)
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	OrigenPqrs orPqrs = new OrigenPqrs(0, "");
	try
	{
		Statement stm = con1.createStatement();
		String consulta = "select * from  origen_pqrs where idorigen = " + idOrigen; 
		logger.info(consulta);
		ResultSet rs = stm.executeQuery(consulta);
		int idOri = 0;
		String nombreOrigen = "";
		while(rs.next()){
			idOri = rs.getInt("idorigen");
			nombreOrigen = rs.getString("nombre_origen");
			break;
		}
		orPqrs = new OrigenPqrs(idOri, nombreOrigen);
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
	}
	return(orPqrs);
}

/**
 * M�todo que se encarga de retornar en un ArrayList todos los origenes de pqrs
 * @return
 */
public static ArrayList<OrigenPqrs> retornarOrigenesPqrs()
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	ArrayList<OrigenPqrs> origenes = new ArrayList();
	OrigenPqrs orPqrs = new OrigenPqrs(0, "");
	try
	{
		Statement stm = con1.createStatement();
		String consulta = "select * from  origen_pqrs";
		logger.info(consulta);
		ResultSet rs = stm.executeQuery(consulta);
		int idOri = 0;
		String nombreOrigen = "";
		while(rs.next()){
			idOri = rs.getInt("idorigen");
			nombreOrigen = rs.getString("nombre_origen");
			orPqrs = new OrigenPqrs(idOri, nombreOrigen);
			origenes.add(orPqrs);
		}
		
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
	}
	return(origenes);
}

/**
 * M�todo que tiene como objetivo modificar un origen de Pqrs.
 * @param Espe Recibe como par�metro un objeto Modelo Origen con base en la cual se har� la modificaci�n.
 * @return Se retorna un string indicadno si el proceso fue exitoso o no.
 */
public static String editarOrigenPqrs(OrigenPqrs orPqrs)
{
	Logger logger = Logger.getLogger("log_file");
	ConexionBaseDatos con = new ConexionBaseDatos();
	Connection con1 = con.obtenerConexionBDPrincipal();
	String resultado = "";
	try
	{
		Statement stm = con1.createStatement();
		String update = "update origen_pqrs set nombre_orige ='" + orPqrs.getNombreOrigen() + "'" + " where idorigen= " + orPqrs.getIdOrigen(); 
		logger.info(update);
		stm.executeUpdate(update);
		resultado = "exitoso";
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
		resultado = "error";
	}
	return(resultado);
}



}
