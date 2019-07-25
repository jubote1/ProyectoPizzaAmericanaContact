package capaControlador;
import capaModelo.Cliente;
import capaModelo.Tienda;
import capaDAO.ClienteDAO;
import capaDAO.MunicipioDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiendaDAO;

import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * 
 * @author Juan David Botero Duque
 * Clase qeu tiene como objetivo en la capa de Controlador para todo el esquema de clientes
 */
public class ClienteCtrl {
	
	/**
	 * 
	 * @param tel recibe el valor del tel�fono para buscar al cliente en la base de datos y devolver en una estructura json
	 * las incripciones del cliente que corresponden al n�mero telef�nico entregado como par�metro.
	 * @return un valor String en formato json con el arreglo de inscripciones en la tabla cliente que responden al tel�fono
	 * enviado como par�metro
	 * NOTA: En la capa DAO validamos el estado del  cliente.
	 */
	public String obtenerCliente(String tel){
		JSONArray listJSON = new JSONArray();
		ArrayList<Cliente> clientes = ClienteDAO.obtenerCliente(tel);
		for (Cliente cliente : clientes) {
			JSONObject cadaViajeJSON = new JSONObject();
			cadaViajeJSON.put("idCliente", cliente.getIdcliente());
			cadaViajeJSON.put("tienda", cliente.getTienda());
			cadaViajeJSON.put("nombre", cliente.getNombres());
			cadaViajeJSON.put("apellido", cliente.getApellidos());
			cadaViajeJSON.put("nombrecompania", cliente.getNombreCompania());
			cadaViajeJSON.put("direccion", cliente.getDireccion());
			cadaViajeJSON.put("zona", cliente.getZonaDireccion());
			cadaViajeJSON.put("observacion", cliente.getObservacion());
			cadaViajeJSON.put("telefono", cliente.getTelefono());
			cadaViajeJSON.put("municipio", cliente.getMunicipio());
			cadaViajeJSON.put("longitud", cliente.getLontitud());
			cadaViajeJSON.put("latitud", cliente.getLatitud());
			cadaViajeJSON.put("distanciatienda", cliente.getDistanciaTienda());
			cadaViajeJSON.put("memcode", cliente.getMemcode());
			cadaViajeJSON.put("idnomenclatura", cliente.getIdnomenclatura());
			cadaViajeJSON.put("numnomenclatura1", cliente.getNumNomenclatura());
			cadaViajeJSON.put("numnomenclatura2", cliente.getNumNomenclatura2());
			cadaViajeJSON.put("num3", cliente.getNum3());
			cadaViajeJSON.put("nomenclatura", cliente.getNomenclatura());
			listJSON.add(cadaViajeJSON);
		}
		//String temp = listJSON.toJSONString();
		//temp = temp.substring(0,1) + "\"cliente\":[" + temp.substring(1, temp.length()-1) + "]]";
		//System.out.println(temp);
		//System.out.println(listJSON.toJSONString());
		return listJSON.toJSONString();
	}
	
	/**
	 * 
	 * @param tel recibe el valor del tel�fono para buscar al cliente en la base de datos y devolver en una estructura json
	 * las incripciones del cliente que corresponden al n�mero telef�nico entregado como par�metro.
	 * @return un valor String en formato json con el arreglo de inscripciones en la tabla cliente que responden al tel�fono
	 * enviado como par�metro
	 * NOTA: En la capa DAO no para este en espec�fico validamos el estado del  cliente.
	 */
	public String obtenerClienteTodos(String tel){
		JSONArray listJSON = new JSONArray();
		ArrayList<Cliente> clientes = ClienteDAO.obtenerClienteTodos(tel);
		for (Cliente cliente : clientes) {
			JSONObject cadaViajeJSON = new JSONObject();
			cadaViajeJSON.put("idCliente", cliente.getIdcliente());
			cadaViajeJSON.put("tienda", cliente.getTienda());
			cadaViajeJSON.put("nombre", cliente.getNombres());
			cadaViajeJSON.put("apellido", cliente.getApellidos());
			cadaViajeJSON.put("nombrecompania", cliente.getNombreCompania());
			cadaViajeJSON.put("direccion", cliente.getDireccion());
			cadaViajeJSON.put("zona", cliente.getZonaDireccion());
			cadaViajeJSON.put("observacion", cliente.getObservacion());
			cadaViajeJSON.put("telefono", cliente.getTelefono());
			cadaViajeJSON.put("municipio", cliente.getMunicipio());
			cadaViajeJSON.put("longitud", cliente.getLontitud());
			cadaViajeJSON.put("latitud", cliente.getLatitud());
			cadaViajeJSON.put("distanciatienda", cliente.getDistanciaTienda());
			cadaViajeJSON.put("memcode", cliente.getMemcode());
			cadaViajeJSON.put("idnomenclatura", cliente.getIdnomenclatura());
			cadaViajeJSON.put("numnomenclatura1", cliente.getNumNomenclatura());
			cadaViajeJSON.put("numnomenclatura2", cliente.getNumNomenclatura2());
			cadaViajeJSON.put("num3", cliente.getNum3());
			cadaViajeJSON.put("nomenclatura", cliente.getNomenclatura());
			cadaViajeJSON.put("estado", cliente.getEstado());
			listJSON.add(cadaViajeJSON);
		}
		//String temp = listJSON.toJSONString();
		//temp = temp.substring(0,1) + "\"cliente\":[" + temp.substring(1, temp.length()-1) + "]]";
		//System.out.println(temp);
		//System.out.println(listJSON.toJSONString());
		return listJSON.toJSONString();
	}
	
	public String inactivarCliente(int idCliente)
	{
		JSONArray listJSON = new JSONArray();
		boolean respuesta = ClienteDAO.inactivarCliente(idCliente);
		JSONObject Respuesta = new JSONObject();
		if(respuesta)
		{
			Respuesta.put("resultado", "EXITOSO");
		}else
		{
			Respuesta.put("resultado", "ERROR");
		}
		listJSON.add(Respuesta);
		return(listJSON.toString());
	}
	
	public String activarCliente(int idCliente)
	{
		JSONArray listJSON = new JSONArray();
		boolean respuesta = ClienteDAO.activarCliente(idCliente);
		JSONObject Respuesta = new JSONObject();
		if(respuesta)
		{
			Respuesta.put("resultado", "EXITOSO");
		}else
		{
			Respuesta.put("resultado", "ERROR");
		}
		listJSON.add(Respuesta);
		return(listJSON.toString());
	}
	
/**
 * 	
 * @param id Dado el id de un cliente se retorna la informaci�n del cliente
 * @return Se retorna en formato json la informaci�n del cliente que corresponde al id cliente, ingresado como par�metro.
 */
public String obtenerClienteporID(int id)
{
	Cliente clienteConsultado = ClienteDAO.obtenerClienteporID(id);
	JSONArray listJSON = new JSONArray();
	JSONObject Respuesta = new JSONObject();
	Respuesta.put("idcliente", clienteConsultado.getIdcliente());
	Respuesta.put("nombretienda", clienteConsultado.getTienda());
	Respuesta.put("nombrecliente", clienteConsultado.getNombres());
	Respuesta.put("direccion", clienteConsultado.getDireccion());
	Respuesta.put("zona", clienteConsultado.getZonaDireccion());
	Respuesta.put("observacion", clienteConsultado.getObservacion());
	Respuesta.put("telefono", clienteConsultado.getTelefono());
	Respuesta.put("nombremunicipio", clienteConsultado.getMunicipio());
	Respuesta.put("latitud", clienteConsultado.getLatitud());
	Respuesta.put("longitud", clienteConsultado.getLontitud());
	Respuesta.put("distanciatienda", clienteConsultado.getDistanciaTienda());
	Respuesta.put("idtienda", clienteConsultado.getIdtienda());
	listJSON.add(Respuesta);
	return(listJSON.toJSONString());
}


/**
 * 	Este m�todo tiene como objetivo tomar los par�metros de un cliente para realizar la inserci�n
 * @param telefono par�metro de ingreso para la creaci�n de cliente
 * @param nombres par�metro de ingreso para la creaci�n de cliente
 * @param direccion par�metro de ingreso para la creaci�n de cliente
 * @param zona par�metro de ingreso para la creaci�n de cliente 
 * @param observacion par�metro de ingreso para la creaci�n de cliente
 * @param tienda par�metro de ingreso para la creaci�n de cliente
 * @return Se retorna en formato Json el id del cliente sea ingresado o actualizado.
 */
public String InsertarClientePedido(String telefono, String nombres, String direccion,  String zona,  String observacion, String tienda)
{
	//Validar si el cliente ya existe en la base de datos
	//Llamamos el m�todo ya existente para saber si el cliente con el tel�fono ya existe
	// Esta pendiente convertir el nombre tienda a tienda
	String retorno = "false";
	int idTienda = TiendaDAO.obteneridTienda(tienda);
	
	Cliente clienteInsertar = new Cliente(telefono, nombres, direccion, zona,  observacion,  tienda, idTienda);
	ArrayList<Cliente> clientes = ClienteDAO.obtenerCliente(telefono);
	boolean clienteTiendaExiste = false;
	int idRespuestaCreacion = 0;
	int idRespuestaActualizacion = 0;
	if (clientes.size() > 0)
	{
		for (Cliente cliente : clientes) 
		{
			
			if (cliente.getTienda().equals(clienteInsertar.getTienda()))
			{
				clienteTiendaExiste = true;
				idRespuestaActualizacion = ClienteDAO.actualizarCliente(clienteInsertar);
				break;
			}
			
		}
		if (clienteTiendaExiste == false)
		{
			idRespuestaCreacion = ClienteDAO.insertarCliente(clienteInsertar);
		}
	}
	else
	{
		//Deberemos insertar el cliente en la base de datos
		idRespuestaCreacion = ClienteDAO.insertarCliente(clienteInsertar);
	}
	if (idRespuestaActualizacion > 0 || idRespuestaCreacion > 0 )
	{
		retorno = "true";
	}
	JSONArray listJSON = new JSONArray();
	JSONObject Respuesta = new JSONObject();
	Respuesta.put("resultado", retorno);
	if (idRespuestaActualizacion > 0)
	{
		Respuesta.put("idcliente", idRespuestaActualizacion);
	}else
	{
		if (idRespuestaCreacion > 0)
		{
			Respuesta.put("idcliente", idRespuestaCreacion);
		}
	}
	listJSON.add(Respuesta);
	return(listJSON.toJSONString());
}

/**
 * Este m�todo recibe la informacion de un cliente y de acuerdo a los diferentes condiciones, actualiza o crea el cliente en la base de datos
 * @param idCliente 
 * @param telefono
 * @param nombres
 * @param apellidos
 * @param nombreCompania
 * @param direccion
 * @param municipio
 * @param latitud
 * @param longitud
 * @param zona
 * @param observacion
 * @param tienda
 * @param memcode
 * @return Retorna el idcliente ingresado o actualizado seg�n los datos recibidos como par�metro.
 */
public int InsertarClientePedidoEncabezado(int idCliente,String telefono, String nombres, String apellidos, String nombreCompania, String direccion, String municipio, float latitud, float longitud, double distanciaTienda, String zona,  String observacion, String tienda, int memcode, int idnomenclatura, String numNomenclatura, String numNomenclatura2, String num3 )
{
	//Validar si el cliente ya existe en la base de datos
	//Llamamos el m�todo ya existente para saber si el cliente con el tel�fono ya existe
	// Esta pendiente convertir el nombre tienda a tienda
	int idTienda = TiendaDAO.obteneridTienda(tienda);
	int idMunicipio = MunicipioDAO.obteneridMunicipio(municipio);
	// Se crea el objeto cliente con todas las caracter�sticas enviadas
	Cliente clienteRevisar = new Cliente(idCliente, telefono, nombres, apellidos, nombreCompania, direccion,municipio, idMunicipio, latitud, longitud, distanciaTienda,  zona,  observacion,  tienda, idTienda, memcode, idnomenclatura, numNomenclatura, numNomenclatura2, num3, "");
	// Se inician las variables para la iniciaci�n de la creaci�n o la actualizaci�n
	int idRespuestaCreacion = 0;
	int idRespuestaActualizacion = 0;
	if ((idCliente > 0) && (memcode > 0) )
	{
		idRespuestaActualizacion = ClienteDAO.actualizarCliente(clienteRevisar);
	}else if((idCliente == 0 ) && (memcode == 0))
	{
		idRespuestaCreacion = ClienteDAO.insertarCliente(clienteRevisar);
	}else if((idCliente > 0 ) && (memcode == 0))
	{
		idRespuestaActualizacion = ClienteDAO.actualizarCliente(clienteRevisar);
	}
		
	if (idRespuestaActualizacion > 0)
	{
		return(idRespuestaActualizacion);
	}else
	{
		if (idRespuestaCreacion > 0)
		{
			return(idRespuestaCreacion);
		}
	}
	
	return(0);
}


public String InsertarClientePedidoEncabezadoJSON(int idCliente,String telefono, String nombres, String apellidos, String nombreCompania, String direccion, String municipio, float latitud, float longitud, double distanciaTienda, String zona,  String observacion, String tienda, int memcode, int idnomenclatura, String numNomenclatura, String numNomenclatura2, String num3 )
{
	//Validar si el cliente ya existe en la base de datos
	//Llamamos el m�todo ya existente para saber si el cliente con el tel�fono ya existe
	// Esta pendiente convertir el nombre tienda a tienda
	int idTienda = TiendaDAO.obteneridTienda(tienda);
	int idMunicipio = MunicipioDAO.obteneridMunicipio(municipio);
	// Se crea el objeto cliente con todas las caracter�sticas enviadas
	Cliente clienteRevisar = new Cliente(idCliente, telefono, nombres, apellidos, nombreCompania, direccion,municipio, idMunicipio, latitud, longitud, distanciaTienda, zona,  observacion,  tienda, idTienda, memcode, idnomenclatura, numNomenclatura, numNomenclatura2, num3, "");
	// Se inician las variables para la iniciaci�n de la creaci�n o la actualizaci�n
	int idRespuestaCreacion = 0;
	int idRespuestaActualizacion = 0;
	int idClienteResp = 0;
	if ((idCliente > 0) && (memcode > 0) )
	{
		idRespuestaActualizacion = ClienteDAO.actualizarCliente(clienteRevisar);
	}else if((idCliente == 0 ) && (memcode == 0))
	{
		idRespuestaCreacion = ClienteDAO.insertarCliente(clienteRevisar);
	}else if((idCliente > 0 ) && (memcode == 0))
	{
		idRespuestaActualizacion = ClienteDAO.actualizarCliente(clienteRevisar);
	}
		
	if (idRespuestaActualizacion > 0)
	{
		idClienteResp = idRespuestaActualizacion;
	}else
	{
		if (idRespuestaCreacion > 0)
		{
			idClienteResp = idRespuestaCreacion;
		}
	}
	JSONArray listJSON = new JSONArray();
	JSONObject Respuesta = new JSONObject();
	Respuesta.put("idcliente", idClienteResp);
	listJSON.add(Respuesta);
	return(listJSON.toJSONString());
}



public String ObtenerClientesTienda(int idtienda)
{
	ArrayList <Cliente> clientesTienda = ClienteDAO.ObtenerClientesTienda(idtienda);
	JSONArray listJSON = new JSONArray();
	for(Cliente clienteConsultado: clientesTienda)
	{
		
		JSONObject Respuesta = new JSONObject();
		Respuesta.put("idcliente", clienteConsultado.getIdcliente());
		Respuesta.put("nombretienda", clienteConsultado.getTienda());
		Respuesta.put("nombrecliente", clienteConsultado.getNombres());
		Respuesta.put("direccion", clienteConsultado.getDireccion());
		Respuesta.put("zona", clienteConsultado.getZonaDireccion());
		Respuesta.put("observacion", clienteConsultado.getObservacion());
		Respuesta.put("telefono", clienteConsultado.getTelefono());
		Respuesta.put("nombremunicipio", clienteConsultado.getMunicipio());
		Respuesta.put("latitud", clienteConsultado.getLatitud());
		Respuesta.put("longitud", clienteConsultado.getLontitud());
		Respuesta.put("idtienda", clienteConsultado.getIdtienda());
		listJSON.add(Respuesta);
	}
	return(listJSON.toJSONString());
	
}

public String InsertarClienteGeolocalizado(int idcliente,  String direccion, String municipio, int idtiendaanterior, int idtiendaactual)
{
	JSONArray listJSON = new JSONArray();
	JSONObject Respuesta = new JSONObject();
	boolean resp = ClienteDAO.InsertarClienteGeolocalizado(idcliente,  direccion, municipio, idtiendaanterior, idtiendaactual);
	Respuesta.put("resultado", resp);
	listJSON.add(Respuesta);
	return(listJSON.toJSONString());
}

}
