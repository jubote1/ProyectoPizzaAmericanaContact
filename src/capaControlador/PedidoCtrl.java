package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.ClienteDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiendaDAO;
import capaDAO.ExcepcionPrecioDAO;
import capaModelo.Especialidad;
import capaModelo.Producto;
import capaModelo.ExcepcionPrecio;
import capaModelo.SaborLiquido;
import capaModelo.DetallePedido;
import capaModelo.Pedido;
import capaModelo.DetallePedidoAdicion;
import capaModelo.ModificadorDetallePedido;

public class PedidoCtrl {

	
	public String obtenerEspecialidades(){
		JSONArray listJSON = new JSONArray();
		ArrayList<Especialidad> especialidades = PedidoDAO.obtenerEspecialidad();
		for (Especialidad espe : especialidades) {
			JSONObject cadaViajeJSON = new JSONObject();
			cadaViajeJSON.put("idespecialidad", espe.getIdespecialidad());
			cadaViajeJSON.put("nombre", espe.getNombre());
			cadaViajeJSON.put("abreviatura", espe.getAbreviatura());
			listJSON.add(cadaViajeJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String obtenerTodosProductos(){
		JSONArray listJSON = new JSONArray();
		ArrayList<Producto> otrosProductos = PedidoDAO.obtenerTodosProductos();
		for (Producto produ : otrosProductos) {
			JSONObject cadaProductoJSON = new JSONObject();
			cadaProductoJSON.put("idproducto", produ.getIdProducto());
			cadaProductoJSON.put("idreceta", produ.getIdReceta());
			cadaProductoJSON.put("nombre", produ.getNombre());
			cadaProductoJSON.put("descripcion", produ.getDescripcion());
			cadaProductoJSON.put("impuesto", produ.getImpuesto());
			cadaProductoJSON.put("tipo", produ.getTipo());
			cadaProductoJSON.put("productoasociaadicion", produ.getProductoasociaadicion());
			cadaProductoJSON.put("preciogeneral", produ.getPreciogeneral());
			cadaProductoJSON.put("incluye_liquido", produ.getIncluye_liquido());
			cadaProductoJSON.put("idtipo_liquido", produ.getIdtipo_liquido());
			listJSON.add(cadaProductoJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String InsertarEncabezadoPedido(String tienda,int idcliente){
		int idtienda = TiendaDAO.obteneridTienda(tienda);
		JSONArray listJSON = new JSONArray();
		int resultado = 0;
		if (idcliente != 0)
		{
			resultado = PedidoDAO.InsertarEncabezadoPedido(idtienda, idcliente);
		}
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idpedido", resultado);
		Respuesta.put("idcliente", idcliente);
		Respuesta.put("idestadopedido", "1");
		Respuesta.put("descripcionestadopedido","En curso");
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String InsertarDetallePedido(int idproducto,int idpedido,double cantidad, String especialidad1, String especialidad2, double valorunitario, double valortotal, String adicion, String observacion, int idsabortipoliquido, int idexcepcion, String modespecialidad1, String modespecialidad2){
		JSONArray listJSON = new JSONArray();
		//int idespecialidad1 = PedidoDAO.obtenerIdEspecialidad(especialidad1);
		//int idespecialidad2 = PedidoDAO.obtenerIdEspecialidad(especialidad2);
		int idespecialidad1;
		int idespecialidad2;
		try
        {
			idespecialidad1 = Integer.parseInt(especialidad1);
        }catch (Exception e)
        {
        	idespecialidad1 = 0;
        }
		try
        {
			idespecialidad2 = Integer.parseInt(especialidad2);
        }catch (Exception e)
        {
        	idespecialidad2 = 0;
        }
		DetallePedido detPedido = new DetallePedido(idproducto,idpedido,cantidad,idespecialidad1,idespecialidad2,valorunitario,valortotal, adicion, observacion, idsabortipoliquido, idexcepcion, modespecialidad1, modespecialidad2);
		int iddetallepedido = PedidoDAO.InsertarDetallePedido(detPedido);
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("iddetallepedido", iddetallepedido);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String InsertarDetalleAdicion(int iddetallepedidopadre, int iddetallepedidoadicion, int idespecialidad1, int idespecialidad2, double cantidad1, double cantidad2){
		JSONArray listJSON = new JSONArray();
		DetallePedidoAdicion detPedidoAdicion = new DetallePedidoAdicion(iddetallepedidopadre, iddetallepedidoadicion, idespecialidad1, idespecialidad2, cantidad1,cantidad2);
		int idadicion = PedidoDAO.InsertarDetalleAdicion(detPedidoAdicion);
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idadicion", idadicion);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String InsertarModificadorDetallePedido(int iddetallepedidopadre, int idproductoespecialidad1, int idproductoespecialidad2, double cantidad){
		JSONArray listJSON = new JSONArray();
		ModificadorDetallePedido modDetPedido = new ModificadorDetallePedido(0,iddetallepedidopadre, idproductoespecialidad1, idproductoespecialidad2, cantidad);
		int idmodificador = PedidoDAO.InsertarModificadorDetallePedido(modDetPedido);
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idmodificador", idmodificador);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String obtenerTodosExcepciones(){
		JSONArray listJSON = new JSONArray();
		ArrayList<ExcepcionPrecio> excepcionesPrecio = ExcepcionPrecioDAO.obtenerExcepcionesPrecio();
		for (ExcepcionPrecio exc : excepcionesPrecio) {
			JSONObject cadaExcepcionJSON = new JSONObject();
			cadaExcepcionJSON.put("idexcepcion", exc.getIdExcepcion());
			cadaExcepcionJSON.put("idproducto", exc.getIdProducto());
			cadaExcepcionJSON.put("precio", exc.getPrecio());
			cadaExcepcionJSON.put("descripcion", exc.getDescripcion());
			listJSON.add(cadaExcepcionJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	
	public String obtenerAdicionProductos(){
		JSONArray listJSON = new JSONArray();
		ArrayList<Producto> adicionProductos = PedidoDAO.obtenerAdicionProductos();
		for (Producto produ : adicionProductos) {
			JSONObject cadaProductoJSON = new JSONObject();
			cadaProductoJSON.put("idproducto", produ.getIdProducto());
			cadaProductoJSON.put("idreceta", produ.getIdReceta());
			cadaProductoJSON.put("nombre", produ.getNombre());
			cadaProductoJSON.put("descripcion", produ.getDescripcion());
			cadaProductoJSON.put("impuesto", produ.getImpuesto());
			cadaProductoJSON.put("tipo", produ.getTipo());
			cadaProductoJSON.put("preciogeneral", produ.getPreciogeneral());
			listJSON.add(cadaProductoJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	
	public String ObtenerSaboresLiquidoProducto(int idProdu)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<SaborLiquido> saboresLiquidos = PedidoDAO.ObtenerSaboresLiquidoProducto(idProdu);
		for (SaborLiquido sabor: saboresLiquidos){
			JSONObject cadaSaborJSON = new JSONObject();
			cadaSaborJSON.put("idSaborTipoLiquido", sabor.getIdSaborTipoLiquido());
			cadaSaborJSON.put("descripcionSabor", sabor.getDescripcionSabor());
			cadaSaborJSON.put("idLiquido", sabor.getIdLiquido());
			cadaSaborJSON.put("descripcionLiquido", sabor.getDescripcionLiquido());
			cadaSaborJSON.put("idProducto", sabor.getIdProducto());
			cadaSaborJSON.put("nombreProducto", sabor.getNombreProducto());
			listJSON.add(cadaSaborJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String ObtenerSaboresLiquidoExcepcion(int idExcep)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<SaborLiquido> saboresLiquidos = PedidoDAO.ObtenerSaboresLiquidoExcepcion(idExcep);
		for (SaborLiquido sabor: saboresLiquidos){
			JSONObject cadaSaborJSON = new JSONObject();
			cadaSaborJSON.put("idSaborTipoLiquido", sabor.getIdSaborTipoLiquido());
			cadaSaborJSON.put("descripcionSabor", sabor.getDescripcionSabor());
			cadaSaborJSON.put("idLiquido", sabor.getIdLiquido());
			cadaSaborJSON.put("descripcionLiquido", sabor.getDescripcionLiquido());
			cadaSaborJSON.put("idExcepcion", sabor.getIdExcepcion());
			cadaSaborJSON.put("descripcionExcepcion", sabor.getDescripcionExcepcion());
			listJSON.add(cadaSaborJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String FinalizarPedido(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado)
	{
		boolean resultado = PedidoDAO.finalizarPedido(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado);
		String tiendaPixel = PedidoDAO.obtenerUrlTienda(idpedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("insertado", resultado);
		Respuesta.put("url", tiendaPixel);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String eliminarPedidoSinConfirmar(int idpedido)
	{
		boolean resultado = PedidoDAO.eliminarPedidoSinConfirmar(idpedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("respuesta", resultado);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	
	public String obtenerTotalPedido(int idpedido)
	{
		double valorTotal = PedidoDAO.obtenerTotalPedido(idpedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("valortotal", valorTotal);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String ConsultaIntegradaPedidos(String fechainicial, String fechafinal, String tienda, int numeropedido)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList <Pedido> consultaPedidos = PedidoDAO.ConsultaIntegradaPedidos(fechainicial, fechafinal, tienda, numeropedido);
		for (Pedido cadaPedido: consultaPedidos){
			JSONObject cadaPedidoJSON = new JSONObject();
			cadaPedidoJSON.put("idpedido", cadaPedido.getIdpedido());
			cadaPedidoJSON.put("tienda", cadaPedido.getNombretienda());
			cadaPedidoJSON.put("totalbruto", cadaPedido.getTotalbruto());
			cadaPedidoJSON.put("impuesto", cadaPedido.getImpuesto());
			cadaPedidoJSON.put("totalneto",cadaPedido.getTotal_neto());
			cadaPedidoJSON.put("idcliente", cadaPedido.getIdcliente());
			cadaPedidoJSON.put("cliente", cadaPedido.getNombrecliente());
			cadaPedidoJSON.put("estadopedido", cadaPedido.getEstadopedido());
			cadaPedidoJSON.put("fechapedido", cadaPedido.getFechapedido());
			listJSON.add(cadaPedidoJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String ConsultarDetallePedido(int numeropedido)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList <DetallePedido> consultaDetallePedido = PedidoDAO.ConsultarDetallePedido(numeropedido);
		for (DetallePedido cadaDetallePedido: consultaDetallePedido){
			JSONObject cadaDetallePedidoJSON = new JSONObject();
			cadaDetallePedidoJSON.put("iddetallepedido", cadaDetallePedido.getIddetallepedido());
			cadaDetallePedidoJSON.put("nombreproducto", cadaDetallePedido.getNombreproducto());
			cadaDetallePedidoJSON.put("cantidad", cadaDetallePedido.getCantidad());
			cadaDetallePedidoJSON.put("especialidad1", cadaDetallePedido.getNombreespecialidad1());
			cadaDetallePedidoJSON.put("especialidad2",cadaDetallePedido.getNombreespecialidad2());
			cadaDetallePedidoJSON.put("valorunitario", cadaDetallePedido.getValorunitario());
			cadaDetallePedidoJSON.put("valortotal", cadaDetallePedido.getValortotal());
			cadaDetallePedidoJSON.put("adicion", cadaDetallePedido.getAdicion());
			cadaDetallePedidoJSON.put("observacion", cadaDetallePedido.getObservacion());
			cadaDetallePedidoJSON.put("liquido", cadaDetallePedido.getLiquido());
			cadaDetallePedidoJSON.put("excepcion", cadaDetallePedido.getExcepcion());
			listJSON.add(cadaDetallePedidoJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String EliminarDetallePedido(int iddetallepedido)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<Integer> idPedidos = PedidoDAO.EliminarDetallePedido(iddetallepedido);
		for(Integer cadaInteger: idPedidos)
		{
			JSONObject cadaIdDetallePedidoJSON = new JSONObject();
			cadaIdDetallePedidoJSON.put("iddetallepedido", cadaInteger.intValue() );
			listJSON.add(cadaIdDetallePedidoJSON);
		}
		return listJSON.toJSONString();
	}
	
}
