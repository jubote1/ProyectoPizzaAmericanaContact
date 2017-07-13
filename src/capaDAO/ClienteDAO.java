package capaDAO;

import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Cliente;
import capaModelo.Tienda;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
public class ClienteDAO {
	
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
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(clientes);
		
	}
	
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
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		return(idClienteInsertado);
	}
	
	
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
		}catch (Exception e){
			logger.error(e.toString());
		}
		return(clienteConsultado);
		
	}
	
	public static int actualizarCliente(Cliente clienteAct)
	{
		Logger logger = Logger.getLogger("log_file");
		int idClienteActualizado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			//Para actualizar el cliente el idcliente debe ser diferente de vac�o.
			Statement stm = con1.createStatement();
			if(clienteAct.getIdcliente() > 0)
			{
				String update = "update cliente set nombre = '" + clienteAct.getNombres() + "' , direccion = '" + clienteAct.getDireccion() + "' , idmunicipio = " + clienteAct.getIdMunicipio() + " , latitud = " + clienteAct.getLatitud() + " , longitud = " + clienteAct.getLontitud() + " , zona = '" + clienteAct.getZonaDireccion() + "' , observacion = '" + clienteAct.getObservacion() +"', apellido = '" + clienteAct.getApellidos() + "' , nombrecompania = '" + clienteAct.getNombreCompania() + "'  where idcliente = " + clienteAct.getIdcliente(); 
				logger.info(update);
				stm.executeUpdate(update);
				idClienteActualizado = clienteAct.getIdcliente();
			}else
			{
				logger.info("No se pudo hacer actualizaci�n dado que el idCliente venia en ceros o vac�o");
			}
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			return(0);
		}
		logger.info("id cliente actualizado" + idClienteActualizado);
		return(idClienteActualizado);
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
			return(0);
		}
		logger.info("id cliente actualizado" + idClienteActualizado);
		return(idClienteActualizado);
	}
	

}
