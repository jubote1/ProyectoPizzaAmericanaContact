package capaControlador;
import capaModelo.Cliente;
import capaModelo.Tienda;
import capaDAO.ClienteDAO;
import capaDAO.MunicipioDAO;
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
	 * @param tel recibe el valor del teléfono para buscar al cliente en la base de datos y devolver en una estructura json
	 * las incripciones del cliente que corresponden al número telefónico entregado como parámetro.
	 * @return un valor String en formato json con el arreglo de inscripciones en la tabla cliente que responden al teléfono
	 * enviado como parámetro
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
			cadaViajeJSON.put("memcode", cliente.getMemcode());
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
 * @param id Dado el id de un cliente se retorna la información del cliente
 * @return Se retorna en formato json la información del cliente que corresponde al id cliente, ingresado como parámetro.
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
	Respuesta.put("idtienda", clienteConsultado.getIdtienda());
	listJSON.add(Respuesta);
	return(listJSON.toJSONString());
}


/**
 * 	Este método tiene como objetivo tomar los parámetros de un cliente para realizar la inserción
 * @param telefono parámetro de ingreso para la creación de cliente
 * @param nombres parámetro de ingreso para la creación de cliente
 * @param direccion parámetro de ingreso para la creación de cliente
 * @param zona parámetro de ingreso para la creación de cliente 
 * @param observacion parámetro de ingreso para la creación de cliente
 * @param tienda parámetro de ingreso para la creación de cliente
 * @return Se retorna en formato Json el id del cliente sea ingresado o actualizado.
 */
public String InsertarClientePedido(String telefono, String nombres, String direccion,  String zona,  String observacion, String tienda)
{
	//Validar si el cliente ya existe en la base de datos
	//Llamamos el mï¿½todo ya existente para saber si el cliente con el telï¿½fono ya existe
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
 * Este método recibe la informacion de un cliente y de acuerdo a los diferentes condiciones, actualiza o crea el cliente en la base de datos
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
 * @return Retorna el idcliente ingresado o actualizado según los datos recibidos como parámetro.
 */
public int InsertarClientePedidoEncabezado(int idCliente,String telefono, String nombres, String apellidos, String nombreCompania, String direccion, String municipio, float latitud, float longitud, String zona,  String observacion, String tienda, int memcode)
{
	//Validar si el cliente ya existe en la base de datos
	//Llamamos el mï¿½todo ya existente para saber si el cliente con el telï¿½fono ya existe
	// Esta pendiente convertir el nombre tienda a tienda
	int idTienda = TiendaDAO.obteneridTienda(tienda);
	System.out.println("idtienda " + idTienda);
	int idMunicipio = MunicipioDAO.obteneridMunicipio(municipio);
	Cliente clienteRevisar = new Cliente(idCliente, telefono, nombres, apellidos, nombreCompania, direccion,municipio, idMunicipio, latitud, longitud, zona,  observacion,  tienda, idTienda, memcode);
	int idRespuestaCreacion = 0;
	int idRespuestaActualizacion = 0;
	System.out.println("idCliente " + idCliente + " memcode " + memcode);
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

}
