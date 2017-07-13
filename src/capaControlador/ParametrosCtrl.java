package capaControlador;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import capaDAO.EspecialidadDAO;
import capaDAO.EstadoPedidoDAO;
import capaDAO.ExcepcionPrecioDAO;
import capaDAO.FormaPagoDAO;
import capaDAO.MunicipioDAO;
import capaDAO.PedidoDAO;
import capaDAO.ProductoDAO;
import capaDAO.SaborTipoLiquidoDAO;
import capaDAO.TiendaDAO;
import capaDAO.TipoLiquidoDAO;
import capaModelo.Especialidad;
import capaModelo.EstadoPedido;
import capaModelo.ExcepcionPrecio;
import capaModelo.TipoLiquido;
import capaModelo.Producto;
import capaModelo.SaborLiquido;
import capaModelo.Tienda;
import capaModelo.Municipio;
import capaModelo.FormaPago;

public class ParametrosCtrl {
	
	//FORMAPAGO
	
	public String retornarFormasPago(){
		JSONArray listJSON = new JSONArray();
		ArrayList<FormaPago> formasPago = FormaPagoDAO.obtenerFormasPago();
		for (FormaPago forma : formasPago) 
		{
			JSONObject cadaFormaPagoJSON = new JSONObject();
			cadaFormaPagoJSON.put("idformapago", forma.getIdformapago());
			cadaFormaPagoJSON.put("nombre", forma.getNombre());
			cadaFormaPagoJSON.put("tipoformapago", forma.getTipoforma());
			listJSON.add(cadaFormaPagoJSON);
		}
		return listJSON.toJSONString();
	}
	
	//ESPECIALIDAD
	
	public String insertarEspecialidad(String nombre, String abreviatura)
	{
		JSONArray listJSON = new JSONArray();
		Especialidad Espe = new Especialidad(nombre, abreviatura);
		int idEspIns = EspecialidadDAO.insertarEspecialidad(Espe);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idespecialidad", idEspIns);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String retornarEspecialidad(int idespecialidad)
	{
		JSONArray listJSON = new JSONArray();
		Especialidad Espe = EspecialidadDAO.retornarEspecialidad(idespecialidad);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idespecialidad", Espe.getIdespecialidad());
		ResultadoJSON.put("nombre", Espe.getNombre());
		ResultadoJSON.put("abreviatura", Espe.getAbreviatura());
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String eliminarEspecialidad(int idespecialidad)
	{
		JSONArray listJSON = new JSONArray();
		EspecialidadDAO.eliminarEspecialidad(idespecialidad);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String editarEspecialidad(int idespecialidad , String nombre, String abreviatura)
	{
		JSONArray listJSON = new JSONArray();
		Especialidad Espe = new Especialidad(idespecialidad, nombre, abreviatura);
		String resultado = EspecialidadDAO.editarEspecialidad(Espe);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", resultado);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	
	//ESTADO PEDIDO
	
	public String insertarEstadoPedido(String descripcion)
	{
		JSONArray listJSON = new JSONArray();
		EstadoPedido Est = new EstadoPedido(descripcion);
		int idEstIns = EstadoPedidoDAO.insertarEstadoPedido(Est);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idestadopedido", idEstIns);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String retornarEstadoPedido(int idestadopedido)
	{
		JSONArray listJSON = new JSONArray();
		EstadoPedido Est = EstadoPedidoDAO.retornarEstadoPedido(idestadopedido);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idestadopedido", Est.getIdestadopedido());
		ResultadoJSON.put("descripcion", Est.getDescripcion());
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	
	
	public String retornarEstadosPedido(){
		JSONArray listJSON = new JSONArray();
		ArrayList<EstadoPedido> estados = EstadoPedidoDAO.obtenerEstadosPedido();
		for (EstadoPedido est : estados) 
		{
			JSONObject cadaEstadoJSON = new JSONObject();
			cadaEstadoJSON.put("idestadopedido", est.getIdestadopedido());
			cadaEstadoJSON.put("descripcion", est.getDescripcion());
			listJSON.add(cadaEstadoJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String eliminarEstadoPedido(int idestadopedido)
	{
		JSONArray listJSON = new JSONArray();
		EstadoPedidoDAO.eliminarEstadoPedido(idestadopedido);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String editarEstadoPedido(int idestadopedido , String descripcion)
	{
		JSONArray listJSON = new JSONArray();
		EstadoPedido Est = new EstadoPedido(idestadopedido,descripcion);
		String resultado = EstadoPedidoDAO.editarEstadoPedido(Est);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", resultado);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	//EXCEPCION PRECIO
	
		public String insertarExcepcionPrecio(int idproducto, double precio, String descripcion, String incluye_liquido, int  idtipoliquido)
		{
			JSONArray listJSON = new JSONArray();
			ExcepcionPrecio Esc = new ExcepcionPrecio(idproducto, precio, descripcion, incluye_liquido, idtipoliquido);
			int idEscIns = ExcepcionPrecioDAO.insertarExcepcionPrecio(Esc);
			JSONObject ResultadoJSON = new JSONObject();
			ResultadoJSON.put("idexcepcion", idEscIns);
			listJSON.add(ResultadoJSON);
			return listJSON.toJSONString();
		}
		
		public String retornarExcepcionPrecio(int idexcepcion)
		{
			JSONArray listJSON = new JSONArray();
			ExcepcionPrecio Esc = ExcepcionPrecioDAO.retornarExcepcionPrecio(idexcepcion);
			JSONObject ResultadoJSON = new JSONObject();
			ResultadoJSON.put("idexcepcion", Esc.getIdExcepcion());
			ResultadoJSON.put("idproducto", Esc.getIdProducto());
			ResultadoJSON.put("precio", Esc.getPrecio());
			ResultadoJSON.put("descripcion", Esc.getDescripcion());
			ResultadoJSON.put("incluye_liquido", Esc.getIncluyeliquido());
			ResultadoJSON.put("idtipoliquido", Esc.getIdtipoliquido());
			listJSON.add(ResultadoJSON);
			return listJSON.toJSONString();
		}
		
		
		
		public String retornarExcecionesPrecio(){
			JSONArray listJSON = new JSONArray();
			ArrayList<ExcepcionPrecio> excepciones = ExcepcionPrecioDAO.obtenerExcepcionesPrecio();
			for (ExcepcionPrecio Esc : excepciones) 
			{
				JSONObject cadaExcepcionJSON = new JSONObject();
				cadaExcepcionJSON.put("idexcepcion", Esc.getIdExcepcion());
				cadaExcepcionJSON.put("idproducto", Esc.getIdProducto());
				cadaExcepcionJSON.put("precio", Esc.getPrecio());
				cadaExcepcionJSON.put("descripcion", Esc.getDescripcion());
				cadaExcepcionJSON.put("incluye_liquido", Esc.getIncluyeliquido());
				cadaExcepcionJSON.put("idtipoliquido", Esc.getIdtipoliquido());
				listJSON.add(cadaExcepcionJSON);
			}
			return listJSON.toJSONString();
		}
		
		public String ObtenerTiposLiquido(){
			JSONArray listJSON = new JSONArray();
			ArrayList<TipoLiquido> tipliquidos = PedidoDAO.ObtenerTiposLiquido();
			for (TipoLiquido tipliq : tipliquidos) 
			{
				JSONObject cadaTipoLiquidoJSON = new JSONObject();
				cadaTipoLiquidoJSON.put("idtipo_liquido", tipliq.getIdtipo_liquido());
				cadaTipoLiquidoJSON.put("nombre", tipliq.getNombre());
				cadaTipoLiquidoJSON.put("capacidad", tipliq.getCapacidad());
				listJSON.add(cadaTipoLiquidoJSON);
			}
			return listJSON.toJSONString();
		}
		
		public String retornarExcecionesPrecioGrid(){
			JSONArray listJSON = new JSONArray();
			ArrayList<ExcepcionPrecio> excepciones = ExcepcionPrecioDAO.obtenerExcepcionesPrecioGrid();
			for (ExcepcionPrecio Esc : excepciones) 
			{
				JSONObject cadaExcepcionJSON = new JSONObject();
				cadaExcepcionJSON.put("idexcepcion", Esc.getIdExcepcion());
				cadaExcepcionJSON.put("idproducto", Esc.getIdProducto());
				cadaExcepcionJSON.put("nombreproducto", Esc.getNombreProducto());
				cadaExcepcionJSON.put("precio", Esc.getPrecio());
				cadaExcepcionJSON.put("descripcion", Esc.getDescripcion());
				cadaExcepcionJSON.put("incluye_liquido", Esc.getIncluyeliquido());
				cadaExcepcionJSON.put("idtipoliquido", Esc.getIdtipoliquido());
				cadaExcepcionJSON.put("nombreliquido", Esc.getNombreLiquido());
				listJSON.add(cadaExcepcionJSON);
			}
			return listJSON.toJSONString();
		}
		
		public String eliminarExcepcionPrecio(int idexcepcion)
		{
			JSONArray listJSON = new JSONArray();
			ExcepcionPrecioDAO.eliminarExcepcionPrecio(idexcepcion);
			JSONObject ResultadoJSON = new JSONObject();
			ResultadoJSON.put("resultado", "exitoso");
			listJSON.add(ResultadoJSON);
			return listJSON.toJSONString();
		}
		
		public String editarExcepcionPrecio(int idexcepcion, int idproducto, double precio, String descripcion, String incluye_liquido, int idtipoliquido)
		{
			JSONArray listJSON = new JSONArray();
			ExcepcionPrecio Esc = new ExcepcionPrecio(idexcepcion, idproducto, precio, descripcion, incluye_liquido, idtipoliquido);
			String resultado = ExcepcionPrecioDAO.editarExcepcionPrecio(Esc);
			JSONObject ResultadoJSON = new JSONObject();
			ResultadoJSON.put("resultado", resultado);
			listJSON.add(ResultadoJSON);
			System.out.println(listJSON.toJSONString());
			return listJSON.toJSONString();
		}
		
		//PRODUCTO
		
				
			public String insertaProducto(int idreceta,String nombre, String descripcion, float impuesto, String tipo, double preciogeneral, String incluye_liquido, int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				Producto Pro = new Producto(0,idreceta,nombre, descripcion, impuesto, tipo, 0, preciogeneral,  incluye_liquido, idtipo_liquido);
				int idProIns = ProductoDAO.insertarProducto(Pro);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idproducto", idProIns);
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String retornarProducto(int idproducto)
			{
				JSONArray listJSON = new JSONArray();
				Producto Pro = ProductoDAO.retornarProducto(idproducto);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idproducto", Pro.getIdProducto());
				ResultadoJSON.put("idreceta", Pro.getIdReceta());
				ResultadoJSON.put("nombre", Pro.getNombre());
				ResultadoJSON.put("descripcion", Pro.getDescripcion());
				ResultadoJSON.put("impuesto", Pro.getImpuesto());
				ResultadoJSON.put("tipo", Pro.getTipo());
				ResultadoJSON.put("preciogeneral", Pro.getPreciogeneral());
				ResultadoJSON.put("incluye_liquido", Pro.getIncluye_liquido());
				ResultadoJSON.put("idtipo_liquido", Pro.getIdtipo_liquido());
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			
			
			public String retornarProductos(){
				JSONArray listJSON = new JSONArray();
				ArrayList<Producto> productos = ProductoDAO.obtenerProductos();
				for (Producto Pro : productos) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idproducto", Pro.getIdProducto());
					ResultadoJSON.put("idreceta", Pro.getIdReceta());
					ResultadoJSON.put("nombre", Pro.getNombre());
					ResultadoJSON.put("descripcion", Pro.getDescripcion());
					ResultadoJSON.put("impuesto", Pro.getImpuesto());
					ResultadoJSON.put("tipo", Pro.getTipo());
					ResultadoJSON.put("preciogeneral", Pro.getPreciogeneral());
					ResultadoJSON.put("incluye_liquido", Pro.getIncluye_liquido());
					ResultadoJSON.put("idtipo_liquido", Pro.getIdtipo_liquido());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
						
			public String retornarProductosGrid(){
				JSONArray listJSON = new JSONArray();
				ArrayList<Producto> productos = ProductoDAO.obtenerProductosGrid();
				for (Producto Pro : productos) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idproducto", Pro.getIdProducto());
					ResultadoJSON.put("idreceta", Pro.getIdReceta());
					ResultadoJSON.put("nombrereceta", Pro.getNombrereceta());
					ResultadoJSON.put("nombre", Pro.getNombre());
					ResultadoJSON.put("descripcion", Pro.getDescripcion());
					ResultadoJSON.put("impuesto", Pro.getImpuesto());
					ResultadoJSON.put("tipo", Pro.getTipo());
					ResultadoJSON.put("preciogeneral", Pro.getPreciogeneral());
					ResultadoJSON.put("incluye_liquido", Pro.getIncluye_liquido());
					ResultadoJSON.put("idtipo_liquido", Pro.getIdtipo_liquido());
					ResultadoJSON.put("nombreliquido", Pro.getNombreliquido());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
			public String eliminarProducto(int idproducto)
			{
				JSONArray listJSON = new JSONArray();
				ProductoDAO.eliminarProducto(idproducto);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", "exitoso");
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String editarProducto(int idproducto,int idreceta,String nombre, String descripcion, float impuesto, String tipo, double preciogeneral, String incluye_liquido, int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				Producto Pro = new Producto(idproducto,idreceta,nombre,descripcion, impuesto, tipo,0, preciogeneral, incluye_liquido, idtipo_liquido);
				String resultado =ProductoDAO.editarProducto(Pro);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", resultado);
				listJSON.add(ResultadoJSON);
				System.out.println(listJSON.toJSONString());
				return listJSON.toJSONString();
			}
			
			//SABOR LIQUIDO
			
			
			public String insertarSaborLiquido(String descripcion, int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				SaborLiquido Sab = new SaborLiquido(0,descripcion,idtipo_liquido);
				int idSabIns = SaborTipoLiquidoDAO.insertarSaborTipoLiquido(Sab);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idsabortipoliquido", idSabIns);
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String retornarSaborLiquido(int idsabortipoliquido)
			{
				JSONArray listJSON = new JSONArray();
				SaborLiquido Sab = SaborTipoLiquidoDAO.retornarSaborTipoLiquido(idsabortipoliquido);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idsabortipoliquido", Sab.getIdSaborTipoLiquido());
				ResultadoJSON.put("descripcion", Sab.getDescripcionSabor());
				ResultadoJSON.put("idtipoliquido", Sab.getIdLiquido());
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			
			
			public String retornarSaborLiquidos(){
				JSONArray listJSON = new JSONArray();
				ArrayList<SaborLiquido> sabores = SaborTipoLiquidoDAO.obtenerSaborLiquidos();
				for (SaborLiquido Sab : sabores) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idsabortipoliquido", Sab.getIdSaborTipoLiquido());
					ResultadoJSON.put("descripcion", Sab.getDescripcionSabor());
					ResultadoJSON.put("idtipoliquido", Sab.getIdLiquido());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
						
			public String retornarSaborLiquidosGrid(){
				JSONArray listJSON = new JSONArray();
				ArrayList<SaborLiquido> sabores = SaborTipoLiquidoDAO.obtenerSaborLiquidosGrid();
				for (SaborLiquido Sab : sabores) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idsabortipoliquido", Sab.getIdSaborTipoLiquido());
					ResultadoJSON.put("descripcion", Sab.getDescripcionSabor());
					ResultadoJSON.put("idtipoliquido", Sab.getIdLiquido());
					ResultadoJSON.put("nombreliquido", Sab.getDescripcionLiquido());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
			public String eliminarSaborLiquido(int idsaborxtipoliquido)
			{
				JSONArray listJSON = new JSONArray();
				SaborTipoLiquidoDAO.eliminarSaborTipoLiquido(idsaborxtipoliquido);;
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", "exitoso");
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String editarSaborLiquido(int idsaborxtipoliquido, String descripcion, int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				SaborLiquido Sab = new SaborLiquido(idsaborxtipoliquido,descripcion,idtipo_liquido);
				String resultado =SaborTipoLiquidoDAO.editarSaborTipoLiquido(Sab);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", resultado);
				listJSON.add(ResultadoJSON);
				System.out.println(listJSON.toJSONString());
				return listJSON.toJSONString();
			}	
			
//TIENDA
			
			
			public String insertarTienda(String nombre, String dsn)
			{
				JSONArray listJSON = new JSONArray();
				Tienda Tie = new Tienda(0,nombre,dsn);
				int idTieIns = TiendaDAO.insertarTienda(Tie);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idtienda", idTieIns);
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String retornarTienda(int idtienda)
			{
				JSONArray listJSON = new JSONArray();
				Tienda Tie = TiendaDAO.retornarTienda(idtienda);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idtienda", Tie.getIdTienda());
				ResultadoJSON.put("nombre", Tie.getNombreTienda());
				ResultadoJSON.put("dsn", Tie.getDsnTienda());
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			
			
			public String retornarTiendas(){
				JSONArray listJSON = new JSONArray();
				ArrayList<Tienda> tiendas = TiendaDAO.obtenerTiendas();
				for (Tienda Tie : tiendas) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idtienda", Tie.getIdTienda());
					ResultadoJSON.put("nombre", Tie.getNombreTienda());
					ResultadoJSON.put("dsn", Tie.getDsnTienda());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
						
			public String eliminarTienda(int idtienda)
			{
				JSONArray listJSON = new JSONArray();
				TiendaDAO.eliminarTienda(idtienda);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", "exitoso");
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String editarTienda(int idtienda, String nombre, String dsn)
			{
				JSONArray listJSON = new JSONArray();
				Tienda Tie = new Tienda(idtienda,nombre,dsn);
				String resultado =TiendaDAO.editarTienda(Tie);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", resultado);
				listJSON.add(ResultadoJSON);
				System.out.println(listJSON.toJSONString());
				return listJSON.toJSONString();
			}	
	
//TIPO LIQUIDO
			
			
			public String insertarTipoLiquido(String nombre, String capacidad)
			{
				JSONArray listJSON = new JSONArray();
				TipoLiquido Tipo = new TipoLiquido(0,nombre,capacidad);
				int idTipoIns = TipoLiquidoDAO.insertarTipoLiquido(Tipo);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idtipoliquido", idTipoIns);
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String retornarTipoLiquido(int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				TipoLiquido Tipo = TipoLiquidoDAO.retornarTipoProducto(idtipo_liquido);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("idtipoliquido", Tipo.getIdtipo_liquido());
				ResultadoJSON.put("nombre", Tipo.getNombre());
				ResultadoJSON.put("capacidad", Tipo.getCapacidad());
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			
			
			public String retornarTiposLiquidos(){
				JSONArray listJSON = new JSONArray();
				ArrayList<TipoLiquido> tipos = TipoLiquidoDAO.obtenerTiposLiquido();
				for (TipoLiquido Tipo : tipos) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idtipoliquido", Tipo.getIdtipo_liquido());
					ResultadoJSON.put("nombre", Tipo.getNombre());
					ResultadoJSON.put("capacidad", Tipo.getCapacidad());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
			
						
			public String eliminarTipodLiquido(int idtipo_liquido)
			{
				JSONArray listJSON = new JSONArray();
				TipoLiquidoDAO.eliminarTipoLiquido(idtipo_liquido);;
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", "exitoso");
				listJSON.add(ResultadoJSON);
				return listJSON.toJSONString();
			}
			
			public String editarTipoLiquido(int idtipo_liquido, String nombre, String capacidad)
			{
				JSONArray listJSON = new JSONArray();
				TipoLiquido Tipo = new TipoLiquido(idtipo_liquido,nombre,capacidad);
				String resultado =TipoLiquidoDAO.editarTipoLiquido(Tipo);
				JSONObject ResultadoJSON = new JSONObject();
				ResultadoJSON.put("resultado", resultado);
				listJSON.add(ResultadoJSON);
				System.out.println(listJSON.toJSONString());
				return listJSON.toJSONString();
			}
			
			//MUNICIPIOS
			
			public String retornarMunicipios(){
				JSONArray listJSON = new JSONArray();
				ArrayList<Municipio> municipios = MunicipioDAO.obtenerMunicipios();
				for (Municipio municipio : municipios) 
				{
					JSONObject ResultadoJSON = new JSONObject();
					ResultadoJSON.put("idmunicipio", municipio.getIdmunicipio());
					ResultadoJSON.put("nombre",municipio.getNombre());
					listJSON.add(ResultadoJSON);
				}
				return listJSON.toJSONString();
			}
}
