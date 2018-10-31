package capaDAO;

import conexion.ConexionBaseDatos;
import pixelpos.Main;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import capaModelo.Especialidad;
import capaModelo.ExcepcionPrecio;
import capaModelo.FormaPago;
import capaModelo.HomologaGaseosaIncluida;
import capaModelo.InsertarPedidoPixel;
import capaModelo.Producto;
import capaModelo.SaborLiquido;
import capaModelo.Tienda;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.TipoLiquido;
import capaModelo.Pedido;
import capaModelo.DetallePedidoPixel;
import capaModelo.DireccionFueraZona;
import capaModelo.DetallePedidoAdicion;
import capaModelo.NomenclaturaDireccion;
import org.apache.log4j.Logger;
//import pixelpos.Main;
import capaModelo.DetallePedidoPixel;
import capaModelo.ModificadorDetallePedido;
import capaModelo.ProductoIncluido;

import java.sql.ResultSet;
import java.util.Date;

public class PedidoDAO {
	
	/**
	 * M�todo que se encarga de obtener todas especialidades de Pizza definidos en el sistema.
	 * @return Se retorna un ArrayList con todas las especialidades definidas en la base de datos.
	 */
	public static ArrayList<Especialidad> obtenerEspecialidad()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Especialidad> especialidades = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select e.idespecialidad, e.nombre, e.abreviatura from especialidad e order by nombre asc";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idespecialidad;
			String nombre;
			String abreviatura;
			while(rs.next()){
				idespecialidad = rs.getInt("idespecialidad");
				nombre = rs.getString("nombre");
				abreviatura = rs.getString("abreviatura");
				Especialidad espec = new Especialidad( idespecialidad, nombre, abreviatura);
				especialidades.add(espec);
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
		return(especialidades);
		
	}
	
	/**
	 * M�todo que se encarga de retornar un ArrayList con objetos de Modelo Producto que correspongan a la tipolog�a
	 * otro productos
	 * @return Se retorna ArrayList con objetos de Modelo Producto que correspongan a la tipolog�a otros productos.
	 */
	public static ArrayList<Producto> obtenerOtrosProductos()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> otrosProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, p.nombre, p.descripcion, p.impuesto, p.tipo, p.preciogeneral from producto p where tipo = 'OTROS' ";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			int idReceta;
			String nombre;
			String descripcion;
			float impuesto;
			String tipo;
			double precio;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				idReceta = rs.getInt("idreceta");
				nombre = rs.getString("nombre");
				descripcion = rs.getString("descripcion");
				impuesto = rs.getFloat("impuesto");
				tipo = rs.getString("tipo");
				precio = rs.getDouble("preciogeneral");
				Producto prod = new Producto(idProducto, idReceta, nombre, descripcion,impuesto, tipo,precio);
				otrosProducto.add(prod);
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
		return(otrosProducto);
		
	}
	
	/**
	 * M�todo que se encarga de retornar los productos tipo adici�n.
	 * @return Se retorna un ArrayLista con entidades MOdelo Producto de la tipolog�a de producto Adici�n.
	 */
	public static ArrayList<Producto> obtenerAdicionProductos()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> adicionProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, p.nombre, p.descripcion, p.impuesto, p.tipo, p.preciogeneral from producto p where tipo = 'ADICION' ";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			int idReceta;
			String nombre;
			String descripcion;
			float impuesto;
			String tipo;
			double precio;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				idReceta = rs.getInt("idreceta");
				nombre = rs.getString("nombre");
				descripcion = rs.getString("descripcion");
				impuesto = rs.getFloat("impuesto");
				tipo = rs.getString("tipo");
				precio = rs.getDouble("preciogeneral");
				Producto prod = new Producto(idProducto, idReceta, nombre, descripcion,impuesto, tipo,precio);
				adicionProducto.add(prod);
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
		return(adicionProducto);
		
	}

	/**
	 * M�todo que se encarga de retornar todos los prodcutos definidos en el sistema
	 * @return Se retorna un arrayList con objetos Modelo Producto, todos los definidos en el sistema.
	 */
	public static ArrayList<Producto> obtenerTodosProductos() {
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> todosProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, p.nombre, p.descripcion, p.impuesto, p.tipo, p.producto_asocia_adicion, p.preciogeneral, p.incluye_liquido, p.idtipo_liquido, p.manejacantidad from producto p order by nombre asc ";
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
	 * M�todo que se encarga de retornar los sabores l�quido definidos para un producto.
	 * @param idProdu Se recibe como par�metro el id producto del cual se desean consultar los sabores tipo liquidos
	 * disponibles por parametrizaci�n.
	 * @return Se retorna un ArrayList con objetos Modelo SaborLiquido con la informaci�n seg�n los par�metros recibidos.
	 */
	public static ArrayList<SaborLiquido> ObtenerSaboresLiquidoProducto(int idProdu, int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saboresLiquido = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idsabor_x_tipo_liquido, a.descripcion, b.idtipo_liquido, b.nombre, c.idproducto, c.descripcion from sabor_x_tipo_liquido a , tipo_liquido b, producto c where a.idtipo_liquido = b.idtipo_liquido and c.idtipo_liquido = b.idtipo_liquido and c.idproducto = " + idProdu + " and a.idproducto not in (select idproducto from producto_no_existente where idtienda = " + idtienda +  ")";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idSaborTipoLiquido;
			String descripcionSabor;
			int idLiquido;
			String descripcionLiquido;
			int idProducto;
			String nombreProducto;
			while(rs.next())
			{
				idSaborTipoLiquido = rs.getInt("idsabor_x_tipo_liquido");
				descripcionSabor= rs.getString("descripcion");
				idLiquido= rs.getInt("idtipo_liquido");
				descripcionLiquido=rs.getString("nombre");
				idProducto = rs.getInt("idproducto");
				nombreProducto = rs.getString("descripcion");
				SaborLiquido saborLiq = new SaborLiquido(idSaborTipoLiquido, descripcionSabor,idLiquido,descripcionLiquido,idProducto,nombreProducto);
				saboresLiquido.add(saborLiq);
			}
			rs.close();
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
		return(saboresLiquido);
	}
	
	/**
	 * M�todo que retorna los sabores tipo liquido que tiene disponible un excepci�n de precio.
	 * @param idExce Se recibe como par�metro un entero con el idexcepcion del cual se quiere consultar
	 * los sabores de liquido disponible
	 * @return Se retorna un ArrayList con objetos MOdelo SaborLiquido de acuerdo a la excepcion pasada como 
	 * par�metro.
	 */
	public static ArrayList<SaborLiquido> ObtenerSaboresLiquidoExcepcion(int idExce, int idtienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saboresLiquido = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idsabor_x_tipo_liquido, a.descripcion, b.idtipo_liquido, b.nombre, c.idexcepcion, c.descripcion from sabor_x_tipo_liquido a , tipo_liquido b, excepcion_precio c where a.idtipo_liquido = b.idtipo_liquido and c.idtipoliquido = b.idtipo_liquido and c.idexcepcion = " + idExce + " and a.idproducto not in (select idproducto from producto_no_existente where idtienda = " + idtienda +  ")";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idSaborTipoLiquido;
			String descripcionSabor;
			int idLiquido;
			String descripcionLiquido;
			int idExcepcion;
			String nombreExcepcion;
			while(rs.next())
			{
				idSaborTipoLiquido = rs.getInt("idsabor_x_tipo_liquido");
				descripcionSabor= rs.getString("descripcion");
				idLiquido= rs.getInt("idtipo_liquido");
				descripcionLiquido=rs.getString("nombre");
				idExcepcion = rs.getInt("idexcepcion");
				nombreExcepcion = rs.getString("descripcion");
				SaborLiquido saborLiq = new SaborLiquido(idSaborTipoLiquido, descripcionSabor,idLiquido,descripcionLiquido,nombreExcepcion, idExcepcion);
				saboresLiquido.add(saborLiq);
			}
			rs.close();
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
		
		return(saboresLiquido);
	}
	
	
	/**
	 * M�todo que se encarga de insertar el encabezado de un pedido.
	 * @param idtienda Se recibe la tienda a la cual pertenecer� el pedido.
	 * @param idcliente Se recibe el idcliente para el cual se tomar� el pedido
	 * @param fechaPedido Se inserta la fecha del pedido.
	 * @return Se retorna el idpedido retornado en por la base de datos en la inserci�n.
	 */
	public static int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPedidoInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Date fechaTemporal = new Date();
		DateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		String fechaPedidoFinal ="";
		try
		{
			fechaTemporal = new SimpleDateFormat("dd/MM/yyyy").parse(fechaPedido);
			fechaPedidoFinal = formatoFinal.format(fechaTemporal);
			
		}catch(Exception e){
			logger.error(e.toString());
			return(0);
		}
		
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente, idestadopedido,fechapedido, usuariopedido) values (" + idtienda + ", " + idcliente + ", 1 , '" + fechaPedidoFinal  + "' , '" + user + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPedidoInsertado=rs.getInt(1);
				System.out.println(idPedidoInsertado);
	        }
	        rs.close();
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
		return(idPedidoInsertado);
	}
	
	
	/**
	 * M�todo que se encarga de ir insertando uno a uno los item pedido solicitados por el cliente.
	 * @param detPedido Se recibe como par�metro en un objeto Modelo DetallePedido la informaci�n de cada detalle pedido
	 * y se va insertando en base de datos
	 * @return Con respuesta se va obteniendo un n�mero entero con el iddetallepedido
	 */
	public static int InsertarDetallePedido(DetallePedido detPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetallePedidoInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into detalle_pedido (idproducto,idpedido,cantidad, idespecialidad1, idespecialidad2, valorUnitario, valorTotal, adicion, observacion, idsabortipoliquido, idexcepcion,modespecialidad1, modespecialidad2) values (" + detPedido.getIdproducto() + " , " + detPedido.getIdpedido() + " , " + detPedido.getCantidad() + " , " + detPedido.getIdespecialidad1() + " , " + detPedido.getIdespecialidad2() + " , " + detPedido.getValorunitario() + " , " + detPedido.getValortotal() + ", '" + detPedido.getAdicion() + "' , '"+detPedido.getObservacion() + "' , " + detPedido.getIdsabortipoliquido() + " , " + detPedido.getIdexcepcion() + " , '"+ detPedido.getModespecialidad1() + "' , '" + detPedido.getModespecialidad2() +"' )"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idDetallePedidoInsertado=rs.getInt(1);
				System.out.println(idDetallePedidoInsertado);
	        }
	        rs.close();
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
		return(idDetallePedidoInsertado);
	}
	
	/**
	 * M�todo que se encarga de insertar el detalle de la adici�n en otra tabla relacional que involucra un iddetallepedido
	 * un idadici�n
	 * @param detPedidoAdicion Se recibe objeto Modelo DetallePedidoAdicion con base en el cual se realiza la inserci�n
	 * del detalle de adici�n para un producto determinado
	 * @return Se retorna el id asociado a la inserci�n realizada
	 */
	public static int InsertarDetalleAdicion(DetallePedidoAdicion detPedidoAdicion)
	{
		Logger logger = Logger.getLogger("log_file");
		int idAdicion = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert adicion_detalle_pedido (iddetallepedidopadre, iddetallepedidoadicion, idespecialidad1, idespecialidad2, cantidad1, cantidad2) "
					+ "values (" + detPedidoAdicion.getIddetallepedidopadre() + " , " + detPedidoAdicion.getIddetallepedidoadicion()+ " , "
							+  detPedidoAdicion.getIdespecialidad1() + " , " + detPedidoAdicion.getIdespecialidad2() + " , " + detPedidoAdicion.getCantidad1() + " , " + detPedidoAdicion.getCantidad2()+ ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idAdicion=rs.getInt(1);
				
	        }
	        rs.close();
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
		return(idAdicion);
	}
	
	/**
	 * M�todo que se encarga de insertar la informaci�n de un modificador CON/SIN de un detalle pedido en particular
	 * @param modDetallePedido Recibe como par�metro el objeto Modelo ModificadorDetallePedido con la informaci�n
	 * a insertar
	 * @return Retorne en un entero el idmodificador retornado luego de la inserci�n en base de datos.
	 */
	public static int InsertarModificadorDetallePedido(ModificadorDetallePedido modDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idModificador = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert modificador_detalle_pedido (iddetallepedidopadre, idproductoespecialidad1,idproductoespecialidad2,cantidad,iddetallepedidoasociado) "
					+ "values (" + modDetallePedido.getIddetallepedidopadre() + " , " + modDetallePedido.getIdproductoespecialidad1() + " , "
							+  modDetallePedido.getIdproductoespecialidad2() + " , " + modDetallePedido.getCantidad() + " , " + modDetallePedido.getIddetallepedidoasociado() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idModificador=rs.getInt(1);
				
	        }
	        rs.close();
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
		return(idModificador);
	}
	
	/**
	 * M�todo que permite retornar un idespecialidad, con base en el nombre especialidad pasado como par�metro
	 * @param especialidad Se recibe como par�metro un String el nombre de la especialidad.
	 * @return Se retorna el idespecialidad asociado a la especialidad enviada como par�metro.
	 */
	public static int obtenerIdEspecialidad(String especialidad)
	{
		Logger logger = Logger.getLogger("log_file");
		int idespecialidad = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idespecialidad from especialidad where abreviatura = '" + especialidad + "' " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idespecialidad = rs.getInt("idespecialidad");
				break;
			}
	        rs.close();
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
		return(idespecialidad);
	}
	
	/**
	 * M�todo que se encarga de obtener el valor total de un pedido, con base en todos los detalles asociados a un pedido
	 * en particular.
	 * @param idpedido Se recibe como par�metro el idpedido del cual se requiere obtener el total.
	 * @return Se retorna un valor doble que tiene el total del valor del pedido pasado como par�metro.
	 */
	public static double obtenerTotalPedido(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		double valorTotal = 0;
		try
		{
			
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedido = " + idpedido + " " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				break;
			}
			
			rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			valorTotal = 0;
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		
		return(valorTotal);
	}
	
	public static int obtenerIdClientePedido(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		int idcliente = 0;
		try
		{
			
			Statement stm = con1.createStatement();
			String consulta = "select idcliente from pedido where idpedido = " + idpedido + " " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idcliente = Integer.parseInt(rs.getString("idcliente"));
				break;
			}
			
			rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			idcliente = 0;
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		
		return(idcliente);
	}
	
	/**
	 * M�todo que se encarga de actualizar un pedido con el n�mero de pedido el cual le fue asignado en la tienda correspondiente.
	 * @param idpedido Se recibe como par�metro el idpedido en el sistema de Contact Center.
	 * @param numPedidoPixel Se recibe el n�mero de pedido que le fue asignado al pedido en la tienda correspondiente.
	 * @return Se retorna un valor booleano que indica si la actualizaci�n fue exitosa(true) o no fue exitosa(false).
	 */
	public static boolean actualizarEstadoNumeroPedidoPixel(int idpedido, int numPedidoPixel)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			// Actualizamos la tabla pedido con el numero pedido pixel y le ponemos estado al pedido = 1, indicando que ya fue enviado a la tienda.
			String update = "update pedido set numposheader = " + numPedidoPixel + " , enviadoPixel = 1  where idpedido = "+ idpedido;
			logger.info(update);
			stm.executeUpdate(update);
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
			return(false);
		}
		return(true);
	}
	
	/**
	 * M�todo que se encarga de eliminar un pedido que no ha sido confirmado en el sistema.
	 * @param idpedido Se recibe como par�metro el idpedido que se desea eliminar.
	 * @return Se retorna valor booleano indicando si el proceso fue exitoso (true) o si no fue exitoso (false).
	 */
	public static boolean eliminarPedidoSinConfirmar(int idpedido, int idcliente, String usuario)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from modificador_detalle_pedido  where iddetallepedidopadre in (select iddetalle_pedido from detalle_pedido where idpedido = "+ idpedido +")";
			logger.info(delete);
			stm.executeUpdate(delete);
			delete = "delete from adicion_detalle_pedido  where iddetallepedidopadre in (select iddetalle_pedido from detalle_pedido where idpedido = "+ idpedido +")";
			logger.info(delete);
			stm.executeUpdate(delete);
			delete = "delete from detalle_pedido  where idpedido = "+ idpedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			delete = "delete from pedido  where idpedido = "+ idpedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			String logDelete = "insert into log_reiniciar_pedido (idpedido, idcliente, usuario) values ("+ idpedido + " , " + idcliente + " , '" + usuario + "')";
			logger.info(logDelete);
			stm.executeUpdate(logDelete);
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
			return(false);
		}
		return(true);
	}
	
	public static boolean actualizarJSONPedido(int idpedido, String resultadoJSON)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set stringpixel = '" + resultadoJSON + "' where idpedido = "+ idpedido;
			logger.info(update);
			stm.executeUpdate(update);
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
			return(false);
		}
		return(true);
	}
	
	/**
	 * M�todo que se encarga de cerrar un pedido, para no poder adicionarle m�s detalles, con el cual se totaliza la 
	 * informaci�n.
	 * @param idpedido El id pedido que se finalizar�.
	 * @param idformapago La forma pago que tendr� el pedido que se va finalizar.
	 * @param valorformapago Valor con el cual se pagar� el pedido.
	 * @param valortotal
	 * @param idcliente idcliente asociado al pedido.
	 * @param insertado Marcaci�n que indica si el cliente fue actualizado o insertado.
	 * @param tiempoPedido se recibe como par�metro el tiempo pedido que se le dio al cliente al momento de confirmar dicho pedido.
	 * @return Se retorna un valor booleano con el resultado del proceso exitoso (true) o no exitoso (false).
	 */
	public static InsertarPedidoPixel finalizarPedido(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado, double tiempopedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			double valorTotal = 0;
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedido = " + idpedido + " " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				break;
			}
			String update = "update pedido set total_bruto =" + valorTotal* 0.92 + " , impuesto = " + valorTotal * 0.08 + " , total_neto =" + valorTotal + " , idestadopedido = 2, tiempopedido = " + tiempopedido +  " where idpedido = " + idpedido;
			logger.info(update);
			stm.executeUpdate(update);
			String insertformapago = "insert pedido_forma_pago (idpedido, idforma_pago, valortotal, valorformapago) values (" + idpedido + " , " + idformapago + " , " + valortotal + " , " + valorformapago + ")";
			logger.info(insertformapago);
			stm.executeUpdate(insertformapago);
			rs.close();
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
		//Debemos obtener el idTienda del Pedido que vamos a finalizar
		Tienda tiendaPedido = PedidoDAO.obtenerTiendaPedido(idpedido);
		
		
		//Recuperar la formapago de la tienda homologada
		int idformapagotienda = PedidoDAO.obtenerFormaPagoHomologadaTienda(tiendaPedido.getIdTienda(), idformapago);
		
		//Llamado Inserci�n Pixel
		
				
		//La invocaci�n del pedido ya no se realizar� as�
		//Main principal = new Main();
		Cliente cliente = ClienteDAO.obtenerClienteporID(idcliente);
		boolean indicadorAct = false;
		if(insertado == 0)
		{
			indicadorAct = true;
		}
		
		// Recolectamos todos los datos para realizar la inserci�n en el POS tienda
		InsertarPedidoPixel pedidoPixel = PedidoPixelDAO.confirmarPedidoPixel(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado, idformapagotienda);
		return(pedidoPixel);
	}
	
	
	public static InsertarPedidoPixel finalizarPedidoPOSPM(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado, double tiempopedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			double valorTotal = 0;
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedido = " + idpedido + " " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				break;
			}
			String update = "update pedido set total_bruto =" + valorTotal* 0.92 + " , impuesto = " + valorTotal * 0.08 + " , total_neto =" + valorTotal + " , idestadopedido = 2, tiempopedido = " + tiempopedido +  " where idpedido = " + idpedido;
			logger.info(update);
			stm.executeUpdate(update);
			String insertformapago = "insert pedido_forma_pago (idpedido, idforma_pago, valortotal, valorformapago) values (" + idpedido + " , " + idformapago + " , " + valortotal + " , " + valorformapago + ")";
			logger.info(insertformapago);
			stm.executeUpdate(insertformapago);
			rs.close();
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
		//Debemos obtener el idTienda del Pedido que vamos a finalizar
		Tienda tiendaPedido = PedidoDAO.obtenerTiendaPedido(idpedido);
		
		
		//Recuperar la formapago de la tienda homologada
		int idformapagotienda = PedidoDAO.obtenerFormaPagoHomologadaTienda(tiendaPedido.getIdTienda(), idformapago);
		
		//Llamado Inserci�n Pixel
		
				
		//La invocaci�n del pedido ya no se realizar� as�
		//Main principal = new Main();
		Cliente cliente = ClienteDAO.obtenerClienteporID(idcliente);
		boolean indicadorAct = false;
		if(insertado == 0)
		{
			indicadorAct = true;
		}
		
		// Recolectamos todos los datos para realizar la inserci�n en el POS tienda
		InsertarPedidoPixel pedidoPixel = PedidoPOSPMDAO.confirmarPedidoPOSPM(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado, idformapagotienda);
		return(pedidoPixel);
	}
	
	/**
	 * M�todo que se encarga de ciertas acciones propias de la confirmaci�n del pedido en el sistema tienda, dado que en el sistema
	 * de contact center ya fue confirmada la informaci�n.
	 * @param idpedido
	 * @param idformapago
	 * @param valorformapago
	 * @param valortotal
	 * @param idcliente
	 * @param insertado
	 * @return
	 */
	public static InsertarPedidoPixel finalizarPedidoReenvio(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		
		//Debemos obtener el idTienda del Pedido que vamos a finalizar
		Tienda tiendaPedido = PedidoDAO.obtenerTiendaPedido(idpedido);
		
		
		//Recuperar la formapago de la tienda homologada
		int idformapagotienda = PedidoDAO.obtenerFormaPagoHomologadaTienda(tiendaPedido.getIdTienda(), idformapago);
		Cliente cliente = ClienteDAO.obtenerClienteporID(idcliente);
		boolean indicadorAct = false;
		if(insertado == 0)
		{
			indicadorAct = true;
		}
		
		// Recolectamos todos los datos para realizar la inserci�n en el POS tienda
		InsertarPedidoPixel pedidoPixel = PedidoPixelDAO.confirmarPedidoPixel(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado, idformapagotienda);
		return(pedidoPixel);
	}
	
	/**
	 * M�todo que se encarga de retornar la url de una tienda, dado un idpedido	
	 * @param idpedido Se recibe como par�metro  el idpedido, del cual se requiere extrear la URL de la tienda
	 * @return Se retorna un String con la URL del servicio de la tienda
	 */
	public static String obtenerUrlTienda(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idtienda = 0;
		String url = "";
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtienda from pedido where idpedido = " + idpedido ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idtienda = rs.getInt("idtienda");
				break;
			}
			consulta = "select url from tienda where idtienda = " + idtienda;
			logger.info(consulta);
			rs = stm.executeQuery(consulta);
			while(rs.next()){
				url = rs.getString("url");
				break;
			}
			rs.close();
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
		return(url);
	}
	
	public static ArrayList<NomenclaturaDireccion> obtenerNomenclaturaDireccion()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		ArrayList<NomenclaturaDireccion> nomenclaturas = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from nomenclatura_direccion" ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idnomenclatura;
			String nomenclatura;
			while(rs.next()){
				idnomenclatura = rs.getInt("idnomenclatura");
				nomenclatura = rs.getString("nomenclatura");
				NomenclaturaDireccion nomen = new NomenclaturaDireccion(idnomenclatura, nomenclatura);
				nomenclaturas.add(nomen);
			}
			rs.close();
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
		return(nomenclaturas);
	}
	
	
	public static int obtenerFormaPagoHomologadaTienda(int idtienda, int idformapago)
	{
		Logger logger = Logger.getLogger("log_file");
		int idformapagohomo = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idformapagotienda from homologacion_forma_pago where idtienda = " + idtienda + " and idforma_pago = " + idformapago ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idformapagohomo = rs.getInt("idformapagotienda");
				break;
			}
			rs.close();
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
		return(idformapagohomo);
	}
	
	/**
	 * M�todo que se encarga de retornar los tipos Liquidos existentes en el sistema.
	 * @return Se retorna un ArrayList con los objetos Modelo TipoLiquido existentes en el sistema.
	 */
	public static ArrayList<TipoLiquido> ObtenerTiposLiquido()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<TipoLiquido> tiposLiquido = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtipo_liquido, nombre, capacidad	 from tipo_liquido " ;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtipo_liquido;
			String nombre;
			String capacidad;
			while(rs.next())
			{
				idtipo_liquido = rs.getInt("idtipo_liquido");
				nombre= rs.getString("nombre");
				capacidad = rs.getString("capacidad");
				TipoLiquido tipliq = new TipoLiquido(idtipo_liquido,nombre, capacidad);
				tiposLiquido.add(tipliq);
			}
			rs.close();
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
		
		return(tiposLiquido);
	}
	
	/**
	 * M�todo que permite la consulta de pedidos de acuerdo a los par�metros enviados para la consulta, esta consulta es exclusiva para los 
	 * productos que son registrados dentro del sistema contact center.
	 * @param fechainicial Fecha inicial de los pedidos a consultar.
	 * @param fechafinal Fecha final de los pedidos a consultar.
	 * @param tienda nombre de la tienda que se desea filtrar para la consulta de los pedidos.
	 * @param numeropedido En caso de desearlo se puede filtrar por un n�mero de pedido en espec�fico.
	 * @return Se retorna un ArrayList con objetos de tipo pedido con la informaci�n de los pedidos consultados.
	 */
	public static ArrayList<Pedido> ConsultaIntegradaPedidos(String fechainicial, String fechafinal, String tienda, int numeropedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <Pedido> consultaPedidos = new ArrayList();
		int idtienda = 0;
		String consulta = "";
		String fechaini = fechainicial.substring(6, 10)+"-"+fechainicial.substring(3, 5)+"-"+fechainicial.substring(0, 2);	
		String fechafin = fechafinal.substring(6, 10)+"-"+fechafinal.substring(3, 5)+"-"+fechafinal.substring(0, 2);	
		if((fechainicial.length()>0) && (fechafinal.length()>0) && (tienda.length()>0) && (numeropedido != 0))
		{
			if (tienda.equals("TODAS"))
			{
				consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, concat (c.nombre , '-' , c.apellido) nombrecliente, c.direccion, c.telefono, d.descripcion, a.fechapedido, c.idcliente, a.enviadopixel, a.numposheader, b.idtienda, b.url, a.stringpixel, a.fechainsercion, a.usuariopedido, e.nombre formapago, e.idforma_pago, a.tiempopedido from pedido a, tienda b, cliente c, estado_pedido d, forma_pago e, pedido_forma_pago f where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and e.idforma_pago = f.idforma_pago and f.idpedido = a.idpedido and a.fechapedido >=  '" + fechaini +"' and a.fechapedido <= '"+ fechafin +"'"  + " and a.numposheader = " + numeropedido;
			}else
			{
				idtienda = TiendaDAO.obteneridTienda(tienda);
				consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, concat (c.nombre , '-' , c.apellido) nombrecliente, c.direccion, c.telefono, d.descripcion, a.fechapedido, c.idcliente, a.enviadopixel, a.numposheader, b.idtienda, b.url, a.stringpixel, a.fechainsercion, a.usuariopedido, e.nombre formapago, e.idforma_pago, a.tiempopedido from pedido a, tienda b, cliente c, estado_pedido d, forma_pago e, pedido_forma_pago f where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and e.idforma_pago = f.idforma_pago and f.idpedido = a.idpedido and a.fechapedido >=  '" + fechaini +"' and a.fechapedido <= '"+ fechafin +"' and a.idtienda =" + idtienda + " and a.numposheader = " + numeropedido;
			}
		}else if((fechainicial.length()>0) && (fechafinal.length()>0) && (tienda.length()>0))
		{
			if (tienda.equals("TODAS"))
			{
				consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, concat (c.nombre , '-' , c.apellido) nombrecliente, c.direccion, c.telefono, d.descripcion, a.fechapedido, c.idcliente, a.enviadopixel, a.numposheader, b.idtienda, b.url, a.stringpixel, a.fechainsercion, a.usuariopedido, e.nombre formapago, e.idforma_pago, a.tiempopedido from pedido a, tienda b, cliente c, estado_pedido d, forma_pago e, pedido_forma_pago f where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and e.idforma_pago = f.idforma_pago and f.idpedido = a.idpedido and a.fechapedido >=  '" + fechaini +"' and a.fechapedido <= '"+ fechafin + "'";
			}else
			{
				idtienda = TiendaDAO.obteneridTienda(tienda);
				consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, concat (c.nombre , '-' , c.apellido) nombrecliente, c.direccion, c.telefono, d.descripcion, a.fechapedido, c.idcliente, a.enviadopixel, a.numposheader, b.idtienda, b.url, a.stringpixel, a.fechainsercion, a.usuariopedido, e.nombre formapago, e.idforma_pago, a.tiempopedido from pedido a, tienda b, cliente c, estado_pedido d, forma_pago e, pedido_forma_pago f where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and e.idforma_pago = f.idforma_pago and f.idpedido = a.idpedido and a.fechapedido >=  '" + fechaini +"' and a.fechapedido <= '"+ fechafin +"' and a.idtienda =" + idtienda ;
			}
		}
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int idpedido;
			String nombreTienda;
			double totalBruto;
			double impuesto;
			double totalNeto;
			String nombreCliente;
			String estadoPedido;
			String fechaPedido;
			int idcliente;
			int enviadopixel;
			int numposheader;
			String url;
			String stringpixel;
			String fechainsercion;
			String usuariopedido;
			String telefono;
			String direccion;
			String formapago;
			int idformapago;
			double tiempopedido;
			while(rs.next())
			{
				idpedido = rs.getInt("idpedido");
				nombreTienda = rs.getString("nombre");
				totalBruto = rs.getDouble("total_bruto");
				impuesto = rs.getDouble("impuesto");
				totalNeto = rs.getDouble("total_neto");
				nombreCliente = rs.getString("nombrecliente");
				estadoPedido = rs.getString("descripcion");
				fechaPedido = rs.getString("fechapedido");
				idcliente = rs.getInt("idcliente");
				enviadopixel = rs.getInt("enviadopixel");
				numposheader = rs.getInt("numposheader");
				stringpixel = rs.getString("stringpixel");
				fechainsercion = rs.getString("fechainsercion");
				usuariopedido = rs.getString("usuariopedido");
				direccion = rs.getString("direccion");
				telefono = rs.getString("telefono");
				url = rs.getString("url");
				formapago = rs.getString("formapago");
				idformapago = rs.getInt("idforma_pago");
				tiempopedido = rs.getDouble("tiempopedido");
				Tienda tiendapedido = new Tienda(idtienda, nombreTienda, "", url, 0);
				Pedido cadaPedido = new Pedido(idpedido,  nombreTienda,totalBruto, impuesto, totalNeto,
						estadoPedido, fechaPedido, nombreCliente, idcliente, enviadopixel,numposheader, tiendapedido, stringpixel, fechainsercion, usuariopedido, direccion, telefono, formapago, idformapago, tiempopedido);
				consultaPedidos.add(cadaPedido);
			}
			rs.close();
			stm.close();
			con1.close();

		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(consultaPedidos);
	}
	
	/**
	 * M�todo que se encarga de eliminar un detalle pedido de acuerdo al par�metro recibido.
	 * @param iddetallepedido Se recibe como par�metro el iddetallepedido que se desea eliminar
	 * @return Se retorna un ArrayList de n�meros enteros qeu responden a los iddetallepedido eliminados los cuales 
	 * pueden ser varios debido a que si eliminamos un detalle pedido que tenga asociadas adiciones, tambien
	 * deberemos de eliminar las adiciones.
	 */
	public static ArrayList<Integer> EliminarDetallePedido(int iddetallepedido)
	{
		ArrayList<Integer> idDetallePedidosBorrados = new ArrayList();
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		boolean respuesta;
		try
		{
			// Debemos de validar que el item del pedido tenga o no adiciones, si tiene adiciones deber� de eliminar los dealles pedido
			//asociado a las adiciones del producto principal
			String consulta = "select iddetallepedidoadicion from adicion_detalle_pedido where iddetallepedidopadre = " + iddetallepedido;
			logger.info(consulta);
			Statement stm = con1.createStatement();
			Statement stm1 = con1.createStatement();
			Statement stm2 = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			String delete = "";
			String consultaModDetPed = "";
			while (rs.next())
			{
				//debemos borrar tambien el detalle de la adici�n
				int valorEliminar = Integer.parseInt( rs.getString("iddetallepedidoadicion"));
				delete = "delete from adicion_detalle_pedido  where iddetallepedidoadicion = " + valorEliminar;
				logger.info(delete);
				stm1.executeUpdate(delete);
				//borramos el detalle pedido asociado a ada adici�n
				delete = "delete from detalle_pedido  where iddetalle_pedido = " + valorEliminar;
				logger.info(delete);
				stm1.executeUpdate(delete);
				idDetallePedidosBorrados.add(new Integer(valorEliminar));
			}
			//borrado en detalle de adiciones en caso de que sea una adicion, en caso que solo estemos eliminando una adici�n
			delete = "delete from adicion_detalle_pedido  where iddetallepedidoadicion = " + iddetallepedido; 
			logger.info(delete);
			stm1.executeUpdate(delete);
			//borrado de la tabla de modificadores sacamos los modificadores que tienen detalle pedido porqeu tienen precio
			consultaModDetPed = "select iddetallepedidoasociado from modificador_detalle_pedido  where iddetallepedidopadre = " + iddetallepedido + " and iddetallepedidoasociado <> 0";
			logger.info(consultaModDetPed);
			rs = stm2.executeQuery(consultaModDetPed);
			while (rs.next())
			{
				//debemos borrar tambien el detalle de la adici�n
				int valorEliminarMod = Integer.parseInt( rs.getString("iddetallepedidoasociado"));
				delete = "delete from detalle_pedido  where iddetalle_pedido = " + valorEliminarMod;
				logger.info(delete);
				stm1.executeUpdate(delete);
				idDetallePedidosBorrados.add(new Integer(valorEliminarMod));
			}
			delete = "delete from modificador_detalle_pedido  where iddetallepedidopadre = " + iddetallepedido; 
			logger.info(delete);
			stm1.executeUpdate(delete);
			// Se hace la eliminaci�n final del detalle pedido pasado como parametro.
			delete = "delete from detalle_pedido  where iddetalle_pedido = " + iddetallepedido; 
			logger.info(delete);
			idDetallePedidosBorrados.add(new Integer(iddetallepedido));
			stm1.executeUpdate(delete);
			stm.close();
			stm1.close();
			stm2.close();
			con1.close();
			//Debemos de ejecutar una reconstrucci�n del campo adici�n del ippedido
			
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
		return(idDetallePedidosBorrados);
		
	}
	

	/**
	 * M�todo que se encarga de dado un iddetallepedido, retornar todas las adiciones asociados a dicho Item.
	 * @param iddetallepedido Se recibe como par�metro el iddetallepedido del cual se requiere saber las adiciones.
	 * @return Se retorna un ArrayList con objetos Modelo DetallePedidoAdicion asociados al iddetallepedido pasado
	 * como par�metro.
	 */
	public static ArrayList<DetallePedidoAdicion> ObtenerAdicionDetallePedido (int iddetallepedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<DetallePedidoAdicion> consultaAdiciones = new ArrayList();
		String consulta = "select a.iddetallepedidopadre, a.iddetallepedidoadicion, a.idespecialidad1, a.cantidad1, a.idespecialidad2, "
				+ "a.cantidad2, b.idproducto from adicion_detalle_pedido a, detalle_pedido b where a.iddetallepedidoadicion = b.iddetalle_pedido and a.iddetallepedidopadre =" + iddetallepedido; 
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int iddetallepedidopadre;
			int iddetallepedidoadicion;
			int idespecialidad1;
			double cantidad1;
			int idespecialidad2;
			double cantidad2;
			int idproducto;
			while(rs.next())
			{
				iddetallepedidopadre = Integer.parseInt(rs.getString("iddetallepedidopadre"));
				iddetallepedidoadicion = Integer.parseInt(rs.getString("iddetallepedidoadicion"));
				idespecialidad1 = Integer.parseInt(rs.getString("idespecialidad1"));
				cantidad1 = Double.parseDouble(rs.getString("cantidad1"));
				idespecialidad2 = Integer.parseInt(rs.getString("idespecialidad2"));
				cantidad2 = Double.parseDouble(rs.getString("cantidad2"));
				idproducto = Integer.parseInt(rs.getString("idproducto"));
				DetallePedidoAdicion detAdicion = new DetallePedidoAdicion(iddetallepedidopadre, iddetallepedidoadicion, idespecialidad1, idespecialidad2, cantidad1, cantidad2);
				detAdicion.setIdproducto(idproducto);
				consultaAdiciones.add(detAdicion);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e)
		{
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(consultaAdiciones);
	}
	
	/**
	 * M�todo que se encarga de retornar todos los modificadores asociados a un iddetallepedido, enviado como par�metro.
	 * @param iddetallepedido Se recibe como par�metro un iddetallepedido con base en el cual se retornar�n todos los
	 * modificadores CON/SIN asociados al detallepedido.
	 * @return Se retorna un ArrayList con objetos tipo ModificadorDetallePedido asociados al iddetallepedido
	 * pasado como par�metro.
	 */
	public static ArrayList<ModificadorDetallePedido> ObtenerModificadorDetallePedido (int iddetallepedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<ModificadorDetallePedido> consultaModificadores = new ArrayList();
		String consulta = "select a.iddetallepedidopadre,  a.idproductoespecialidad1, a.idproductoespecialidad2, "
				+ "a.cantidad, a.iddetallepedidoasociado from modificador_detalle_pedido a where a.iddetallepedidopadre =" + iddetallepedido; 
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int iddetallepedidopadre;
			int idproductoespecialidad1;
			int idproductoespecialidad2;
			int iddetallepedidoasociado;
			double cantidad;
			while(rs.next())
			{
				iddetallepedidopadre = Integer.parseInt(rs.getString("iddetallepedidopadre"));
				idproductoespecialidad1 = Integer.parseInt(rs.getString("idproductoespecialidad1"));
				idproductoespecialidad2 = Integer.parseInt(rs.getString("idproductoespecialidad2"));
				cantidad = Double.parseDouble(rs.getString("cantidad"));
				iddetallepedidoasociado = Integer.parseInt(rs.getString("iddetallepedidoasociado"));
				ModificadorDetallePedido detModificador = new ModificadorDetallePedido(0,iddetallepedidopadre,idproductoespecialidad1, idproductoespecialidad2, cantidad, iddetallepedidoasociado);
				consultaModificadores.add(detModificador);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e)
		{
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(consultaModificadores);
	}
	
	/**
	 * M�todo que se encarga de retornar el detalle de un pedido, con base en el id pedido recibido como par�metro
	 * @param numeropedido Se recibe como par�metro del idpedido con base en el cual se retornar� la informaci�n.
	 * @return Se retorna un ArrayList con objetos tipo DetallePedido de acuerdo alos par�metros recibidos.
	 */
	public static ArrayList<DetallePedido> ConsultarDetallePedido(int numeropedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <DetallePedido> consultaDetallePedidos = new ArrayList();
		String consulta = "select a.iddetalle_pedido, a.idproducto, b.nombre, a.cantidad, a.idespecialidad1, a.idespecialidad2, concat(c.nombre , a.modespecialidad1) especialidad1, concat(d.nombre , a.modespecialidad2) especialidad2, "
				+ "a.valorUnitario, a.valorTotal, a.adicion, a.observacion, e.descripcion liquido , f.descripcion excepcion, a.idexcepcion, a.idsabortipoliquido  from detalle_pedido a left outer join especialidad "
				+ "c on a.idespecialidad1 = c.idespecialidad left outer join especialidad d on a.idespecialidad2 = d.idespecialidad"
				+ " left outer join sabor_x_tipo_liquido e on a.idsabortipoliquido = e.idsabor_x_tipo_liquido "
				+ "left outer join excepcion_precio f on a.idexcepcion = f.idexcepcion"
				+ ",producto b where a.idproducto = b.idproducto and idpedido = " + numeropedido;
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int iddetallepedido;
			int idproducto;
			String nombreproducto;
			double cantidad;
			String especialidad1;
			int idespecialidad1;
			String especialidad2;
			int idespecialidad2;
			double valorunitario;
			double valortotal;
			String adicion;
			String observacion;
			String liquido;
			String excepcion;
			int idexcepcion;
			int idsabortipoliquido;
			while(rs.next())
			{
				iddetallepedido = rs.getInt("iddetalle_pedido");
				idproducto = rs.getInt("idproducto");
				nombreproducto = rs.getString("nombre");
				cantidad = rs.getDouble("cantidad");
				especialidad1 = rs.getString("especialidad1");
				idespecialidad1 = rs.getInt("idespecialidad1");
				especialidad2 = rs.getString("especialidad2");
				idespecialidad2 = rs.getInt("idespecialidad2");
				valorunitario = rs.getDouble("valorUnitario");
				valortotal = rs.getDouble("valorTotal");
				adicion = rs.getString("adicion");
				observacion = rs.getString("observacion");
				liquido = rs.getString("liquido");
				excepcion = rs.getString("excepcion");
				idexcepcion = rs.getInt("idexcepcion");
				idsabortipoliquido = rs.getInt("idsabortipoliquido");
				DetallePedido cadaDetallePedido = new DetallePedido(iddetallepedido, nombreproducto, idproducto, cantidad,especialidad1, idespecialidad1, especialidad2, idespecialidad2,valorunitario, valortotal,adicion,observacion,liquido, excepcion, numeropedido, idexcepcion, idsabortipoliquido,"");
				consultaDetallePedidos.add(cadaDetallePedido);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
			
		}
		return(consultaDetallePedidos);
	}

	
	/**
	 * M�todo que recibe como par�metro un id pedido y con base en el retorna todos lo detallespedidos asociados, sin 
	 * incluir las adiciones.
	 * @param numeropedido Se recibe como par�metro un n�mero de pedido con base en el cual se realiza la consulta.
	 * @return Se retorna un ArrayList con objetos Modelo DetallePedido asociados al pedido pasado como par�metro y excluyendo
	 * las adiciones.
	 */
	public static ArrayList<DetallePedido> ConsultarDetallePedidoSinAdiciones(int numeropedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <DetallePedido> consultaDetallePedidos = new ArrayList();
		String consulta = "select a.iddetalle_pedido, a.idproducto, b.nombre, a.cantidad, a.idespecialidad1, a.idespecialidad2, c.nombre especialidad1, d.nombre especialidad2, "
				+ "a.valorUnitario, a.valorTotal, a.adicion, a.observacion, e.descripcion liquido , f.descripcion excepcion, a.idexcepcion, a.idsabortipoliquido  from detalle_pedido a left outer join especialidad "
				+ "c on a.idespecialidad1 = c.idespecialidad left outer join especialidad d on a.idespecialidad2 = d.idespecialidad"
				+ " left outer join sabor_x_tipo_liquido e on a.idsabortipoliquido = e.idsabor_x_tipo_liquido "
				+ "left outer join excepcion_precio f on a.idexcepcion = f.idexcepcion"
				+ ",producto b where a.idproducto = b.idproducto and b.tipo in ('OTROS' , 'PIZZA') and idpedido = " + numeropedido;
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int iddetallepedido;
			int idproducto;
			String nombreproducto;
			double cantidad;
			String especialidad1;
			int idespecialidad1;
			String especialidad2;
			int idespecialidad2;
			double valorunitario;
			double valortotal;
			String adicion;
			String observacion;
			String liquido;
			String excepcion;
			int idexcepcion;
			int idsabortipoliquido;
			while(rs.next())
			{
				iddetallepedido = rs.getInt("iddetalle_pedido");
				idproducto = rs.getInt("idproducto");
				nombreproducto = rs.getString("nombre");
				cantidad = rs.getDouble("cantidad");
				especialidad1 = rs.getString("especialidad1");
				idespecialidad1 = rs.getInt("idespecialidad1");
				especialidad2 = rs.getString("especialidad2");
				idespecialidad2 = rs.getInt("idespecialidad2");
				valorunitario = rs.getDouble("valorUnitario");
				valortotal = rs.getDouble("valorTotal");
				adicion = rs.getString("adicion");
				observacion = rs.getString("observacion");
				liquido = rs.getString("liquido");
				excepcion = rs.getString("excepcion");
				idexcepcion = rs.getInt("idexcepcion");
				idsabortipoliquido = rs.getInt("idsabortipoliquido");
				DetallePedido cadaDetallePedido = new DetallePedido(iddetallepedido, nombreproducto, idproducto, cantidad,especialidad1, idespecialidad1, especialidad2, idespecialidad2,valorunitario, valortotal,adicion,observacion,liquido, excepcion, numeropedido, idexcepcion, idsabortipoliquido,"");
				consultaDetallePedidos.add(cadaDetallePedido);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(consultaDetallePedidos);
	}
	
	/**
	 * M�todo que retornar dado un idpedidopadre, retorna lo detalles de pedido asociados a este, como adiciones, productos
	 * o modificadores con precio
	 * @param iddetpedidopadre se recibe como param�tro el iddetalle pedido que deber� ser un productos de la tipolog�a
	 * PIZZA u OTROS.
	 * @return
	 */
	public static ArrayList<DetallePedido> ConsultarDetallePedidoPorPadre(int iddetpedidopadre)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <DetallePedido> consultaDetallePedidos = new ArrayList();
		String consulta = "select a.iddetalle_pedido, a.idpedido, a.idproducto, b.nombre, a.cantidad, a.idespecialidad1, a.idespecialidad2, c.nombre especialidad1, d.nombre especialidad2, "
				+ "a.valorUnitario, a.valorTotal, a.adicion, a.observacion, e.descripcion liquido , f.descripcion excepcion, a.idexcepcion, a.idsabortipoliquido, b.tipo  from detalle_pedido a left outer join especialidad "
				+ "c on a.idespecialidad1 = c.idespecialidad left outer join especialidad d on a.idespecialidad2 = d.idespecialidad"
				+ " left outer join sabor_x_tipo_liquido e on a.idsabortipoliquido = e.idsabor_x_tipo_liquido "
				+ "left outer join excepcion_precio f on a.idexcepcion = f.idexcepcion"
				+ ",producto b where a.idproducto = b.idproducto and a.iddetalle_pedido in  " 
				+ " (select iddetalle_pedido from detalle_pedido where iddetalle_pedido = " + iddetpedidopadre 
				+ " union select iddetalle_pedido from detalle_pedido where observacion = '"+ "Producto Incluido-" + iddetpedidopadre +"'"
				+ " union select iddetallepedidoadicion from adicion_detalle_pedido where iddetallepedidopadre = " + iddetpedidopadre
				+ " union select iddetallepedidoasociado from modificador_detalle_pedido where iddetallepedidopadre = " + iddetpedidopadre + ")"; 
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int idpedido;
			int iddetallepedido;
			int idproducto;
			String nombreproducto;
			double cantidad;
			String especialidad1;
			int idespecialidad1;
			String especialidad2;
			int idespecialidad2;
			double valorunitario;
			double valortotal;
			String adicion;
			String observacion;
			String liquido;
			String excepcion;
			int idexcepcion;
			int idsabortipoliquido;
			String tipoProducto;
			while(rs.next())
			{
				iddetallepedido = rs.getInt("iddetalle_pedido");
				idpedido = rs.getInt("idpedido");
				idproducto = rs.getInt("idproducto");
				nombreproducto = rs.getString("nombre");
				cantidad = rs.getDouble("cantidad");
				especialidad1 = rs.getString("especialidad1");
				idespecialidad1 = rs.getInt("idespecialidad1");
				especialidad2 = rs.getString("especialidad2");
				idespecialidad2 = rs.getInt("idespecialidad2");
				valorunitario = rs.getDouble("valorUnitario");
				valortotal = rs.getDouble("valorTotal");
				adicion = rs.getString("adicion");
				observacion = rs.getString("observacion");
				liquido = rs.getString("liquido");
				excepcion = rs.getString("excepcion");
				idexcepcion = rs.getInt("idexcepcion");
				idsabortipoliquido = rs.getInt("idsabortipoliquido");
				tipoProducto = rs.getString("tipo");
				DetallePedido cadaDetallePedido = new DetallePedido(iddetallepedido, nombreproducto, idproducto, cantidad,especialidad1, idespecialidad1, especialidad2, idespecialidad2,valorunitario, valortotal,adicion,observacion,liquido, excepcion, idpedido , idexcepcion, idsabortipoliquido,tipoProducto);
				consultaDetallePedidos.add(cadaDetallePedido);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(consultaDetallePedidos);
	}
	
	/**
	 * mPetodo que se encarga de retornar la informaci�n de una entidad Tienda, con base en la informaci�n recibida como 
	 * par�metro.
	 * @param idpedido Se recibe como par�metro el idpedido con base en el cual se retornar� la informacion de la tienda
	 * asociada al pedido.
	 * @return Se retorna un objeto de tipo Modelo Tienda con base en el idpedido recibido como par�metro.
	 */
	public static Tienda obtenerTiendaPedido(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Tienda tienda = new Tienda();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idtienda from pedido p where idpedido = " + idpedido;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idTienda;
			while(rs.next()){
				idTienda = rs.getInt("idtienda");
				tienda = TiendaDAO.retornarTienda(idTienda);
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
		return(tienda);
		
	}
	
	/**
	 * M�todo que se encarga de retornar la forma de pago dado un id pedido.
	 * @param idPedido Se recibe como par�metro el idpedido, del cual se retorna la forma de pago
	 * @return Se retorna un objeto Modelo FormaPago asociado al pedido pasado como par�metro.
	 */
	public static FormaPago obtenerFormaPagoPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		FormaPago formaPago = new FormaPago();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedido_forma_pago, a.idforma_pago,valortotal, a.valorformapago, b.nombre from pedido_forma_pago a, forma_pago b where a.idforma_pago = b.idforma_pago and a.idpedido = " + idPedido;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idpedidoformapago;
			int idformapago;
			double valortotal;
			double valorformapago;
			String nombre;
			while(rs.next()){
				idpedidoformapago = rs.getInt("idpedido_forma_pago");
				idformapago = rs.getInt("idforma_pago");
				valortotal = rs.getDouble("valortotal");
				valorformapago = rs.getDouble("valorformapago");
				nombre = rs.getString("nombre");
			formaPago = new FormaPago(idformapago,nombre,"",valortotal, valorformapago);
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
		return(formaPago);
	}
	
	/**
	 * M�todo que se encarga de retornar un ArrayList con todos los productos incluidos en el sistema, con el objetivo
	 * que en la capa de presentaci�n, se controle la adici�n de un producto que tenga productos adicionales.
	 * @return Se retorna un arrayList con los productos incluidos parametrizados en el sistema.
	 */
	public static ArrayList<ProductoIncluido> obtenerProductosIncluidos()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		ArrayList<ProductoIncluido> productosIncluidos = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproductoincluido, a.idproductopadre, a.idproductohijo, a.cantidad, b.nombre, b.preciogeneral "
					+ "from producto_incluido a , producto b "
					+ " where a.idproductohijo = b.idproducto";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idproductoincluido;
			int idproductopadre;
			int idproductohijo;
			double cantidad;
			String nombre;
			double preciogeneral;
			while(rs.next()){
				idproductoincluido = rs.getInt("idproductoincluido");
				idproductopadre = rs.getInt("idproductopadre");
				idproductohijo = rs.getInt("idproductohijo");
				cantidad= rs.getDouble("cantidad");
				nombre = rs.getString("nombre");
				preciogeneral = rs.getDouble("preciogeneral");
				productosIncluidos.add(new ProductoIncluido(idproductoincluido,idproductopadre,idproductohijo,cantidad,nombre, preciogeneral));
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
		return(productosIncluidos);
	}
	
	public static int actualizarClienteMemcode(int idCliente, int memcode)
	{
		Logger logger = Logger.getLogger("log_file");
		int idClienteActualizado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			//Para actualizar el cliente el idcliente debe ser diferente de vac�o.
			Statement stm = con1.createStatement();
			String update = "update cliente set memcode = " + memcode + "  where idcliente = " + idCliente; 
			logger.info(update);
			stm.executeUpdate(update);
			idClienteActualizado = idCliente;
			
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
		logger.info("id cliente actualizado" + idClienteActualizado);
		return(idClienteActualizado);
	}
	
	/**
	 * M�todo que se encarga de retornar todas las gaseosas incluidas en productos homologadas en todas las tiendas
	 * @return Se retorna un arrayList con objetos HomologaGaseosaIncluida los cuales basicamente traen las propiedades
	 * de idtienda e idsabortipoliquido definido para las homologaciones.
	 */
	public static ArrayList<HomologaGaseosaIncluida> obtenerHomologacionGaseosaIncluida()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		ArrayList<HomologaGaseosaIncluida> gaseosaHomologada = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idtienda, a.idsabortipoliquidoint "
					+ "from homologacion_producto a where a.idsabortipoliquidoint  > 0  " ;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idtienda;
			int idsabortipoliquido;
			while(rs.next()){
				idtienda = rs.getInt("idtienda");
				idsabortipoliquido = rs.getInt("idsabortipoliquidoint");
				gaseosaHomologada.add(new HomologaGaseosaIncluida(idtienda, idsabortipoliquido));
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
		return(gaseosaHomologada);
	}
	
	//M�todo que buscar� traer los �ltimos pedidos de un cliente.
	public static ArrayList<Pedido> ConsultaUltimosPedidosCliente(int idCliente)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <Pedido> consultaPedidos = new ArrayList();
		int idtienda = 0;
		String consulta = "";
		consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, concat (c.nombre , '-' , c.apellido) nombrecliente, c.direccion, c.telefono, d.descripcion, a.fechapedido, c.idcliente, a.enviadopixel, a.numposheader, b.idtienda, b.url, a.stringpixel, a.fechainsercion, a.usuariopedido, e.nombre formapago, e.idforma_pago, a.tiempopedido from pedido a, tienda b, cliente c, estado_pedido d, forma_pago e, pedido_forma_pago f where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and e.idforma_pago = f.idforma_pago and f.idpedido = a.idpedido and a.idcliente = " + idCliente + " order by fechapedido desc limit 3";
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int idpedido;
			String nombreTienda;
			double totalBruto;
			double impuesto;
			double totalNeto;
			String nombreCliente;
			String estadoPedido;
			String fechaPedido;
			int idcliente;
			int enviadopixel;
			int numposheader;
			String url;
			String stringpixel;
			String fechainsercion;
			String usuariopedido;
			String telefono;
			String direccion;
			String formapago;
			int idformapago;
			double tiempopedido;
			while(rs.next())
			{
				idpedido = rs.getInt("idpedido");
				nombreTienda = rs.getString("nombre");
				totalBruto = rs.getDouble("total_bruto");
				impuesto = rs.getDouble("impuesto");
				totalNeto = rs.getDouble("total_neto");
				nombreCliente = rs.getString("nombrecliente");
				estadoPedido = rs.getString("descripcion");
				fechaPedido = rs.getString("fechapedido");
				idcliente = rs.getInt("idcliente");
				enviadopixel = rs.getInt("enviadopixel");
				numposheader = rs.getInt("numposheader");
				stringpixel = rs.getString("stringpixel");
				fechainsercion = rs.getString("fechainsercion");
				usuariopedido = rs.getString("usuariopedido");
				direccion = rs.getString("direccion");
				telefono = rs.getString("telefono");
				url = rs.getString("url");
				formapago = rs.getString("formapago");
				idformapago = rs.getInt("idforma_pago");
				tiempopedido = rs.getDouble("tiempopedido");
				Tienda tiendapedido = new Tienda(idtienda, nombreTienda, "", url,0);
				Pedido cadaPedido = new Pedido(idpedido,  nombreTienda,totalBruto, impuesto, totalNeto,
						estadoPedido, fechaPedido, nombreCliente, idcliente, enviadopixel,numposheader, tiendapedido, stringpixel, fechainsercion, usuariopedido, direccion, telefono, formapago, idformapago, tiempopedido);
				consultaPedidos.add(cadaPedido);
			}
			rs.close();
			stm.close();
			con1.close();

		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(consultaPedidos);
	}
	
	public static ArrayList<DireccionFueraZona> ConsultarDireccionesPedido(String fechainicial, String fechafinal, String strIdMuni)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <DireccionFueraZona> consultaDirs = new ArrayList();
		String consulta = "";
		String fechaini = fechainicial.substring(6, 10)+"-"+fechainicial.substring(3, 5)+"-"+fechainicial.substring(0, 2) + " 00:00:00";	
		String fechafin = fechafinal.substring(6, 10)+"-"+fechafinal.substring(3, 5)+"-"+fechafinal.substring(0, 2) + " 23:59:00";	
		//Modificamos consulta para incluir el n�mero de pedidos que tiene el cliente, para realizar un control
		//Validamos si el municipio es igual a cero es porque vamos a consultar todos los municipio, sino es as�
		// es porque la consulta deber� filtrar por municipio.
		if(strIdMuni.equals(new String ("TODOS")))
		{
			consulta = "select a.idpedido id, b.direccion, c.nombre municipio, b.idcliente, b.latitud, b.longitud, b.telefono, b.nombre, b.apellido, a.fechapedido fecha_ingreso from pedido a, cliente b, municipio c where a.idcliente = b.idcliente and c.idmunicipio = b.idmunicipio and  a.fechapedido >= '"+ fechaini + "' and a.fechapedido <= '" + fechafin + "'";
		}else
		{
			int idMunicipio = Integer.parseInt(strIdMuni);
			consulta = "select a.idpedido id, b.direccion, c.nombre municipio, b.idcliente, b.latitud, b.longitud, b.telefono, b.nombre, b.apellido, a.fechapedido fecha_ingreso from pedido a, cliente b, municipio c where a.idcliente = b.idcliente and c.idmunicipio = b.idmunicipio and  a.fechapedido >= '"+ fechaini + "' and a.fechapedido <= '" + fechafin + "' and b.idmunicipio = " + idMunicipio;
		}
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int id = 0;
			String direccion = "";
			String municipio = "";
			int idCliente = 0;
			double latitud = 0;
			double longitud = 0;
			String telefono = "";
			String nombre = "";
			String apellido = "";
			String fechaIngreso ="";
			while(rs.next())
			{
				id = rs.getInt("id");
				direccion = rs.getString("direccion");
				municipio = rs.getString("municipio");
				idCliente = rs.getInt("idcliente");
				latitud = rs.getDouble("latitud");
				longitud = rs.getDouble("longitud");
				telefono = rs.getString("telefono");
				nombre = rs.getString("nombre");
				apellido = rs.getString("apellido");
				fechaIngreso = rs.getString("fecha_ingreso");
				//Luego de tomada la informaci�n de la cantidad de pedidos, validamos que los pedidos llevados al cliente seran 0 o 1.
				DireccionFueraZona dirFuera = new DireccionFueraZona(id, direccion, municipio, idCliente, latitud, longitud, telefono, nombre, apellido);
				dirFuera.setFechaIngreso(fechaIngreso);
				consultaDirs.add(dirFuera);
			}
			rs.close();
			stm.close();
			con1.close();

		}catch(Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(consultaDirs);
	}
	
	
}
