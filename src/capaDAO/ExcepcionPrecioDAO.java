package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.EstadoPedido;
import capaModelo.ExcepcionPrecio;
import capaModelo.Tienda;
import conexion.ConexionBaseDatos;
import org.apache.log4j.Logger;
/**
 * Clase que implementa todos los métodos de acceso a la base de datos para la administración de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class ExcepcionPrecioDAO {
	
	/**
	 * Método que se encarga de obtener todas la excepciones de precio parametrizadas en base de datos
	 * @return Retorna un ArrayList con objetos de Modelo Excepción Precio.
	 */
	public static ArrayList<ExcepcionPrecio> obtenerExcepcionesPrecio()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<ExcepcionPrecio> excepciones = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from excepcion_precio order by descripcion asc";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idExcepcion = rs.getInt("idexcepcion");
				int idProducto = rs.getInt("idproducto");
				double precio = rs.getDouble("precio");
				String descripcion = rs.getString("descripcion");
				String incluye_liquido  = rs.getString("incluye_liquido");
				int idtipoliquido = rs.getInt("idtipoliquido");
				String controlaCantidadIngredientes = rs.getString("controla_cantidad_ingredientes");
				int cantidadIngredientes = rs.getInt("cantidad_ingredientes");
				String partiradiciones = rs.getString("partiradiciones");
				ExcepcionPrecio excepcion = new ExcepcionPrecio(idExcepcion, idProducto, precio, descripcion,controlaCantidadIngredientes,cantidadIngredientes,"",0,partiradiciones);
				excepciones.add(excepcion);
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
		return(excepciones);
		
	}
	
	/**
	 * Método que se encarga de todos las excepciones Precio de la base de datos,en el formato que lo requiere el grid
	 * de la capa de Presentación
	 * @return Retorna un ArrayList con objetos de Modelo ExcepcionPrecio.
	 */
	public static ArrayList<ExcepcionPrecio> obtenerExcepcionesPrecioGrid()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<ExcepcionPrecio> excepciones = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select e.idexcepcion, e.idproducto, p.descripcion producto, e.precio, e.descripcion, e.incluye_liquido, e.idtipoliquido, t.nombre nombreliquido   from  producto p, excepcion_precio e LEFT JOIN tipo_liquido t ON (e.idtipoliquido = t.idtipo_liquido) where  e.idproducto = p.idproducto";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idExcepcion = rs.getInt("idexcepcion");
				int idProducto = rs.getInt("idproducto");
				String producto = rs.getString("producto");
				double precio = rs.getDouble("precio");
				String descripcion = rs.getString("descripcion");
				String incluye_liquido  = rs.getString("incluye_liquido");
				int idtipoliquido = rs.getInt("idtipoliquido");
				String nombreliquido = rs.getString("nombreliquido");
				ExcepcionPrecio excepcion = new ExcepcionPrecio(idExcepcion, idProducto, producto, precio, descripcion, incluye_liquido, idtipoliquido, nombreliquido);
				excepciones.add(excepcion);
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
		return(excepciones);
		
	}
	
	/**
	 * Método que se encarga de realizar la inserción de una ExcepcionPrecio con base en la información recibida como 
	 * parámetro.
	 * @param Exc Recibe como parámetro un objeto de Modelo ExcepcionPrecio con base en el cual se realiza la inserción
	 * de la información.
	 * @return Se retorna un número entero con el idexcepcion retornado en la inserción a la base de datos.
	 */
	public static int insertarExcepcionPrecio(ExcepcionPrecio Exc)
	{
		Logger logger = Logger.getLogger("log_file");
		int idExcepcionPrecioIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into excepcion_precio (idproducto,precio,descripcion,incluye_liquido,idtipoliquido) values (" + Exc.getIdProducto() + "," + Exc.getPrecio() + ", '" + Exc.getDescripcion() + "' , '" + Exc.getIncluyeliquido() + "' , " + Exc.getIdtipoliquido()  +  ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idExcepcionPrecioIns = rs.getInt(1);
				logger.info("idexcepcionprecio insertado es " + idExcepcionPrecioIns);
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
		return(idExcepcionPrecioIns);
	}

	/**
	 * Método qeu se encarga de eliminar una excepción de precio con base en la información enviadad como parámetro.
	 * @param idexcepcion Recibe como parámetro el idexcepcion que desea ser eliminado.
	 */
	public static void eliminarExcepcionPrecio(int idexcepcion)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from excepcion_precio  where idexcepcion = " + idexcepcion; 
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
	 * Método que se encarga de consultar una excepcion Precio con base en el parámetro recibido.
	 * @param idexcepcion Se recibe como parámetro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo ExcepcionPrecio que contiene la información el excepcion Precio consultada.
	 */
	public static ExcepcionPrecio retornarExcepcionPrecio(int idexcepcion)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		ExcepcionPrecio Esc = new ExcepcionPrecio(0,0,0,"","",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idexcepcion,idproducto,precio,descripcion,incluye_liquido,idtipoliquido from  excepcion_precio  where idexcepcion = " + idexcepcion; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idexc = 0;
			int idpro = 0;
			double precio = 0;
			String desc = "";
			String inc_liq = "";
			int idtipliq = 0;
			while(rs.next()){
				idexc = rs.getInt("idexcepcion");
				idpro = rs.getInt("idproducto");
				precio = rs.getDouble("precio");
				desc = rs.getString("descripcion");
				inc_liq = rs.getString("incluye_liquido");
				idtipliq = rs.getInt("idtipoliquido");
				break;
			}
			Esc = new ExcepcionPrecio(idexc, idpro,precio, desc,inc_liq,idtipliq);
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
		return(Esc);
	}

	/**
	 * Método que permite editar una excepción Precio con base en la información enviada como parámetro.
	 * @param Esc Recibe como parámetro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edición.
	 * @return Retorna un string con el resultado del proceso de edición.
	 */
	public static String editarExcepcionPrecio(ExcepcionPrecio Esc)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update excepcion_precio set idproducto =" + Esc.getIdProducto() + ", precio = " + Esc.getPrecio() + ", descripcion = '" + Esc.getDescripcion() + "' , incluye_liquido = '" + Esc.getIncluyeliquido() + "' , idtipoliquido =" + Esc.getIdtipoliquido() +"  where idexcepcion = " + Esc.getIdExcepcion(); 
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
