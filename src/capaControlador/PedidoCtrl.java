package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.ClienteDAO;
import capaDAO.DireccionFueraZonaDAO;
import capaDAO.EspecialidadDAO;
import capaDAO.PedidoDAO;
import capaDAO.ProductoDAO;
import capaDAO.TiendaDAO;
import capaDAO.TmpPedidosPoligonoDAO;
import capaDAO.ExcepcionPrecioDAO;
import capaDAO.MarcacionPedidoDAO;
import capaModelo.Especialidad;
import capaModelo.Producto;
import capaModelo.ExcepcionPrecio;
import capaModelo.FormaPago;
import capaModelo.HomologaGaseosaIncluida;
import capaModelo.InsertarPedidoPixel;
import capaModelo.MarcacionPedido;
import capaModelo.SaborLiquido;
import capaModelo.Tienda;
import capaModelo.DetallePedido;
import capaModelo.Pedido;
import capaModelo.DetallePedidoAdicion;
import capaModelo.DetallePedidoPixel;
import capaModelo.DireccionFueraZona;
import capaModelo.ModificadorDetallePedido;
import capaModelo.NomenclaturaDireccion;
import capaModelo.ProductoIncluido;

public class PedidoCtrl {

	
	public String obtenerEspecialidades(int idExcepcion){
		JSONArray listJSON = new JSONArray();
		ArrayList<Especialidad> especialidades = PedidoDAO.obtenerEspecialidad(idExcepcion);
		for (Especialidad espe : especialidades) {
			JSONObject cadaViajeJSON = new JSONObject();
			cadaViajeJSON.put("idespecialidad", espe.getIdespecialidad());
			cadaViajeJSON.put("nombre", espe.getNombre());
			cadaViajeJSON.put("abreviatura", espe.getAbreviatura());
			listJSON.add(cadaViajeJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	
	public String obtenerNomenclaturaDireccion(){
		JSONArray listJSON = new JSONArray();
		ArrayList<NomenclaturaDireccion> nomenclaturas = PedidoDAO.obtenerNomenclaturaDireccion();
		for (NomenclaturaDireccion nomen : nomenclaturas) {
			JSONObject cadaNomenJSON = new JSONObject();
			cadaNomenJSON.put("idnomenclatura", nomen.getIdnomemclatura());
			cadaNomenJSON.put("nomenclatura", nomen.getNomenclatura());
			listJSON.add(cadaNomenJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String actualizarEstadoNumeroPedidoPixel(int idpedido, int numPedidoPixel, boolean creaCliente, int membercode, int idcliente)
	{
		JSONArray listJSON = new JSONArray();
		boolean resp = PedidoDAO.actualizarEstadoNumeroPedidoPixel(idpedido, numPedidoPixel);
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("resultado", resp);
		listJSON.add(Respuesta);
		if (creaCliente)
		{
			PedidoDAO.actualizarClienteMemcode(idcliente, membercode);
		}
		return(listJSON.toJSONString());
		
	}
	
	public String actualizarMemcode(int membercode, int idcliente)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		PedidoDAO.actualizarClienteMemcode(idcliente, membercode);
		Respuesta.put("resultado", "OK");
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
		
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
			cadaProductoJSON.put("manejacantidad", produ.getManejacantidad());
			listJSON.add(cadaProductoJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String GetProductosTienda(int idtienda){
		JSONArray listJSON = new JSONArray();
		ArrayList<Producto> otrosProductos = ProductoDAO.GetProductosTienda(idtienda);
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
			cadaProductoJSON.put("manejacantidad", produ.getManejacantidad());
			listJSON.add(cadaProductoJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String  obtenerHomologacionGaseosaIncluida()
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<HomologaGaseosaIncluida> gaseosaHomologada = PedidoDAO.obtenerHomologacionGaseosaIncluida();
		for (HomologaGaseosaIncluida homologa : gaseosaHomologada) {
			JSONObject cadaGaseosaJSON = new JSONObject();
			cadaGaseosaJSON.put("idtienda", homologa.getIdtienda());
			cadaGaseosaJSON.put("idsabortipoliquido", homologa.getIdsabortipoliquido());
			listJSON.add(cadaGaseosaJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	/**
	 * Método que se encarga de retornar las gaseosas como producto homologada para cada tienda
	 * @return
	 */
	public String obtenerHomologacionProductoGaseosa(int idtienda)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<HomologaGaseosaIncluida> gaseosaHomologada = PedidoDAO.obtenerHomologacionProductoGaseosa(idtienda);
		for (HomologaGaseosaIncluida homologa : gaseosaHomologada) {
			JSONObject cadaGaseosaJSON = new JSONObject();
			cadaGaseosaJSON.put("idtienda", homologa.getIdtienda());
			cadaGaseosaJSON.put("idproducto", homologa.getIdsabortipoliquido());
			cadaGaseosaJSON.put("nombre", homologa.getNombre());
			cadaGaseosaJSON.put("preciogeneral", homologa.getPrecioGeneral());
			listJSON.add(cadaGaseosaJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	/**
	 * Método en la capa controladora que se encarga de invocar a la capa DAO y obtener todos los productos incluidos
	 * posteriormente los formatea en el JSON y se los envía al servicio para ser retornados
	 * @return Retorna un String en formato JSON con los productos incluidos parametrizados en el sistema.
	 */
	public String obtenerProductosIncluidos(){
		JSONArray listJSON = new JSONArray();
		ArrayList<ProductoIncluido> productosIncluidos = PedidoDAO.obtenerProductosIncluidos();
		for (ProductoIncluido produ : productosIncluidos) {
			JSONObject cadaProductoJSON = new JSONObject();
			cadaProductoJSON.put("idproductoincluido", produ.getIdproductoincluido());
			cadaProductoJSON.put("idproductopadre", produ.getIdproductopadre());
			cadaProductoJSON.put("idproductohijo", produ.getIdproductohijo());
			cadaProductoJSON.put("cantidad", produ.getCantidad());
			cadaProductoJSON.put("nombre", produ.getNombre());
			cadaProductoJSON.put("preciogeneral", produ.getPreciogeneral());
			listJSON.add(cadaProductoJSON);
		}
		
		return listJSON.toJSONString();
	}
	
	public String InsertarEncabezadoPedido(String tienda,int idcliente, String fechaPedido, String user){
		int idtienda = TiendaDAO.obteneridTienda(tienda);
		JSONArray listJSON = new JSONArray();
		int resultado = 0;
		if (idcliente != 0)
		{
			resultado = PedidoDAO.InsertarEncabezadoPedido(idtienda, idcliente, fechaPedido, user);
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
	
	public String InsertarModificadorDetallePedido(int iddetallepedidopadre, int idproductoespecialidad1, int idproductoespecialidad2, double cantidad, int iddetallepedido){
		JSONArray listJSON = new JSONArray();
		ModificadorDetallePedido modDetPedido = new ModificadorDetallePedido(0,iddetallepedidopadre, idproductoespecialidad1, idproductoespecialidad2, cantidad, iddetallepedido);
		int idmodificador = PedidoDAO.InsertarModificadorDetallePedido(modDetPedido);
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idmodificador", idmodificador);
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String InsertarMarcacionPedido(MarcacionPedido marPedido){
		MarcacionPedidoDAO.InsertarMarcacionPedido(marPedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("resultado", "OK");
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
			cadaExcepcionJSON.put("controlacantidadingredientes", exc.getControlaCantidadIngredientes());
			cadaExcepcionJSON.put("cantidadingredientes", exc.getCantidadIngrediantes());
			cadaExcepcionJSON.put("partiradiciones", exc.getPartiradiciones());
			cadaExcepcionJSON.put("horainicial", exc.getHoraInicial());
			cadaExcepcionJSON.put("horafinal", exc.getHoraFinal());
			cadaExcepcionJSON.put("lunes", exc.getLunes());
			cadaExcepcionJSON.put("martes", exc.getMartes());
			cadaExcepcionJSON.put("miercoles", exc.getMiercoles());
			cadaExcepcionJSON.put("jueves", exc.getJueves());
			cadaExcepcionJSON.put("viernes", exc.getViernes());
			cadaExcepcionJSON.put("sabado", exc.getSabado());
			cadaExcepcionJSON.put("domingo", exc.getDomingo());
			cadaExcepcionJSON.put("controlaespecialidades", exc.getControlaEspecialidades());
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
	
	
	public String ObtenerSaboresLiquidoProducto(int idProdu, int idtienda)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<SaborLiquido> saboresLiquidos = PedidoDAO.ObtenerSaboresLiquidoProducto(idProdu, idtienda);
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
	
	public String ObtenerSaboresLiquidoExcepcion(int idExcep, int idtienda)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<SaborLiquido> saboresLiquidos = PedidoDAO.ObtenerSaboresLiquidoExcepcion(idExcep, idtienda);
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
	
	/**
	 * Método que se encarga de realizar las acciones para finalizar el pedido en el sistema de Contact Center, secuencialmente
	 * se finaliza el pedido en la BD de contact center, se totaliza el valor del pedido y se cambia el estado del pedido, adicionalmente
	 * se forma un json con la informaciónd del pedido, con la cual se invocará el servicio de tienda con los datos necesarios del pedido.
	 * @param idpedido
	 * @param idformapago
	 * @param valorformapago
	 * @param valortotal
	 * @param idcliente
	 * @param insertado
	 * @param tiempopedido
	 * @return Se retorna un string en formato JSON con todos los valores que se pasarán al servicio tienda para la creación del pedido en la 
	 * tienda.
	 */
	public String FinalizarPedido(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado, double tiempopedido, String validaDir, double descuento, String motivoDescuento)
	{
		Tienda tienda = PedidoDAO.obtenerTiendaPedido(idpedido);
		String tiendaPixel = tienda.getUrl();
		//Capturamos el parámetro de para que POS irá el destino del pedido, con base en esto se formará la información para enviar
		int pos = tienda.getPos();
		//Validamos si el Pos es igual a 2 el envío será para PIXEL, en caso contrario será para POS Pizza Americana
		InsertarPedidoPixel pedidoPixel = new InsertarPedidoPixel();
		if(pos == 2)
		{
			pedidoPixel = PedidoDAO.finalizarPedido(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado, tiempopedido);
		}else if (pos == 1)
		{
			pedidoPixel = PedidoDAO.finalizarPedidoPOSPM(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado, tiempopedido, descuento);
		}
		JSONArray listJSON = new JSONArray();
		JSONArray listJSONCliente = new JSONArray();
		JSONObject respuesta = new JSONObject();
		respuesta.put("idpedido", idpedido);
		respuesta.put("valortotal", valortotal);
		respuesta.put("descuento", descuento);
		respuesta.put("motivodescuento", motivoDescuento);
		respuesta.put("insertado", "true");
		respuesta.put("url", tiendaPixel);
		respuesta.put("dsntienda", pedidoPixel.getDsnTienda());
		respuesta.put("memcode", pedidoPixel.getMemcode());
		respuesta.put("indicadoract", pedidoPixel.getIndicadorAct());
		respuesta.put("valorformapago", pedidoPixel.getValorformapago());
		respuesta.put("idformapagotienda", pedidoPixel.getIdformapagotienda());
		respuesta.put("pos", pos);
		respuesta.put("usuariopedido", "CONTACT");
		respuesta.put("tiempopedido", tiempopedido);
		ArrayList<DetallePedidoPixel> detPedPOS = new ArrayList();
		detPedPOS = pedidoPixel.getEnvioPixel();
		//Se realiza un ciclo For para obtener y formatear en json cada uno de los detalles pedidos
		int contador = 1;
		//Validamos si que pos es para saber que datos enviamos
		if(pos == 2)
		{
			for(DetallePedidoPixel detPed: detPedPOS)
			{
				
				respuesta.put("idproductoext" + contador, detPed.getIdproductoext() );
				respuesta.put("cantidad" + contador, detPed.getCantidad());
				//System.out.println("idproductoext " + detPed.getIdproductoext() + " cantidad " + detPed.getCantidad() );
				contador++;
				
			}
		}else if (pos == 1)
		{
			for(DetallePedidoPixel detPed: detPedPOS)
			{
				
				respuesta.put("idproductoext" + contador, detPed.getIdproductoext() );
				respuesta.put("cantidad" + contador, detPed.getCantidad());
				respuesta.put("esmaster" + contador, detPed.isEsMaster());
				respuesta.put("idmaster" + contador, detPed.getIdMaster());
				respuesta.put("idmodificador" + contador, detPed.getIdModificador());
				respuesta.put("iddetalle" + contador, detPed.getIdDetallePedido());
				//System.out.println("idproductoext " + detPed.getIdproductoext() + " cantidad " + detPed.getCantidad() + " iddetalle " + detPed.getIdDetallePedido());
				contador++;
				
			}
		}
		contador--;
		respuesta.put("cantidaditempedido", contador);
		JSONObject clienteJSON = new JSONObject();
		// El objeto cliente del cual se extraen cada uno de los parámetros y se formatea en JSON.
		clienteJSON.put("apellidos", pedidoPixel.getCliente().getApellidos());
		clienteJSON.put("nombres", pedidoPixel.getCliente().getNombres());
		clienteJSON.put("nombrecompania", pedidoPixel.getCliente().getNombreCompania());
		if (validaDir.equals(new String("S")))
		{
			clienteJSON.put("direccion", pedidoPixel.getCliente().getNomenclatura() + " " + pedidoPixel.getCliente().getNumNomenclatura() + " # " + pedidoPixel.getCliente().getNumNomenclatura2() + " - " + pedidoPixel.getCliente().getNum3() );
		}else
		{
			clienteJSON.put("direccion", pedidoPixel.getCliente().getDireccion() );
		}
		clienteJSON.put("telefono", pedidoPixel.getCliente().getTelefono());
		clienteJSON.put("idcliente", pedidoPixel.getCliente().getIdcliente());
		clienteJSON.put("memcode", pedidoPixel.getCliente().getMemcode());
		clienteJSON.put("zonadireccion", pedidoPixel.getCliente().getZonaDireccion());
		clienteJSON.put("observacion", pedidoPixel.getCliente().getObservacion());
		clienteJSON.put("memcode", pedidoPixel.getCliente().getMemcode());
		clienteJSON.put("idnomenclatura", pedidoPixel.getCliente().getIdnomenclatura());
		clienteJSON.put("nomenclatura", pedidoPixel.getCliente().getNomenclatura());
		clienteJSON.put("numnomenclatura", pedidoPixel.getCliente().getNumNomenclatura());
		clienteJSON.put("numnomenclatura2", pedidoPixel.getCliente().getNumNomenclatura2());
		clienteJSON.put("num3", pedidoPixel.getCliente().getNum3());
		clienteJSON.put("municipio", pedidoPixel.getCliente().getMunicipio());
		clienteJSON.put("idmunicipio", pedidoPixel.getCliente().getIdMunicipio());
		clienteJSON.put("latitud", pedidoPixel.getCliente().getLatitud());
		clienteJSON.put("longitud", pedidoPixel.getCliente().getLontitud());
		clienteJSON.put("distanciatienda", pedidoPixel.getCliente().getDistanciaTienda());
		listJSONCliente.add(clienteJSON);
		respuesta.put("cliente", listJSONCliente.toString());
		listJSON.add(respuesta);
		String respuestaJson = listJSON.toJSONString();
		PedidoDAO.actualizarJSONPedido(idpedido, respuestaJson);
		return(respuestaJson);
	}
	
	/**
	 * Método que se encarga de finalizar un pedido en el modo reenvío, teniendo en cuenta que la información del pedido en el
	 * sistema contact center ya fue confirmado pero faltaría confirmarlo en el sistema de la tienda
	 * @param idpedido
	 * @param idformapago
	 * @param valorformapago
	 * @param valortotal
	 * @param idcliente
	 * @param insertado
	 * @return
	 */
	public String FinalizarPedidoReenvio(int idpedido, int idformapago, double valorformapago, double valortotal, int idcliente, int insertado)
	{
		
		Tienda tienda = PedidoDAO.obtenerTiendaPedido(idpedido);
		String tiendaPixel = tienda.getUrl();
		//Capturamos el parámetro de para que POS irá el destino del pedido, con base en esto se formará la información para enviar
		int pos = tienda.getPos();
		//Validamos si el Pos es igual a 2 el envío será para PIXEL, en caso contrario será para POS Pizza Americana
		InsertarPedidoPixel pedidoPixel = new InsertarPedidoPixel();
		if(pos == 2)
		{
			pedidoPixel = PedidoDAO.finalizarPedidoReenvio(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado);
		}else if (pos == 1)
		{
			pedidoPixel = PedidoDAO.finalizarPedidoReenvioPOSPM(idpedido, idformapago, valorformapago, valortotal, idcliente, insertado);
		}
		
		
		JSONArray listJSON = new JSONArray();
		JSONArray listJSONCliente = new JSONArray();
		JSONObject respuesta = new JSONObject();
		respuesta.put("idpedido", idpedido);
		respuesta.put("valortotal", valortotal);
		respuesta.put("insertado", "true");
		respuesta.put("url", tiendaPixel);
		respuesta.put("dsntienda", pedidoPixel.getDsnTienda());
		respuesta.put("memcode", pedidoPixel.getMemcode());
		respuesta.put("indicadoract", pedidoPixel.getIndicadorAct());
		respuesta.put("valorformapago", pedidoPixel.getValorformapago());
		respuesta.put("idformapagotienda", pedidoPixel.getIdformapagotienda());
		respuesta.put("pos", pos);
		respuesta.put("usuariopedido", "CONTACT");
		respuesta.put("tiempopedido", 0);
		ArrayList<DetallePedidoPixel> detPedPixel = pedidoPixel.getEnvioPixel();
		//Se realiza un ciclo For para obtener y formatear en json cada uno de los detalles pedidos
		int contador = 1;
		if(pos == 2)
		{
			for(DetallePedidoPixel detPed: detPedPixel)
			{
				
				respuesta.put("idproductoext" + contador, detPed.getIdproductoext() );
				respuesta.put("cantidad" + contador, detPed.getCantidad());
				System.out.println("idproductoext " + detPed.getIdproductoext() + " cantidad " + detPed.getCantidad() );
				contador++;
				
			}
		}else if (pos == 1)
		{
			for(DetallePedidoPixel detPed: detPedPixel)
			{
				
				respuesta.put("idproductoext" + contador, detPed.getIdproductoext() );
				respuesta.put("cantidad" + contador, detPed.getCantidad());
				respuesta.put("esmaster" + contador, detPed.isEsMaster());
				respuesta.put("idmaster" + contador, detPed.getIdMaster());
				respuesta.put("idmodificador" + contador, detPed.getIdModificador());
				respuesta.put("iddetalle" + contador, detPed.getIdDetallePedido());
				//System.out.println("idproductoext " + detPed.getIdproductoext() + " cantidad " + detPed.getCantidad() + " iddetalle " + detPed.getIdDetallePedido());
				contador++;
				
			}
		}
		contador--;
		respuesta.put("cantidaditempedido", contador);
		JSONObject clienteJSON = new JSONObject();
		// El objeto cliente del cual se extraen cada uno de los parámetros y se formatea en JSON.
		clienteJSON.put("apellidos", pedidoPixel.getCliente().getApellidos());
		clienteJSON.put("nombres", pedidoPixel.getCliente().getNombres());
		clienteJSON.put("nombrecompania", pedidoPixel.getCliente().getNombreCompania());
		clienteJSON.put("direccion", pedidoPixel.getCliente().getDireccion());
		clienteJSON.put("telefono", pedidoPixel.getCliente().getTelefono());
		clienteJSON.put("apellidos", pedidoPixel.getCliente().getApellidos());
		clienteJSON.put("idcliente", pedidoPixel.getCliente().getIdcliente());
		clienteJSON.put("memcode", pedidoPixel.getCliente().getMemcode());
		clienteJSON.put("zonadireccion", pedidoPixel.getCliente().getZonaDireccion());
		clienteJSON.put("observacion", pedidoPixel.getCliente().getObservacion());
		clienteJSON.put("memcode", pedidoPixel.getCliente().getMemcode());
		clienteJSON.put("idnomenclatura", pedidoPixel.getCliente().getIdnomenclatura());
		clienteJSON.put("nomenclatura", pedidoPixel.getCliente().getNomenclatura());
		clienteJSON.put("numnomenclatura", pedidoPixel.getCliente().getNumNomenclatura());
		clienteJSON.put("numnomenclatura2", pedidoPixel.getCliente().getNumNomenclatura2());
		clienteJSON.put("num3", pedidoPixel.getCliente().getNum3());
		clienteJSON.put("municipio", pedidoPixel.getCliente().getMunicipio());
		clienteJSON.put("idmunicipio", pedidoPixel.getCliente().getIdMunicipio());
		clienteJSON.put("latitud", pedidoPixel.getCliente().getLatitud());
		clienteJSON.put("longitud", pedidoPixel.getCliente().getLontitud());
		listJSONCliente.add(clienteJSON);
		respuesta.put("cliente", listJSONCliente.toString());
		listJSON.add(respuesta);
		String respuestaJson = listJSON.toJSONString();
		PedidoDAO.actualizarJSONPedido(idpedido, respuestaJson);
		return(respuestaJson);
	}
	
	public String eliminarPedidoSinConfirmar(int idpedido, String usuario)
	{
		int idcliente = PedidoDAO.obtenerIdClientePedido(idpedido);
		boolean resultado = PedidoDAO.eliminarPedidoSinConfirmar(idpedido, idcliente, usuario);
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
	
	public String obtenerFormaPagoPedido(int idpedido)
	{
		FormaPago formapago = PedidoDAO.obtenerFormaPagoPedido(idpedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idformapago", formapago.getIdformapago());
		Respuesta.put("valortotal", formapago.getValortotal());
		Respuesta.put("valorformapago", formapago.getValorformapago());
		Respuesta.put("nombre", formapago.getNombre());
		Respuesta.put("descuento", formapago.getDescuento());
		listJSON.add(Respuesta);
		return(listJSON.toJSONString());
	}
	
	public String obtenerMarcacionesPedido(int idpedido)
	{
		ArrayList<MarcacionPedido> marcacionesPedido = PedidoDAO.obtenerMarcacionesPedido(idpedido);
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		for(MarcacionPedido cadaMarcacion: marcacionesPedido)
		{
			Respuesta.put("idpedido", cadaMarcacion.getIdPedido());
			Respuesta.put("idmarcacion", cadaMarcacion.getIdMarcacion() );
			Respuesta.put("nombremarcacion", cadaMarcacion.getNombreMarcacion() );
			Respuesta.put("observacion", cadaMarcacion.getObservacion());
			Respuesta.put("descuento", cadaMarcacion.getDescuento());
			Respuesta.put("motivo", cadaMarcacion.getMotivo() );
			listJSON.add(Respuesta);
		}
		
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
			cadaPedidoJSON.put("totalneto",cadaPedido.getTotal_neto());
			cadaPedidoJSON.put("idcliente", cadaPedido.getIdcliente());
			cadaPedidoJSON.put("cliente", cadaPedido.getNombrecliente());
			cadaPedidoJSON.put("estadopedido", cadaPedido.getEstadopedido());
			cadaPedidoJSON.put("enviadopixel", cadaPedido.getEnviadoPixel());
			if(cadaPedido.getEnviadoPixel() == 1)
			{
				cadaPedidoJSON.put("estadoenviotienda", "ENVIADO A TIENDA");
			}
			else
			{
				cadaPedidoJSON.put("estadoenviotienda", "PENDIENTE TIENDA");
			}
			cadaPedidoJSON.put("numposheader", cadaPedido.getNumposheader());
			cadaPedidoJSON.put("idtienda", cadaPedido.getTienda().getIdTienda());
			cadaPedidoJSON.put("urltienda", cadaPedido.getTienda().getUrl());
			cadaPedidoJSON.put("stringpixel", cadaPedido.getStringpixel());
			cadaPedidoJSON.put("fechainsercion", cadaPedido.getFechainsercion());
			cadaPedidoJSON.put("usuariopedido", cadaPedido.getUsuariopedido());
			cadaPedidoJSON.put("direccion", cadaPedido.getDireccion());
			cadaPedidoJSON.put("telefono", cadaPedido.getTelefono());
			cadaPedidoJSON.put("formapago", cadaPedido.getFormapago());
			cadaPedidoJSON.put("idformapago", cadaPedido.getIdformapago());
			cadaPedidoJSON.put("tiempopedido", cadaPedido.getTiempopedido());
			listJSON.add(cadaPedidoJSON);
		}
		return listJSON.toJSONString();
	}
	
	
	/**
	 * Método que responde desde la capa controladora para la consulta del encabezado de los pedidos de un cliente.
	 * @param idCliente, se recibe como parámetro el identificador del cliente para la consulta
	 * @return
	 */
	public String ConsultaUltimosPedidosCliente(int idCliente)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList <Pedido> consultaPedidos = PedidoDAO.ConsultaUltimosPedidosCliente(idCliente);
		for (Pedido cadaPedido: consultaPedidos){
			JSONObject cadaPedidoJSON = new JSONObject();
			cadaPedidoJSON.put("idpedido", cadaPedido.getIdpedido());
			cadaPedidoJSON.put("tienda", cadaPedido.getNombretienda());
			cadaPedidoJSON.put("totalneto",cadaPedido.getTotal_neto());
			cadaPedidoJSON.put("idcliente", cadaPedido.getIdcliente());
			cadaPedidoJSON.put("cliente", cadaPedido.getNombrecliente());
			cadaPedidoJSON.put("estadopedido", cadaPedido.getEstadopedido());
			cadaPedidoJSON.put("enviadopixel", cadaPedido.getEnviadoPixel());
			if(cadaPedido.getEnviadoPixel() == 1)
			{
				cadaPedidoJSON.put("estadoenviotienda", "ENVIADO A TIENDA");
			}
			else
			{
				cadaPedidoJSON.put("estadoenviotienda", "PENDIENTE TIENDA");
			}
			cadaPedidoJSON.put("numposheader", cadaPedido.getNumposheader());
			cadaPedidoJSON.put("idtienda", cadaPedido.getTienda().getIdTienda());
			cadaPedidoJSON.put("urltienda", cadaPedido.getTienda().getUrl());
			cadaPedidoJSON.put("stringpixel", cadaPedido.getStringpixel());
			cadaPedidoJSON.put("fechainsercion", cadaPedido.getFechainsercion());
			cadaPedidoJSON.put("usuariopedido", cadaPedido.getUsuariopedido());
			cadaPedidoJSON.put("direccion", cadaPedido.getDireccion());
			cadaPedidoJSON.put("telefono", cadaPedido.getTelefono());
			cadaPedidoJSON.put("formapago", cadaPedido.getFormapago());
			cadaPedidoJSON.put("idformapago", cadaPedido.getIdformapago());
			cadaPedidoJSON.put("tiempopedido", cadaPedido.getTiempopedido());
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
			cadaDetallePedidoJSON.put("modespecialidad1", cadaDetallePedido.getModespecialidad1());
			cadaDetallePedidoJSON.put("especialidad2",cadaDetallePedido.getNombreespecialidad2());
			cadaDetallePedidoJSON.put("modespecialidad2", cadaDetallePedido.getModespecialidad2());
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
	
	/**
	 * Método que se encarga de permitir duplicar un iddetallepedido, validando todo lo que tiene el id pedido padre
	 * y si es posible duplicar todo lo asociado a este.
	 * @param iddetallepedido Se recibe como parámetro el iddetallepedido asociado a la duplicación, desde la capa de
	 * presentación se permite realizar la duplicación de productos diferentes a adiciones y modificadores.
	 * @return Se retorna un String en formato JSON con la respuesta de cada uno de los detalles pedidos insertados
	 * y que se tomarán como bases para pintar el DATATABLE.
	 */
	public String DuplicarDetallePedido(int iddetallepedido)
	{
				//Utilizamos método que se encarga de retonar todos los detalles pedido asignado a un grupo de pedido
				//donde podría tenerse modificadores y adiciones, retornados en un ArrayList.
				ArrayList<DetallePedido> detallesPedido = PedidoDAO.ConsultarDetallePedidoPorPadre(iddetallepedido);
				//Se instancia ArrayList en donde se insertaran los nuevos detalles pedidos productos de la duplicación
				// de los iniciales.
				ArrayList<DetallePedido> detallesPedidoNuevos = new ArrayList();
				int idDetPedIns;
				//Se lleva el control del que será el detalle pedido padre, esto se detecta por el tipo de pedido.
				boolean bandDetPedido = false;
				int idDetPedidoPadre = 0;
				// Se define arreglo donde se llevaran lo iddetallepedidos nuevo y viejos
				int[][] idDetsPeds = new int[20][2];
				int contDetPeds = 0;
				//Se recorren uno a uno los detalle de pedido para insertarse como duplicado
				for (DetallePedido detPed : detallesPedido) {
					// Se realiza la inserción de de pedido, no existe inconveniente debido a que no se toma el iddetallepedido
					idDetsPeds[contDetPeds][0] = detPed.getIddetallepedido();
					//Antes de realizar la inserción del detalle pedido controlaremos si es un producto incluido para cambiar la información
					if (detPed.getObservacion().equals(new String("Producto Incluido-"+ iddetallepedido)))
					{
						detPed.setObservacion("Producto Incluido-"+ idDetPedidoPadre);
					}
					// Se realiza la inserción del detallepedido
					idDetPedIns = PedidoDAO.InsertarDetallePedido(detPed);
					// Se lleva registro en el array del iddetallepedido recién insertado.
					idDetsPeds[contDetPeds][1] = idDetPedIns;
					// Se lleva control de si es el detalle pedido principal con la marcación de que sea sola una vez, sea tipo de prodcutos OTROS o PIZZA y adicionalmente no sea un producto incluido
					if (bandDetPedido == false && (detPed.getTipoProducto().equals(new String("OTROS")) || detPed.getTipoProducto().equals(new String("PIZZA"))) && !(detPed.getObservacion().equals(new String("Producto Incluido-"+ iddetallepedido))) )
					{
						idDetPedidoPadre = idDetPedIns;
						bandDetPedido = true;
					}
					// Se clona el objeto para este ser editado y adicionarlo al ArrayList que se responderá.					
					DetallePedido detPedTemp = (DetallePedido) detPed.clone();
					detPedTemp.setIddetallepedido(idDetPedIns);
					// Se agrega el detallePedido al ArrayList de respuesta
					detallesPedidoNuevos.add(detPedTemp);
					// Se lleva contador de detalles pedidos adiciones/duplicados
					contDetPeds++;
				}
				//Obtenemos ArrayList con las adiciones dado un detallepedido
				ArrayList<DetallePedidoAdicion> adicionDetallePedido = PedidoDAO.ObtenerAdicionDetallePedido(iddetallepedido);
				//Recorremos los detalles de las adiciones
				for(DetallePedidoAdicion detPedAdi: adicionDetallePedido)
				{
					//Nos traemos el detalle de pedido padre que significa el producto detalle pedido al cual
					// esta asociada la adición.
					int idDetPedAdiAnt =  detPedAdi.getIddetallepedidoadicion();
					for (int i= 0; i < contDetPeds; i++)
					{
						//buscamos uno a uno el arreglo donde se guardan los detalles pedidos
						if (idDetsPeds[i][0] == idDetPedAdiAnt)
						{
							// Se realizan los cambios en el objeto para dejar la información correcta
							detPedAdi.setIddetallepedidoadicion(idDetsPeds[i][1]);
							detPedAdi.setIddetallepedidopadre(idDetPedidoPadre);
							// Se realiza la inserción del detalle pedido adición.
							PedidoDAO.InsertarDetalleAdicion(detPedAdi);
						}
						
					}
				}
				// Se extrae un Array List con los modificadores de detalle pedido 
				ArrayList<ModificadorDetallePedido> modificadorDetallePedido = PedidoDAO.ObtenerModificadorDetallePedido(iddetallepedido);
				// Se recorren uno a uno los modificadores detalle pedido.
				for(ModificadorDetallePedido modDetPed: modificadorDetallePedido)
				{
					int idDetPedPadAnt =  modDetPed.getIddetallepedidopadre();
					for (int i= 0; i < contDetPeds; i++)
					{
						if (idDetsPeds[i][0] == idDetPedPadAnt)
						{
							modDetPed.setIddetallepedidopadre(idDetPedidoPadre);
							// Cuando se tiene un modificador detalle pedido que en el campo iddetallepedidoasociado
							// es diferente de cero se tiene que asociar el iddetallepedido insertado, esto se da
							// cuando hay modificadores que traen un precio adicional para la pizza.
							if(modDetPed.getIddetallepedidoasociado() != 0)
							{
								int idDetPedAso = modDetPed.getIddetallepedidoasociado();
								for (int j= 0; j < contDetPeds; j++)
								{
									if (idDetsPeds[j][0] == idDetPedAso)
									{
										modDetPed.setIddetallepedidoasociado(idDetsPeds[j][1]);;
									}
								}
							}
							PedidoDAO.InsertarModificadorDetallePedido(modDetPed);
						}
						
					}
				}		
				JSONArray listJSON = new JSONArray();
				// Se realiza el formateo en JSON para la respuesta de los detalles pedidos duplicados para que sean 
				// pintados en la capa de presentación.
				for (DetallePedido cadaDetallePedido: detallesPedidoNuevos){
					JSONObject cadaDetallePedidoJSON = new JSONObject();
					cadaDetallePedidoJSON.put("iddetallepedido", cadaDetallePedido.getIddetallepedido());
					if(cadaDetallePedido.getTipoProducto().equals(new String("PIZZA")))
					{
						cadaDetallePedidoJSON.put("pizza", cadaDetallePedido.getNombreproducto());
					}else
					{
						cadaDetallePedidoJSON.put("otroprod", cadaDetallePedido.getNombreproducto());
					}
					cadaDetallePedidoJSON.put("cantidad", cadaDetallePedido.getCantidad());
					cadaDetallePedidoJSON.put("especialidad1", cadaDetallePedido.getNombreespecialidad1());
					cadaDetallePedidoJSON.put("especialidad2", cadaDetallePedido.getNombreespecialidad2());
					cadaDetallePedidoJSON.put("liquido", cadaDetallePedido.getLiquido());
					cadaDetallePedidoJSON.put("observacion", cadaDetallePedido.getObservacion());
					cadaDetallePedidoJSON.put("adicion", cadaDetallePedido.getAdicion());
					cadaDetallePedidoJSON.put("valorunitario", cadaDetallePedido.getValorunitario());
					cadaDetallePedidoJSON.put("valortotal", cadaDetallePedido.getValortotal());
					cadaDetallePedidoJSON.put("tipo", cadaDetallePedido.getTipoProducto());
					listJSON.add(cadaDetallePedidoJSON);
				}
				return(listJSON.toJSONString());
	}
	
	/**
	 * Método de la capa controladora que se encarga de retornar en formato JSON el precio asociado a una excepción de
	 * precio de especialidad y producto.
	 * @param idespecialidad Se recibe como parámetro el idespecialidad asociado a la excepción de precio especialidad.
	 * @param idproducto Se recibe como parámetro el idproducto asociado a la excepción de precio especialidad.
	 * @return Se retorna en un String formato json el precio asociado según los parámetros de entrada|
	 */
	public String obtenerPrecioExcepcionEspecialidad(int idespecialidad, int idproducto)
	{
		JSONArray listJSON = new JSONArray();
		double precio = EspecialidadDAO.obtenerPrecioExcepcionEspecialidad(idespecialidad, idproducto);
		JSONObject precioJSON = new JSONObject();
		precioJSON.put("precio", precio );
		listJSON.add(precioJSON);
		return listJSON.toJSONString();
		
	}
	
	public String ConsultarDireccionesPedido(String fechainicial, String fechafinal, String idMunicipio, String idTienda, String horaIni, String horaFin)
	{
		ArrayList <DireccionFueraZona> dirsPedido = PedidoDAO.ConsultarDireccionesPedido(fechainicial, fechafinal, idMunicipio, idTienda, horaIni, horaFin);
		JSONArray listJSON = new JSONArray();
		JSONObject ResultadoJSON = new JSONObject();
		for (DireccionFueraZona dirTemp : dirsPedido) 
		{
			ResultadoJSON = new JSONObject();
			ResultadoJSON.put("idpedido", dirTemp.getId());
			ResultadoJSON.put("direccion", dirTemp.getDireccion());
			ResultadoJSON.put("municipio", dirTemp.getMunicipio());
			ResultadoJSON.put("idcliente", dirTemp.getIdCliente());
			ResultadoJSON.put("latitud", dirTemp.getLatitud());
			ResultadoJSON.put("longitud", dirTemp.getLongitud());
			ResultadoJSON.put("telefono", dirTemp.getTelefono());
			ResultadoJSON.put("nombre", dirTemp.getNombre());
			ResultadoJSON.put("apellido", dirTemp.getApellido());
			ResultadoJSON.put("fechapedido", dirTemp.getFechaIngreso());
			ResultadoJSON.put("valor", dirTemp.getValor());
			listJSON.add(ResultadoJSON);
		}
		//Tendremos una tabla temporal para reportes clientes y pedidos dentro de un poligono
		TmpPedidosPoligonoDAO.limpiarTabla();
		return listJSON.toJSONString();
		
	}
	
	public String insertarTmpPedidoPoligono(int idPedido, int idCliente)
	{
		int respuesta = TmpPedidosPoligonoDAO.insertarTmpPedidoPoligono(idPedido, idCliente);
		JSONObject ResultadoJSON  = new JSONObject();
		if(respuesta > 0)
		{
			ResultadoJSON.put("resultado", "OK");
		}else
		{
			ResultadoJSON.put("resultado", "NOK");
		}
		return(ResultadoJSON.toJSONString());
	}
	
	
	public ArrayList<Pedido> ConsultarPedidosPendientes(String fechaPed)
	{
		ArrayList<Pedido> pedidosPendientes = PedidoDAO.ConsultarPedidosPendientes(fechaPed);
		return(pedidosPendientes);
		
	}
}
