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
 * Clase que implementa todos los métodos de acceso a la base de datos para la administración de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaClienteDAO {
	
	/**
	 * Método que se encarga de obtener todas la Ofertas de un cliente parametrizadas en base de datos
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
			int PQRS = 0;
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"", 0,"","", "");
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				PQRS = rs.getInt("PQRS");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, PQRS,ingresoOferta, usoOferta, observacion);
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
	 * Método que contiene las ofertas que TIENE VIGENTE UN CLIENTE DETERMINADO
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
			int PQRS = 0;
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"", PQRS,"","", observacion);
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				PQRS = rs.getInt("PQRS");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, PQRS, ingresoOferta, usoOferta, observacion);
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
	 * Método que se encarga de realizar la inserción de una ferta Cliente con base en la información recibida como 
	 * parámetro.
	 * @param Exc Recibe como parámetro un objeto de Modelo EOfertaCliente con base en el cual se realiza la inserción
	 * de la información.
	 * @return Se retorna un número entero con el idofertacliente retornado en la inserción a la base de datos.
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
			String insert = "insert into oferta_cliente (idoferta,idcliente, observacion, PQRS) values (" + ofer.getIdOferta() + " ," + ofer.getIdCliente() + " , '" + ofer.getObservacion() + "' ," + ofer.getPQRS() +")"; 
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
	 * Método qeu se encarga de eliminar una oferta CLiente con base en la información enviadad como parámetro.
	 * @param idofertaCliente Recibe como parámetro el idexcepcion que desea ser eliminado.
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
	 * Método que se encarga de consultar una ofertaCliente con base en el parámetro recibido.
	 * @param idOferta Se recibe como parámetro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo Oferta que contiene la información el excepcion Precio consultada.
	 */
	public static OfertaCliente retornarOfertaCliente(int idOfertaCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"",0,"","","");
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
			int PQRS = 0;
			while(rs.next()){
				idCliente = rs.getInt("idcliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				observacion = rs.getString("observacion");
				PQRS = rs.getInt("PQRS");
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, PQRS,ingresoOferta, usoOferta, observacion);
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
	 * Método que permite editar una excepción Precio con base en la información enviada como parámetro.
	 * @param Esc Recibe como parámetro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edición.
	 * @return Retorna un string con el resultado del proceso de edición.
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
	
	
	/**
	 * Método que se encargará de retornar las ofertas ingresadas en la semana de rango enviada como parámetros y recupera
	 * la información como un arrayList de ofertascliente.
	 * @param fechaSuperior
	 * @param fechaInferior
	 * @return
	 */
	public static ArrayList<OfertaCliente> obtenerOfertasNuevasSemana(String fechaSuperior, String fechaInferior)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<OfertaCliente> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.nombre_oferta from oferta_cliente a, oferta b where a.idoferta = b.idoferta and a.ingreso_oferta >=  '" + fechaInferior + "'  and a.ingreso_oferta <= '" + fechaSuperior + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOfertaCliente;
			int idOferta;
			int idCliente = 0;
			String utilizada;
			String ingresoOferta;
			String usoOferta;
			String nombreOferta = "";
			String observacion = "";
			int PQRS = 0;
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"", 0,"","", "");
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				idCliente = rs.getInt("idcliente");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				PQRS = rs.getInt(PQRS);
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, PQRS,ingresoOferta, usoOferta, observacion);
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
	 * Método que retornará un ArrayList con objetos de tipo oferta Cliente, con todas las ofertas redimidas dentro del  rango de fechas 
	 * enviadas como parámetro.
	 * @param fechaSuperior
	 * @param fechaInferior
	 * @return
	 */
	public static ArrayList<OfertaCliente> obtenerOfertasRedimidasSemana(String fechaSuperior, String fechaInferior)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<OfertaCliente> ofertas = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.nombre_oferta from oferta_cliente a, oferta b where a.idoferta = b.idoferta and a.uso_oferta >=  '" + fechaInferior + "'  and a.uso_oferta <= '" + fechaSuperior + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idOfertaCliente;
			int idOferta;
			int idCliente = 0;
			String utilizada;
			String ingresoOferta;
			String usoOferta;
			String nombreOferta = "";
			String observacion = "";
			int PQRS = 0;
			OfertaCliente ofertaTemp = new OfertaCliente(0,0,0,"", 0,"","", "");
			while(rs.next()){
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				utilizada = rs.getString("utilizada");
				idCliente = rs.getInt("idcliente");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				nombreOferta = rs.getString("nombre_oferta");
				observacion = rs.getString("observacion");
				PQRS = rs.getInt(PQRS);
				ofertaTemp = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, PQRS,ingresoOferta, usoOferta, observacion);
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

}
