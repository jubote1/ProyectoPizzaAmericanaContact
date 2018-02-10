package capaDAO;

import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Especialidad;
import capaModelo.ExcepcionPrecio;
import capaModelo.Producto;
import capaModelo.SaborLiquido;
import capaModelo.DetallePedido;
import capaModelo.TipoLiquido;

import java.sql.ResultSet;
import org.apache.log4j.Logger;

/**
 * Clase que se encarga de implementar toda la interacci�n con la base de datos para le entidad Producto.
 * @author JuanDavid
 *
 */
public class ProductoDAO {
	
	/**
	 * M�todo que se encarga de retonar la informaci�n de todas la entidades Producto definidas en el sistema.
	 * @return Se retorna un ArrayList con todos los productos definidos en el sistema.
	 *
	 */
	public static ArrayList<Producto> obtenerProductos()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> productos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from producto";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idproducto = rs.getInt("idproducto");
				int idreceta = rs.getInt("idreceta");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				float impuesto = rs.getFloat("impuesto");
				String tipo = rs.getString("tipo");
				int productoasociaadicion = Integer.parseInt(rs.getString("producto_asocia_adicion"));
				double preciogeneral = rs.getDouble("preciogeneral");
				String incluye_liquido = rs.getString("incluye_liquido");
				int idtipoliquido = rs.getInt("idtipo_liquido");
				String manejacantidad = rs.getString("manejacantidad");
				Producto producto = new Producto(idproducto, idreceta, nombre, descripcion, impuesto,tipo,productoasociaadicion, preciogeneral, incluye_liquido, idtipoliquido, manejacantidad);
				productos.add(producto);
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
		return(productos);
		
	}
	
	/**
	 * M�todo que se encarga de retornar la informaci�n de todos los productos definidos en el sistema, en forma GRID
	 * para la implementaci�n del CRUD de la entidad Producto.
	 * @return Se retorna un ArrayList con todos los productos definidos en el sistema con la informaci�n base para
	 * la implementaci�n del GRID.
	 */
	public static ArrayList<Producto> obtenerProductosGrid()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> productos = new ArrayList<>();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, r.nombre nombrereceta, p.nombre,  p.descripcion, p.impuesto, p.tipo, p.preciogeneral, p.incluye_liquido, p.idtipo_liquido, t.nombre nombreliquido   from  producto p LEFT JOIN tipo_liquido t ON (p.idtipo_liquido = t.idtipo_liquido) LEFT JOIN RECETA r ON (p.idreceta = r.idreceta)";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idproducto = rs.getInt("idproducto");
				int idreceta = rs.getInt("idreceta");
				String nombrereceta = rs.getString("nombrereceta");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				float impuesto = rs.getFloat("impuesto");
				String tipo = rs.getString("tipo");
				double preciogeneral = rs.getDouble("preciogeneral");
				String incluye_liquido  = rs.getString("incluye_liquido");
				int idtipoliquido = rs.getInt("idtipo_liquido");
				String nombreliquido = rs.getString("nombreliquido");
				Producto producto = new Producto(idproducto,idreceta, nombrereceta, nombre, descripcion, impuesto, tipo, preciogeneral, incluye_liquido, idtipoliquido, nombreliquido);
				productos.add(producto);
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
		return(productos);
		
	}
	
	public static ArrayList<Producto> GetProductosTienda(int idtienda) {
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> todosProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, p.nombre, p.descripcion, p.impuesto, p.tipo, p.producto_asocia_adicion, p.preciogeneral, p.incluye_liquido, p.idtipo_liquido, p.manejacantidad from producto p where p.idproducto not in (select b.idproducto from producto_no_existente b where idtienda ="+ idtienda +" ) order by nombre asc";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			int idReceta;
			String nombre;
			String descripcion;
			float impuesto;
			String tipo;
			int productoasociaadicion;
			double precio;
			String incluye_liquido;
			int idtipo_liquido;
			String manejacantidad;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				idReceta = rs.getInt("idreceta");
				nombre = rs.getString("nombre");
				descripcion = rs.getString("descripcion");
				impuesto = rs.getFloat("impuesto");
				tipo = rs.getString("tipo");
				productoasociaadicion = rs.getInt("producto_asocia_adicion");
				precio = rs.getDouble("preciogeneral");
				incluye_liquido = rs.getString("incluye_liquido");
				idtipo_liquido = rs.getInt("idtipo_liquido");
				manejacantidad = rs.getString("manejacantidad");
				Producto prod = new Producto(idProducto, idReceta, nombre, descripcion,impuesto, tipo,productoasociaadicion, precio, incluye_liquido, idtipo_liquido, manejacantidad);
				todosProducto.add(prod);
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
		return(todosProducto);
		
	}
	
	/**
	 * M�todo que se encarga de insertar un nuevo producto con base en la informaci�n recibida como par�metro
	 * @param pro Se recibe como par�metro un objeto Modelo Producto con toda la informaci� del nuevo producto
	 * a insertar
	 * @return Se retorna un valor entero con el idproducto del producto creado.
	 */
	public static int insertarProducto(Producto pro)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into producto (idreceta,nombre,descripcion, impuesto,tipo,preciogeneral,incluye_liquido,idtipo_liquido) values (" + pro.getIdReceta() + ", '" + pro.getNombre() + "' , '" + pro.getDescripcion() + "' , " + pro.getImpuesto() + " , '" + pro.getTipo() + "', " + pro.getPreciogeneral() + " , '" + pro.getIncluye_liquido() + "' ," + pro.getIdtipo_liquido()  +  ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idProductoIns = rs.getInt(1);
				
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
		return(idProductoIns);
	}

	/**
	 * M�todo que se encarga de la eliminaci�n de un Producto con base el par�metro recibido.
	 * @param idproducto Se recibe como par�metro el idproducto con base en el cual se realiza la eliminaci�n del producto
	 */
	public static void eliminarProducto(int idproducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from producto  where idproducto = " + idproducto; 
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
	 * M�todo con base en el cual se retorna la informaci�n de un producto con base en el par�metro recibido
	 * @param idproducto Se recibe como par�metro el idproducto del producto que desea ser consultado.
	 * @return Se retorna un objeto Modelo Producto con la informaci�n del producto que se desea consultar.
	 */
	public static Producto retornarProducto(int idproducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Producto Pro = new Producto(0, 0, "", "", 0, "", 0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idproducto,idreceta,nombre, descripcion,impuesto,tipo,producto_asocia_adicion,preciogeneral,incluye_liquido,idtipo_liquido, manejacantidad from  producto  where idproducto = " + idproducto; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idproduc = 0;
			int idreceta = 0;
			String nombre ="";
			String descripcion = "";
			float impuesto = 0;
			String tipo = "";
			int productoasociaadicion= 0;
			double preciogeneral = 0;
			String incluye_liquido = "";
			int idtipo_liquido = 0;
			String manejacantidad = "";
			while(rs.next()){
				idproduc = rs.getInt("idproducto");
				idreceta  = rs.getInt("idreceta");
				nombre = rs.getString("nombre");
				descripcion = rs.getString("descripcion");
				impuesto = rs.getFloat("impuesto");
				tipo = rs.getString("tipo");
				productoasociaadicion = rs.getInt("producto_asocia_adicion");
				preciogeneral = rs.getDouble("preciogeneral");
				incluye_liquido = rs.getString("incluye_liquido");
				idtipo_liquido = rs.getInt("idtipo_liquido");
				manejacantidad = rs.getString("manejacantidad");
				break;
			}
			Pro = new Producto(idproduc,idreceta,nombre,descripcion,impuesto,tipo,productoasociaadicion,preciogeneral,incluye_liquido,idtipo_liquido, manejacantidad);
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
		return(Pro);
	}

	/**
	 * M�todo que se encarga de editar un producto con base en los par�metros recibidos
	 * @param Pro Se recibe como par�metro un objeto Modelo Producto el cual contiene la informaci�n base para
	 * la edici�n del producto.
	 * @return Se retorna un valor tipo String con el resultado del proceso.
	 */
	public static String editarProducto(Producto Pro)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update producto set idreceta =" + Pro.getIdReceta() + ", nombre = '" + Pro.getNombre() + "', descripcion = '" + Pro.getDescripcion() + "', impuesto=" + Pro.getImpuesto()  + ", tipo = '" + Pro.getTipo() + "', preciogeneral = " + Pro.getPreciogeneral() + " , incluye_liquido = '" + Pro.getIncluye_liquido() + "' , idtipo_liquido =" + Pro.getIdtipo_liquido() +"  where idproducto = " + Pro.getIdProducto(); 
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
