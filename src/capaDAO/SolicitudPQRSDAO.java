package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import capaModelo.SolicitudPQRS;
import capaModelo.Tienda;

import org.apache.log4j.Logger;

import capaModelo.FormaPago;
import capaModelo.Pedido;
import conexion.ConexionBaseDatos;

public class SolicitudPQRSDAO {
	
	/**
	 * Método de la capa DAO que se encarga de implementar la inserción de la entidad solicitudPQRS, recibiendo como parámetro
	 * un objeto tipo SolicitudPQRS, retornará como resultado de la inserción el idsolicitudPQRS asignado por la base de datos
	 * en base a un campo configurado como autoincrementable en la misma.
	 * @param solicitud Se recibe como parámetro un objeto de la capaModelo SolicitudPQRS
	 * @return Se retorna valor intero con el idSolicitudPQRS asignado por la base de datos.
	 */
	public static int insertarSolicitudPQRS(SolicitudPQRS solicitud)
	{
		Logger logger = Logger.getLogger("log_file");
		int idSolicitudPQRSIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Date fechaTemporal = new Date();
		DateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		String fechaSolicitudFinal ="";
		try
		{
			fechaTemporal = new SimpleDateFormat("dd/MM/yyyy").parse(solicitud.getFechaSolicitud());
			fechaSolicitudFinal  = formatoFinal.format(fechaTemporal);
			
		}catch(Exception e){
			logger.error(e.toString());
			return(0);
		}
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into solicitudPQRS (fechasolicitud,tiposolicitud,idcliente, idtienda, nombres, apellidos, telefono, direccion, zona, idmunicipio, comentario) values ('" + fechaSolicitudFinal + "', '" + solicitud.getTipoSolicitud() + "', " + solicitud.getIdcliente() + " , " + solicitud.getIdtienda() + " , '"+ solicitud.getNombres() + "' , '"+ solicitud.getApellidos() + "' , '" + solicitud.getTelefono() + "' , '" + solicitud.getDireccion() + "' , '" + solicitud.getZona() + "' , " + solicitud.getIdmunicipio() + " , '" + solicitud.getComentario() + "')" ; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idSolicitudPQRSIns =rs.getInt(1);
				logger.info("Id SolicitudPQRS insertada en bd " + idSolicitudPQRSIns);
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
		return(idSolicitudPQRSIns);
	}
	
	
	public static ArrayList<SolicitudPQRS> ConsultaIntegradaSolicitudesPQRS(String fechainicial, String fechafinal, String tienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList <SolicitudPQRS> consultaSolicitudes = new ArrayList();
		String consulta = "";
		String fechaini = fechainicial.substring(6, 10)+"-"+fechainicial.substring(3, 5)+"-"+fechainicial.substring(0, 2);	
		String fechafin = fechafinal.substring(6, 10)+"-"+fechafinal.substring(3, 5)+"-"+fechafinal.substring(0, 2);	
		if((fechainicial.length()>0) && (fechafinal.length()>0) && (tienda.length()>0))
		{
			if (tienda.equals("TODAS"))
			{
				consulta = "select idsolicitudPQRS, fechasolicitud, tiposolicitud,nombres,apellidos, direccion , telefono,comentario from solicitudPQRS where fechasolicitud >=  '" + fechaini +"' and fechasolicitud <= '"+ fechafin +"'" ;
			}else
			{
				consulta = "select idsolicitudPQRS, fechasolicitud, tiposolicitud,nombres,apellidos, direccion , telefono,comentario from solicitudPQRS where fechasolicitud >=  '" + fechaini +"' and fechasolicitud <= '"+ fechafin +"' and idtienda = " + tienda;
			}
		}
		logger.info(consulta);
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(consulta);
			int idsolicitudpqrs = 0;
			String fechasolicitud = "";
			String tiposolicitud = "";
			String nombres = "";
			String apellidos = "";
			String direccion = "";
			String telefono = "";
			String comentario = "";
			while(rs.next())
			{
				idsolicitudpqrs = rs.getInt("idsolicitudPQRS");
				fechasolicitud = rs.getString("fechasolicitud");
				tiposolicitud = rs.getString("tiposolicitud");
				nombres = rs.getString("nombres");
				apellidos = rs.getString("apellidos");
				direccion = rs.getString("direccion");
				telefono = rs.getString("telefono");
				comentario = rs.getString("comentario");
				SolicitudPQRS cadaSolicitud = new SolicitudPQRS(idsolicitudpqrs, fechasolicitud, tiposolicitud, 0, 0,
						nombres, apellidos, telefono, direccion, "", 0,
						comentario);
				consultaSolicitudes.add(cadaSolicitud);
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
		return(consultaSolicitudes);
	}

}
