package capaDAO;

import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Especialidad;
import capaModelo.Producto;
import capaModelo.SaborLiquido;
import capaModelo.Tienda;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.TipoLiquido;
import capaModelo.Pedido;
import capaModelo.DetallePedidoPixel;
import capaModelo.DetallePedidoAdicion;
import org.apache.log4j.Logger;
import pixelpos.Main;
import capaModelo.DetallePedidoPixel;
import capaModelo.ModificadorDetallePedido;

import java.sql.ResultSet;

public class PedidoDAO {
	
	
	public static ArrayList<Especialidad> obtenerEspecialidad()
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Especialidad> especialidades = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select e.idespecialidad, e.nombre, e.abreviatura from especialidad e";
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
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(especialidades);
		
	}
	
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
		}catch (Exception e){
			logger.error(e.toString());
			
		}
		return(otrosProducto);
		
	}
	
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
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(adicionProducto);
		
	}

	public static ArrayList<Producto> obtenerTodosProductos() {
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Producto> todosProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select p.idproducto, p.idreceta, p.nombre, p.descripcion, p.impuesto, p.tipo, p.producto_asocia_adicion, p.preciogeneral, p.incluye_liquido, p.idtipo_liquido from producto p  ";
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
				Producto prod = new Producto(idProducto, idReceta, nombre, descripcion,impuesto, tipo,productoasociaadicion, precio, incluye_liquido, idtipo_liquido);
				todosProducto.add(prod);
			}
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(todosProducto);
		
	}
	
	public static ArrayList<SaborLiquido> ObtenerSaboresLiquidoProducto(int idProdu)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saboresLiquido = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idsabor_x_tipo_liquido, a.descripcion, b.idtipo_liquido, b.nombre, c.idproducto, c.descripcion from sabor_x_tipo_liquido a , tipo_liquido b, producto c where a.idtipo_liquido = b.idtipo_liquido and c.idtipo_liquido = b.idtipo_liquido and c.idproducto = " + idProdu;
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
			
		}
			catch (Exception e){
				logger.error(e.toString());
		}
		return(saboresLiquido);
	}
	
	
	public static ArrayList<SaborLiquido> ObtenerSaboresLiquidoExcepcion(int idExce)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<SaborLiquido> saboresLiquido = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idsabor_x_tipo_liquido, a.descripcion, b.idtipo_liquido, b.nombre, c.idexcepcion, c.descripcion from sabor_x_tipo_liquido a , tipo_liquido b, excepcion_precio c where a.idtipo_liquido = b.idtipo_liquido and c.idtipoliquido = b.idtipo_liquido and c.idexcepcion = " + idExce;
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
			
		}
			catch (Exception e){
				logger.error(e.toString());
		}
		
		return(saboresLiquido);
	}
	
	
	public static int InsertarEncabezadoPedido(int idtienda, int idcliente)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPedidoInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente, idestadopedido) values (" + idtienda + ", " + idcliente + ", 1" + ")"; 
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
			return(0);
		}
		return(idPedidoInsertado);
	}
	
	
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
			return(0);
		}
		return(idDetallePedidoInsertado);
	}
	
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
			return(0);
		}
		return(idAdicion);
	}
	
	public static int InsertarModificadorDetallePedido(ModificadorDetallePedido modDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idModificador = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert modificador_detalle_pedido (iddetallepedidopadre, idproductoespecialidad1,idproductoespecialidad2,cantidad) "
					+ "values (" + modDetallePedido.getIddetallepedidopadre() + " , " + modDetallePedido.getIdproductoespecialidad1() + " , "
							+  modDetallePedido.getIdproductoespecialidad2() + " , " + modDetallePedido.getCantidad() + ")"; 
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
			return(0);
		}
		return(idModificador);
	}
	
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
			return(0);
		}
		return(idespecialidad);
	}
	
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
		}
		catch (Exception e){
			logger.error(e.toString());
			valorTotal = 0;
		}
		System.out.println(valorTotal);
		return(valorTotal);
	}
	
	public static boolean finalizarPedido(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado)
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
			String update = "update pedido set total_bruto =" + valorTotal* 0.92 + " , impuesto = " + valorTotal * 0.08 + " , total_neto =" + valorTotal + " , idestadopedido = 2 where idpedido = " + idpedido;
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
			return(false);
		}
		//Debemos obtener el idTienda del Pedido que vamos a finalizar
		Tienda tiendaPedido = PedidoDAO.obtenerTiendaPedido(idpedido);
		
		//Llamado Inserci�n Pixel
		ArrayList <DetallePedidoPixel> EnvioPixel = PedidoDAO.InsertarPedidoPixel(idpedido);
		
		//Main.main(args, EnvioPixel);
		Main principal = new Main();
		Cliente cliente = ClienteDAO.obtenerClienteporID(idcliente);
		boolean indicadorAct = false;
		if(insertado == 0)
		{
			indicadorAct = true;
		}
		
		//Si memcode = 0 es porque hay que crear el cliente
		//Si memcode <> 0 y indicador igual a true hay que actualizar
		//Si memcode <> 0 y indicador igual a false hay que actualizar
		int memcode = cliente.getMemcode();
		principal.main(EnvioPixel, tiendaPedido.getDsnTienda(),cliente.getMemcode(),cliente, indicadorAct);
		if (memcode == 0)
		{
			ClienteDAO.actualizarClienteMemcode(cliente.getIdcliente(), cliente.getMemcode());
		}
		return(true);
	}
	
	public static ArrayList<DetallePedidoPixel> InsertarPedidoPixel(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		logger.info("Se inicia la homologaci�n para base de datos Pixel, para el pedido " + idpedido);
		//Tener en cuenta que tenemos en homologaci�n el producto 10000 interno que har� el simil al producto de mensaje en Pixel
		ArrayList <DetallePedido> pedidoPixel = PedidoDAO.ConsultarDetallePedidoSinAdiciones(idpedido);
		ArrayList <DetallePedidoPixel> pedidoDefinitivoPixel = new ArrayList();
		double cantidadPixel;
		int idproductoext;
		int idproductomaestroext;
		int idproductoextsep;
		int idSaborTipoLiquido;
		//extraemos el c�digo producto pixel de la gaseosa para las que van dentro de los combos
		int idproductogasext;
		ArrayList<DetallePedidoAdicion> adicionDetallePedido = new ArrayList();
		ArrayList<ModificadorDetallePedido> modificadoresDetallePedido = new ArrayList();
		for (DetallePedido cadaDetallePedido: pedidoPixel)
		{
			adicionDetallePedido = PedidoDAO.ObtenerAdicionDetallePedido(cadaDetallePedido.getIddetallepedido());
			modificadoresDetallePedido = PedidoDAO.ObtenerModificadorDetallePedido(cadaDetallePedido.getIddetallepedido());
			//Aqui tendremos la l�gica para generar un array list
			//Definimos la cantidad del item que se va a pasar al otro sistema
			int cantidad = (int) cadaDetallePedido.getCantidad();
			// si no se tiene especialidad ni en uno ni en dos no ser�a un producto no pizza
			if(cadaDetallePedido.getIdespecialidad1() == 0 && cadaDetallePedido.getIdespecialidad2() == 0)
			{
				cantidadPixel = cadaDetallePedido.getCantidad();
				idSaborTipoLiquido = cadaDetallePedido.getIdsabortipoliquido();
				idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaDetallePedido.getIdproducto(), 0);
				pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cantidadPixel));
				logger.debug(idproductoext + " , " + cantidadPixel);
				for(DetallePedidoAdicion cadaAdicion: adicionDetallePedido)
				{
					if(cadaAdicion.getCantidad1() > 0)
					{
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad1()));
						logger.debug(idproductoext + " , " + cadaAdicion.getCantidad1());
					}
					else if(cadaAdicion.getCantidad2() > 0)
					{
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad2()));
						logger.debug(idproductoext + " , " + cadaAdicion.getCantidad2());
					}
					
				}
				//Recorremos los modificadores
				
				for(ModificadorDetallePedido cadaModificador: modificadoresDetallePedido)
				{
					if(cadaModificador.getIdproductoespecialidad1()>0)
					{
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad1(), 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()));
						logger.debug(idproductoext + " , " + cadaModificador.getCantidad());
					}
					
					if(cadaModificador.getIdproductoespecialidad2()>0)
					{
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad2(), 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()));
						logger.debug(idproductoext + " , " + cadaModificador.getCantidad());
					}
					
				}
				
				//Debemos revisar si el producto lleva gaseosa, en cuyo caso en el detalle del pedido debe ir un valor diferente a cero
				if (idSaborTipoLiquido > 0)
				{
					idproductoext = PedidoDAO.RetornarIdprodGaseosaPromo(idSaborTipoLiquido);
					pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cantidadPixel));
					logger.debug(idproductoext + " , " + cantidadPixel);
					
				}
				//Poner separador 0,0
				pedidoDefinitivoPixel.add(new DetallePedidoPixel(0, 0));
				logger.debug(0 + " , " + 0);
				
			}
			else
			{
				//Requerimos de un ciclo for para dividir la pizza si es el caso en varias
				for(int i = 1; i <= cantidad; i++)
				{
					if((cadaDetallePedido.getIdespecialidad1() > 0)&&(cadaDetallePedido.getIdespecialidad2() == 0))
					{
						//En las pizzas y con base en la pizza que se est� facturando, al principio debo de tener un master item
						//Master ITEM Se har� la homologaci�n entre el c�digo producto y la excepci�n de precio
						idproductomaestroext = PedidoDAO.RetornarIdproductoMaestroExterno(cadaDetallePedido.getIdproducto(), cadaDetallePedido.getIdexcepcion());
						int idproductopizza = 0;
						cantidadPixel = 0.5;
						idSaborTipoLiquido = cadaDetallePedido.getIdsabortipoliquido();
						//Adicionamos el producto maestro con cantidad 1
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductomaestroext, 1));
						logger.debug(idproductomaestroext + " , " + cantidadPixel);
						
						idproductoextsep = PedidoDAO.RetornarIdproductoExterno(10000, 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						idproductopizza = PedidoDAO.RetornarIdproductoExterno(cadaDetallePedido.getIdproducto(), cadaDetallePedido.getIdespecialidad1());
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductopizza, cantidadPixel));
						logger.debug(idproductopizza + " , " + cantidadPixel);
						
						//Aqui se ingresan la adiciones y los modificadores
						for(DetallePedidoAdicion cadaAdicion: adicionDetallePedido)
						{
							if(cadaAdicion.getIdespecialidad1() > 0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad1()));
								logger.debug(idproductoext + " , " + cadaAdicion.getCantidad1());
							}
						}
						
						//Recorremos los modificadores
						
						for(ModificadorDetallePedido cadaModificador: modificadoresDetallePedido)
						{
							if(cadaModificador.getIdproductoespecialidad1()>0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad1(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()/2));
								logger.debug(idproductoext + " , " + cadaModificador.getCantidad()/2);
							}
							
						}
						
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductopizza, cantidadPixel));
						logger.debug(idproductopizza + " , " + cantidadPixel);
						
						for(DetallePedidoAdicion cadaAdicion: adicionDetallePedido)
						{
							if(cadaAdicion.getIdespecialidad2() > 0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad2()));
								logger.debug(idproductoext + " , " + cadaAdicion.getCantidad2());
							}
						}
						
						for(ModificadorDetallePedido cadaModificador: modificadoresDetallePedido)
						{
							if(cadaModificador.getIdproductoespecialidad1()>0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad1(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()/2));
								logger.debug(idproductoext + " , " + cadaModificador.getCantidad()/2);
							}
							
						}
						
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						//Debemos revisar si el producto lleva gaseosa, en cuyo caso en el detalle del pedido debe ir un valor diferente a cero
						if (idSaborTipoLiquido > 0)
						{
							idproductoext = PedidoDAO.RetornarIdprodGaseosaPromo(idSaborTipoLiquido);
							pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, 1));
							logger.debug(idproductoext + " , " + cantidadPixel);
							
						}
						//Poner separador 0,0
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(0, 0));
						logger.debug(0 + " , " + 0);
						
					}else
					{
						//En las pizzas y con base en la pizza que se est� facturando, al principio debo de tener un master item
						//Master ITEM Se har� la homologaci�n entre el c�digo producto y la excepci�n de precio
						idproductomaestroext = PedidoDAO.RetornarIdproductoMaestroExterno(cadaDetallePedido.getIdproducto(), cadaDetallePedido.getIdexcepcion());
						cantidadPixel = 0.5;
						idSaborTipoLiquido = cadaDetallePedido.getIdsabortipoliquido();
						//Adicionamos el producto maestro con cantidad 1
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductomaestroext, 1));
						logger.debug(idproductomaestroext + " , " + cantidadPixel);
						
						idproductoextsep = PedidoDAO.RetornarIdproductoExterno(10000, 0);
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaDetallePedido.getIdproducto(), cadaDetallePedido.getIdespecialidad1());
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cantidadPixel));
						logger.debug(idproductoext + " , " + cantidadPixel);
						
						for(DetallePedidoAdicion cadaAdicion: adicionDetallePedido)
						{
							if(cadaAdicion.getIdespecialidad1() > 0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad1()));
								logger.debug(idproductoext + " , " + cadaAdicion.getCantidad1());
							}
						}
						
						for(ModificadorDetallePedido cadaModificador: modificadoresDetallePedido)
						{
							if(cadaModificador.getIdproductoespecialidad1()>0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad1(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()/2));
								logger.debug(idproductoext + " , " + cadaModificador.getCantidad()/2);
							}
							
						}
						
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaDetallePedido.getIdproducto(), cadaDetallePedido.getIdespecialidad2());
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cantidadPixel));
						logger.debug(idproductoext + " , " + cantidadPixel);
						
						for(DetallePedidoAdicion cadaAdicion: adicionDetallePedido)
						{
							if(cadaAdicion.getIdespecialidad2() > 0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaAdicion.getIdproducto(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaAdicion.getCantidad2()));
								logger.debug(idproductoext + " , " + cadaAdicion.getCantidad2());
							}
						}
						
						for(ModificadorDetallePedido cadaModificador: modificadoresDetallePedido)
						{
							if(cadaModificador.getIdproductoespecialidad2()>0)
							{
								idproductoext = PedidoDAO.RetornarIdproductoExterno(cadaModificador.getIdproductoespecialidad2(), 0);
								pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, cadaModificador.getCantidad()/2));
								logger.debug(idproductoext + " , " + cadaModificador.getCantidad()/2);
							}
							
						}
						
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoextsep, cantidadPixel));
						logger.debug(idproductoextsep + " , " + cantidadPixel);
						
						//Debemos revisar si el producto lleva gaseosa, en cuyo caso en el detalle del pedido debe ir un valor diferente a cero
						if (idSaborTipoLiquido > 0)
						{
							idproductoext = PedidoDAO.RetornarIdprodGaseosaPromo(idSaborTipoLiquido);
							pedidoDefinitivoPixel.add(new DetallePedidoPixel(idproductoext, 1));
							logger.debug(idproductoext + " , " + cantidadPixel);
							
						}
						//Poner separador 0,0
						pedidoDefinitivoPixel.add(new DetallePedidoPixel(0, 0));
						logger.debug(0 + " , " + 0);
						
					}

				}
			}	
		}
		return(pedidoDefinitivoPixel);
	}
	
	public static int RetornarIdproductoExterno(int idproductoint, int idespecialidadint)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoExt = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idproductoext from homologacion_producto where idproductoint = " + idproductoint + " and  idespecialidadint = " + idespecialidadint ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idProductoExt = rs.getInt("idproductoext");
				break;
			}
	        rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(-1);
		}
		return(idProductoExt);
	}
	
	
	public static int RetornarIdproductoMaestroExterno(int idproductoint, int idexcepcion)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoExt = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idproductoext from homologacion_producto where idproductoint = " + idproductoint + " and  idexcepcion = " + idexcepcion ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idProductoExt = rs.getInt("idproductoext");
				break;
			}
	        rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(-1);
		}
		return(idProductoExt);
	}
	
	public static int RetornarIdprodGaseosaPromo(int idsabortipoliquidoint)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoExt = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idproductoext from homologacion_producto where idsabortipoliquidoint = " + idsabortipoliquidoint  ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idProductoExt = rs.getInt("idproductoext");
				break;
			}
	        rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(-1);
		}
		return(idProductoExt);
	}
	
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
			
		}
			catch (Exception e){
				logger.error(e.toString());
		}
		
		return(tiposLiquido);
	}
	
	public static ArrayList<Pedido> ConsultaIntegradaPedidos(String fechainicial, String fechafinal, String tienda, int numeropedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <Pedido> consultaPedidos = new ArrayList();
		int idtienda = 0;
		String consulta = "";
		
		if((fechainicial.length()>0) && (fechafinal.length()>0) && (tienda.length()>0) && (numeropedido != 0))
		{
			idtienda = TiendaDAO.obteneridTienda(tienda);
			consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, c.nombre nombrecliente, d.descripcion, a.fechapedido, c.idcliente from pedido a, tienda b, cliente c, estado_pedido d where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and DATE_FORMAT(a.fechapedido, '%d/%m/%Y') >=  '" + fechainicial +"' and DATE_FORMAT(a.fechapedido, '%d/%m/%Y') <= '"+ fechafinal +"' and a.idtienda =" + idtienda + " and a.idpedido = " + numeropedido;
		}else if((fechainicial.length()>0) && (fechafinal.length()>0) && (tienda.length()>0))
		{
			idtienda = TiendaDAO.obteneridTienda(tienda);
			consulta = "select a.idpedido, b.nombre, a.total_bruto, a.impuesto, a.total_neto, c.nombre nombrecliente, d.descripcion, a.fechapedido, c.idcliente from pedido a, tienda b, cliente c, estado_pedido d where a.idtienda = b.idtienda and a.idcliente = c.idcliente and a.idestadopedido = d.idestadopedido and DATE_FORMAT(a.fechapedido, '%d/%m/%Y')>=  '" + fechainicial +"' and DATE_FORMAT(a.fechapedido, '%d/%m/%Y') <= '"+ fechafinal +"' and a.idtienda =" + idtienda ;
			
			
			
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
				Pedido cadaPedido = new Pedido(idpedido,  nombreTienda,totalBruto, impuesto, totalNeto,
						estadoPedido, fechaPedido, nombreCliente, idcliente);
				consultaPedidos.add(cadaPedido);
			}
		}catch(Exception e){
			logger.error(e.toString());
			
			
		}
		return(consultaPedidos);
	}
	
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
			ResultSet rs = stm.executeQuery(consulta);
			String delete = "";
			while (rs.next())
			{
				//debemos borrar tambien el detalle de la adici�n
				int valorEliminar = Integer.parseInt( rs.getString("iddetallepedidoadicion"));
				delete = "delete from adicion_detalle_pedido  where iddetallepedidoadicion = " + valorEliminar;
				logger.info(delete);
				stm1.executeUpdate(delete);
				//borramos el detalle pedido
				delete = "delete from detalle_pedido  where iddetalle_pedido = " + valorEliminar;
				logger.info(delete);
				stm1.executeUpdate(delete);
				idDetallePedidosBorrados.add(new Integer(valorEliminar));
			}
			//borrado en detalle de adiciones en caso de que sea una adicion
			delete = "delete from adicion_detalle_pedido  where iddetallepedidoadicion = " + iddetallepedido; 
			logger.info(delete);
			stm1.executeUpdate(delete);
			//
			delete = "delete from detalle_pedido  where iddetalle_pedido = " + iddetallepedido; 
			logger.info(delete);
			idDetallePedidosBorrados.add(new Integer(iddetallepedido));
			stm1.executeUpdate(delete);
			stm.close();
			stm1.close();
			con1.close();
			//Debemos de ejecutar una reconstrucci�n del campo adici�n del ippedido
			
		}
		catch (Exception e){
			logger.error(e.toString());
			
			
		}
		return(idDetallePedidosBorrados);
		
	}
	
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
		}catch(Exception e)
		{
			logger.error(e.toString());
			
		}
		return(consultaAdiciones);
	}
	
	public static ArrayList<ModificadorDetallePedido> ObtenerModificadorDetallePedido (int iddetallepedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<ModificadorDetallePedido> consultaModificadores = new ArrayList();
		String consulta = "select a.iddetallepedidopadre,  a.idproductoespecialidad1, a.idproductoespecialidad2, "
				+ "a.cantidad from modificador_detalle_pedido a where a.iddetallepedidopadre =" + iddetallepedido; 
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
			double cantidad;
			while(rs.next())
			{
				iddetallepedidopadre = Integer.parseInt(rs.getString("iddetallepedidopadre"));
				idproductoespecialidad1 = Integer.parseInt(rs.getString("idproductoespecialidad1"));
				idproductoespecialidad2 = Integer.parseInt(rs.getString("idproductoespecialidad2"));
				cantidad = Double.parseDouble(rs.getString("cantidad"));
				ModificadorDetallePedido detModificador = new ModificadorDetallePedido(0,iddetallepedidopadre,idproductoespecialidad1, idproductoespecialidad2, cantidad);
				consultaModificadores.add(detModificador);
			}
		}catch(Exception e)
		{
			logger.error(e.toString());
			
		}
		return(consultaModificadores);
	}
	
	public static ArrayList<DetallePedido> ConsultarDetallePedido(int numeropedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <DetallePedido> consultaDetallePedidos = new ArrayList();
		String consulta = "select a.iddetalle_pedido, a.idproducto, b.nombre, a.cantidad, a.idespecialidad1, a.idespecialidad2, c.nombre especialidad1, d.nombre especialidad2, "
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
				DetallePedido cadaDetallePedido = new DetallePedido(iddetallepedido, nombreproducto, idproducto, cantidad,especialidad1, idespecialidad1, especialidad2, idespecialidad2,valorunitario, valortotal,adicion,observacion,liquido, excepcion, numeropedido, idexcepcion, idsabortipoliquido);
				consultaDetallePedidos.add(cadaDetallePedido);
			}
		}catch(Exception e){
			logger.error(e.toString());
			
			
		}
		return(consultaDetallePedidos);
	}

	
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
				DetallePedido cadaDetallePedido = new DetallePedido(iddetallepedido, nombreproducto, idproducto, cantidad,especialidad1, idespecialidad1, especialidad2, idespecialidad2,valorunitario, valortotal,adicion,observacion,liquido, excepcion, numeropedido, idexcepcion, idsabortipoliquido);
				consultaDetallePedidos.add(cadaDetallePedido);
			}
		}catch(Exception e){
			logger.error(e.toString());
			
			
		}
		return(consultaDetallePedidos);
	}
	
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
		}catch (Exception e){
			logger.error(e.toString());
			
		}
		return(tienda);
		
	}
	
}
