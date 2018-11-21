package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import capaModelo.EstadoPedido;
import capaModelo.ExcepcionPrecio;
import capaModelo.Oferta;
import capaModelo.OfertaCliente;
import capaModelo.Tienda;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;
/**
 * Clase que implementa todos los m�todos de acceso a la base de datos para la administraci�n de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaClienteDAO {
	
	/**
	 * M�todo que se encarga de obtener todas la Ofertas de un cliente parametrizadas en base de datos
	 * @return Retorna un ArrayList con objetos de Modelo Oferta.
	 */
	public static ArrayList<OfertaCliente> obtenerOfertasClienteGrid(int idCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<OfertaCliente> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.nombre_oferta from oferta_cliente a, oferta b where a.idoferta = b.idoferta and a.idcliente = " + idCliente;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOfertaCliente;
			int idOferta;
			String utilizada;
			String ingresoOferta;
			String usoOferta;
			String nombreOferta = "";
			String observacion = "";
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"","","", "");
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, ingresoOferta, usoOferta, observacion);
				ofertaTemp.setNombreOferta(nombreOferta);
				ofertas.add(ofertaTemp);
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
		return(ofertas);
		
	}
	
	/**
	 * M�todo que contiene las ofertas que TIENE VIGENTE UN CLIENTE DETERMINADO
	 * @param idCliente
	 * @return
	 */
	public static ArrayList<OfertaCliente> obtenerOfertasVigenteCliente(int idCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<OfertaCliente> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.nombre_oferta from oferta_cliente a, oferta b where a.idoferta = b.idoferta and utilizada = 'N' and idcliente = " + idCliente;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOfertaCliente;
			int idOferta;
			String utilizada;
			String ingresoOferta;
			String usoOferta;
			String nombreOferta = "";
			String observacion = "";
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"","","", observacion);
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, ingresoOferta, usoOferta, observacion);
				ofertaTemp.setNombreOferta(nombreOferta);
				ofertas.add(ofertaTemp);
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
		return(ofertas);
		
	}
	
		
	/**
	 * M�todo que se encarga de realizar la inserci�n de una ferta Cliente con base en la informaci�n recibida como 
	 * par�metro.
	 * @param Exc Recibe como par�metro un objeto de Modelo EOfertaCliente con base en el cual se realiza la inserci�n
	 * de la informaci�n.
	 * @return Se retorna un n�mero entero con el idofertacliente retornado en la inserci�n a la base de datos.
	 */
	public static int insertarOfertaCliente(OfertaCliente ofer)
	{
		Logger logger = Logger.getLogger("log_file");
		int idOfertaClienteIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into oferta_cliente (idoferta,idcliente, observacion) values (" + ofer.getIdOferta() + " ," + ofer.getIdCliente() + " , '" + ofer.getObservacion() +"')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idOfertaClienteIns = rs.getInt(1);
				logger.info("idOfertaCliente insertado es " + idOfertaClienteIns);
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
		return(idOfertaClienteIns);
	}

	/**
	 * M�todo qeu se encarga de eliminar una oferta CLiente con base en la informaci�n enviadad como par�metro.
	 * @param idofertaCliente Recibe como par�metro el idexcepcion que desea ser eliminado.
	 */
	public static void eliminarOfertaCliente(int idOfertaCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from oferta_cliente  where idofertacliente = " + idOfertaCliente; 
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
	 * M�todo que se encarga de consultar una ofertaCliente con base en el par�metro recibido.
	 * @param idOferta Se recibe como par�metro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo Oferta que contiene la informaci�n el excepcion Precio consultada.
	 */
	public static OfertaCliente retornarOfertaCliente(int idOfertaCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"","","","");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from  oferta_cliente  where idofertacliente = " + idOfertaCliente; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idCliente;
			int idOferta;
			String utilizada;
			String ingresoOferta;
			String usoOferta;
			String observacion;
			while(rs.next()){
				idCliente = rs.getInt("idcliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				observacion = rs.getString("observacion");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, ingresoOferta, usoOferta, observacion);
				break;
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
		return(ofertaTemp);
	}

	/**
	 * M�todo que permite editar una excepci�n Precio con base en la informaci�n enviada como par�metro.
	 * @param Esc Recibe como par�metro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edici�n.
	 * @return Retorna un string con el resultado del proceso de edici�n.
	 */
	public static String actualizarUsoOferta(int idOfertaCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm"+":00");
		Date fechaActual = new Date();
		String fechaActualizar = formatoFecha.format(fechaActual);
		try
		{
			Statement stm = con1.createStatement();
			String update = "update oferta_cliente set utilizada = 'S' , uso_oferta = '"+ fechaActualizar +"' where idofertacliente = " + idOfertaCliente; 
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
