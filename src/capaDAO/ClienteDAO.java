package capaDAO;

import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Cliente;
import capaModelo.Tienda;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
/**
 * Clase que se encarga de todo lo relacionado con clientes y la interacción con la base de datos
 * @author JuanDavid
 *
 */
public class ClienteDAO {
	
	/**
	 * 
	 * @param tel Dado el telefóno de un cliente se encarga de retornar en un array list de objetos tipo liente
	 * la información de los registros que coincidente con dicho teléfono.
	 * @return ArrayList de tipo cliente con la información de clientes que coinciden con el teléfono dado.
	 */
	public static ArrayList<Cliente> obtenerCliente(String tel)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Cliente> clientes = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idcliente, b.nombre nombreTienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode from cliente a,tienda b, municipio c where a.idtienda = b.idtienda and a.idmunicipio = c.idmunicipio and a.telefono = '" + tel +"'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idcliente;
			String nombreTienda;
			String nombreCliente;
			String apellido;
			String nombreCompania;
			String direccion;
			String zona;
			String observacion;
			String telefono;
			String municipio;
			float latitud;
			float longitud;
			int idTienda;
			int memcode;
			while(rs.next()){
				idcliente = rs.getInt("idcliente");
				nombreTienda = rs.getString("nombreTienda");
				nombreCliente = rs.getString("nombre");
				apellido = rs.getString("apellido");
				nombreCompania = rs.getString("nombrecompania");
				direccion = rs.getString("direccion");
				zona = rs.getString("zona");
				observacion = rs.getString("observacion");
				telefono = rs.getString("telefono");
				municipio = rs.getString("nombremunicipio");
				latitud = rs.getFloat("latitud");
				longitud = rs.getFloat("longitud");
				idTienda = rs.getInt("idtienda");
				memcode = rs.getInt("memcode");
				Cliente clien = new Cliente( idcliente, telefono, nombreCliente,apellido, nombreCompania, direccion,municipio,latitud, longitud, zona, observacion, nombreTienda, idTienda, memcode);
				clientes.add(clien);
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
		return(clientes);
		
	}
	
	/**
	 * Método que se encarga de insertar en la base de datos un cliente
	 * @param clienteInsertar Se recibe como parámetro un objeto Modelo Cliente con base en el cual se inserta el cliente.
	 * @return  Se retorna un int con el valor del idcliente insertado en la base de datos.
	 */
	public static int insertarCliente(Cliente clienteInsertar)
	{
		Logger logger = Logger.getLogger("log_file");
		int idClienteInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into cliente (idtienda,nombre, apellido, nombrecompania, direccion, zona, telefono, observacion, idmunicipio, latitud, longitud) values (" + clienteInsertar.getIdtienda() + ", '" +clienteInsertar.getNombres() + "' , '" + clienteInsertar.getApellidos() + "' , '" + clienteInsertar.getNombreCompania() + "' , '" + clienteInsertar.getDireccion() + "' , '" + clienteInsertar.getZonaDireccion() +"' , '" + clienteInsertar.getTelefono() + "' , '" + clienteInsertar.getObservacion() + "' , " + clienteInsertar.getIdMunicipio() + " , " + clienteInsertar.getLatitud() + " , " + clienteInsertar.getLontitud() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idClienteInsertado=rs.getInt(1);
				
	        }
			stm.close();
			rs.close();
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
		return(idClienteInsertado);
	}
	
	/**
	 * Método que se encarga de retonar un objeto Modelo CLiente, con base en un id que envía como parámetro.
	 * @param id Se recibe un id cliente y con base en este se busca en base de datos.
	 * @return Se retorna un objeto Modelo Cliente con la información del cliente.
	 */
	public static Cliente obtenerClienteporID(int id)
	{
		Logger logger = Logger.getLogger("log_file");
		Cliente clienteConsultado = new Cliente(); 
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idcliente, b.nombre nombreTienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode from cliente a,tienda b, municipio c where a.idtienda = b.idtienda and a.idmunicipio = c.idmunicipio and a.idcliente = " + id +"";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idcliente;
			String nombreTienda;
			String nombreCliente;
			String apellido;
			String nombreCompania;
			String direccion;
			String zona;
			String observacion;
			String telefono;
			String municipio;
			float latitud;
			float longitud;
			int idTienda;
			int memcode;
			while(rs.next()){
				idcliente = rs.getInt("idcliente");
				nombreTienda = rs.getString("nombreTienda");
				nombreCliente = rs.getString("nombre");
				apellido = rs.getString("apellido");
				nombreCompania = rs.getString("nombrecompania");
				direccion = rs.getString("direccion");
				zona = rs.getString("zona");
				observacion = rs.getString("observacion");
				telefono = rs.getString("telefono");
				municipio = rs.getString("nombremunicipio");
				latitud = rs.getFloat("latitud");
				longitud = rs.getFloat("longitud");
				idTienda = rs.getInt("idtienda");
				memcode = rs.getInt("memcode");
				clienteConsultado = new Cliente( idcliente, telefono, nombreCliente, apellido, nombreCompania, direccion,municipio,latitud, longitud, zona, observacion, nombreTienda, idTienda,memcode);
				
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
		return(clienteConsultado);
		
	}
	
	/**
	 * Método que busca actualizar la información de un cliente con base en un objeto Modelo Cliente enviado como parámetro.
	 * @param clienteAct Se envía como parámetro un tipo de Modelo Cliente con base en el cual se hace la actualización.
	 * @return Se retorna un valor entero con el valor del id cliente actualizado en el sistema.
	 */
	public static int actualizarCliente(Cliente clienteAct)
	{
		Logger logger = Logger.getLogger("log_file");
		int idClienteActualizado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			//Para actualizar el cliente el idcliente debe ser diferente de vacï¿½o.
			Statement stm = con1.createStatement();
			if(clienteAct.getIdcliente() > 0)
			{
				String update = "update cliente set nombre = '" + clienteAct.getNombres() + "' , direccion = '" + clienteAct.getDireccion() + "' , idmunicipio = " + clienteAct.getIdMunicipio() + " , latitud = " + clienteAct.getLatitud() + " , longitud = " + clienteAct.getLontitud() + " , zona = '" + clienteAct.getZonaDireccion() + "' , observacion = '" + clienteAct.getObservacion() +"', apellido = '" + clienteAct.getApellidos() + "' , nombrecompania = '" + clienteAct.getNombreCompania() + "'  where idcliente = " + clienteAct.getIdcliente(); 
				logger.info(update);
				stm.executeUpdate(update);
				idClienteActualizado = clienteAct.getIdcliente();
			}else
			{
				logger.info("No se pudo hacer actualizaciï¿½n dado que el idCliente venia en ceros o vacï¿½o");
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
		logger.info("id cliente actualizado" + idClienteActualizado);
		return(idClienteActualizado);
	}
	
	/**
	 * El campo memcode corresponde al consecutivo cliente que tiene en la base de datos de la tienda, como la inserción
	 * es asincrona, se requiere que luego de insertado el cliente en nuestro sistema y luego de insertado el pedido en la tienda
	 * donde el cliente nuevo tiene un memcode, venimos y actualizamos el memcode en nuestro sistema.
	 * @param idCliente Se recibe como parámetro el idcliente al cual se le va a actualizar el memcode.
	 * @param memcode Valor de memcode a actualizar.
	 * @return Se retorna un entero con el idcliente actualizado.
	 */
	public static int actualizarClienteMemcode(int idCliente, int memcode)
	{
		Logger logger = Logger.getLogger("log_file");
		int idClienteActualizado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			//Para actualizar el cliente el idcliente debe ser diferente de vacï¿½o.
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
	

}
