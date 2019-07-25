package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.MunicipioDAO;
import capaDAO.OrigenPqrsDAO;
import capaDAO.PedidoDAO;
import capaDAO.SolicitudPQRSDAO;
import capaDAO.TiendaDAO;
import capaDAO.GeneralDAO;
import capaModelo.Correo;
import capaModelo.OrigenPqrs;
import capaModelo.Pedido;
import capaModelo.SolicitudPQRS;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;;


public class SolicitudPQRSCtrl {
	
	/**
	 * Método de la capa controladora que recibe los parámetros de la entidad SolicitudPQRS se encarga de crear el objeto correspondiente
	 * e invoca la capa DAO para la inserción de la entidad.
	 * @param fechaSolicitud
	 * @param tipoSolicitud
	 * @param idcliente
	 * @param idtienda
	 * @param nombres
	 * @param apellidos
	 * @param telefono
	 * @param direccion
	 * @param zona
	 * @param idmunicipio
	 * @param comentario
	 * @return Retorna un entero con el idSolicitudPQRS que retorna la base de datos en la creación del mismo.
	 */
	public String insertarSolicitudPQRS(String fechaSolicitud, String tipoSolicitud, int idcliente, int idtienda,
			String nombres, String apellidos, String telefono, String direccion, String zona, int idmunicipio,
			String comentario, int idOrigen)
	{
		JSONArray listJSON = new JSONArray();
		SolicitudPQRS solicitud = new SolicitudPQRS(0,fechaSolicitud, tipoSolicitud,idcliente, idtienda,
			 nombres, apellidos,  telefono,  direccion,  zona, idmunicipio,
			comentario, idOrigen);
		
		Tienda objTienda = TiendaDAO.retornarTienda(idtienda);
		String nombreTienda = objTienda.getNombreTienda();
		int idSolPQRSIns = SolicitudPQRSDAO.insertarSolicitudPQRS(solicitud);
		// Se realiza envío del correo con la solicitud
		Correo correo = new Correo();
		correo.setAsunto("SE REGISTRO PQRS # " + idSolPQRSIns);
		ArrayList correos = GeneralDAO.obtenerCorreosParametro("REGISTROPQRS");
		correo.setContrasena("Pizzaamericana2017");
		correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
		correo.setMensaje("Se registro PQRS para el cliente " + nombres + " " + apellidos + " para la tienda " + nombreTienda + " con el siguiente comentario: " + comentario + " \n Información Adicional del cliente telefono: " + telefono + " Direccion: " + direccion + ". \n Si desea más información favor revisar en el sistema de Contact Center en el apartado de PQRS" );
		ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
		contro.enviarCorreo();
		//
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idSolicitudPQRS", idSolPQRSIns);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public String ConsultaIntegradaSolicitudesPQRS(String fechainicial, String fechafinal, String tienda)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList <SolicitudPQRS> consultaSolicitudes = SolicitudPQRSDAO.ConsultaIntegradaSolicitudesPQRS(fechainicial, fechafinal, tienda);
		for (SolicitudPQRS cadaSolicitud: consultaSolicitudes){
			JSONObject cadaSolicitudJSON = new JSONObject();
			cadaSolicitudJSON.put("idconsultaPQRS", cadaSolicitud.getIdsolicitud());
			cadaSolicitudJSON.put("fechasolicitud", cadaSolicitud.getFechaSolicitud());
			cadaSolicitudJSON.put("tiposolicitud",cadaSolicitud.getTipoSolicitud());
			cadaSolicitudJSON.put("cliente", cadaSolicitud.getNombres() + " " + cadaSolicitud.getApellidos());
			cadaSolicitudJSON.put("direccion", cadaSolicitud.getDireccion());
			cadaSolicitudJSON.put("telefono", cadaSolicitud.getTelefono());
			cadaSolicitudJSON.put("comentario", cadaSolicitud.getComentario());
			Tienda tie = TiendaDAO.retornarTienda(cadaSolicitud.getIdtienda());
			String muni = MunicipioDAO.obtenerMunicipio(cadaSolicitud.getIdmunicipio());
			cadaSolicitudJSON.put("municipio", muni);
			cadaSolicitudJSON.put("tienda", tie.getNombreTienda());
			cadaSolicitudJSON.put("idorigen", cadaSolicitud.getIdOrigen());
			cadaSolicitudJSON.put("nombreorigen", cadaSolicitud.getOrigen());
			listJSON.add(cadaSolicitudJSON);
		}
		return listJSON.toJSONString();
	}
	
	public String validarPQRS(int PQRS, int idCliente)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject respuestaJSON = new JSONObject();
		boolean respuesta = SolicitudPQRSDAO.validarPQRS(PQRS, idCliente);
		if(respuesta)
		{
			respuestaJSON.put("resultado", "OK");
		}else
		{
			respuestaJSON.put("resultado", "NOK");
		}
		listJSON.add(respuestaJSON);
		return(listJSON.toJSONString());
	}
	
	public String adicionarComentarioPQRS(int idSolicitudPQRS, String comentario)
	{
		boolean respuesta = SolicitudPQRSDAO.adicionarComentarioPQRS(idSolicitudPQRS, comentario);
		JSONArray listJSON = new JSONArray();
		JSONObject respuestaJSON = new JSONObject();
		if(respuesta)
		{
			respuestaJSON.put("resultado", "OK");
		}else
		{
			respuestaJSON.put("resultado", "NOK");
		}
		Correo correo = new Correo();
		correo.setAsunto("SE ACTUALIZÓ REGISTRO PQRS # " + idSolicitudPQRS);
		ArrayList correos = GeneralDAO.obtenerCorreosParametro("REGISTROPQRS");
		correo.setContrasena("Pizzaamericana2017");
		correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
		correo.setMensaje("Se Actualizó PQRS con el siguiente comentario: " + comentario + ". \n Si desea más información favor revisar en el sistema de Contact Center en el apartado de PQRS" );
		ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
		contro.enviarCorreo();
		
		listJSON.add(respuestaJSON);
		return(listJSON.toJSONString());
	}
	
	//ESPACIO RESERVADO PARA TODO LO RELACIONADO CON ORIGEN_PQRS
	
	public String retornarOrigenesPqrs()
	{
		JSONArray listJSON = new JSONArray();
		ArrayList <OrigenPqrs> origenes = OrigenPqrsDAO.retornarOrigenesPqrs();
		for (OrigenPqrs cadaOrigen: origenes){
			JSONObject cadaOrigenJSON = new JSONObject();
			cadaOrigenJSON.put("idorigen", cadaOrigen.getIdOrigen());
			cadaOrigenJSON.put("nombreorigen", cadaOrigen.getNombreOrigen());
			listJSON.add(cadaOrigenJSON);
		}
		return listJSON.toJSONString();
	}

}
