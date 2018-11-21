package capaControlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.OfertaClienteDAO;
import capaDAO.OfertaDAO;
import capaModelo.ExcepcionPrecio;
import capaModelo.Oferta;
import capaModelo.OfertaCliente;
import conexion.ConexionBaseDatos;

public class PromocionesCtrl {
	
	//ENTIDAD OFERTAS CLIENTE
	
	public String obtenerOfertasClienteGrid(int idCliente)
	{
		ArrayList<OfertaCliente> ofertas = OfertaClienteDAO.obtenerOfertasClienteGrid(idCliente);
		JSONArray listJSON = new JSONArray();
		for (OfertaCliente ofer : ofertas) 
		{
			JSONObject cadaOfertaJSON = new JSONObject();
			cadaOfertaJSON.put("idofertacliente", ofer.getIdOfertaCliente());
			cadaOfertaJSON.put("idcliente", ofer.getIdCliente());
			cadaOfertaJSON.put("idoferta", ofer.getIdOferta());
			cadaOfertaJSON.put("nombreoferta", ofer.getNombreOferta());
			cadaOfertaJSON.put("utilizada", ofer.getUtilizada());
			cadaOfertaJSON.put("ingresooferta", ofer.getIngresoOferta());
			cadaOfertaJSON.put("usooferta", ofer.getUsoOferta());
			cadaOfertaJSON.put("observacion", ofer.getObservacion());
			listJSON.add(cadaOfertaJSON);
		}
		return(listJSON.toJSONString());
	}
	
	public  String obtenerOfertasVigenteCliente(int idCliente)
	{
		ArrayList<OfertaCliente> ofertas = OfertaClienteDAO.obtenerOfertasVigenteCliente(idCliente);
		JSONArray listJSON = new JSONArray();
		for (OfertaCliente ofer : ofertas) 
		{
			JSONObject cadaOfertaJSON = new JSONObject();
			cadaOfertaJSON.put("idofertacliente", ofer.getIdOfertaCliente());
			cadaOfertaJSON.put("idcliente", ofer.getIdCliente());
			cadaOfertaJSON.put("idoferta", ofer.getIdOferta());
			cadaOfertaJSON.put("nombreoferta", ofer.getNombreOferta());
			cadaOfertaJSON.put("utilizada", ofer.getUtilizada());
			cadaOfertaJSON.put("ingresooferta", ofer.getIngresoOferta());
			cadaOfertaJSON.put("usooferta", ofer.getUsoOferta());
			cadaOfertaJSON.put("observacion", ofer.getObservacion());
			listJSON.add(cadaOfertaJSON);
		}
		return(listJSON.toJSONString());
	}
	
	public String insertarOfertaCliente(OfertaCliente ofer)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject ResultadoJSON = new JSONObject();
		int respuesta = OfertaClienteDAO.insertarOfertaCliente(ofer);
		ResultadoJSON.put("idofertacliente", respuesta);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}

	public String eliminarOfertaCliente(int idOfertaCliente)
	{
		JSONArray listJSON = new JSONArray();
		OfertaClienteDAO.eliminarOfertaCliente(idOfertaCliente);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}

	public  String retornarOfertaCliente(int idOfertaCliente)
	{
		
		JSONArray listJSON = new JSONArray();
		OfertaCliente oferCliente = OfertaClienteDAO.retornarOfertaCliente(idOfertaCliente);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idofertacliente", oferCliente.getIdOfertaCliente());
		ResultadoJSON.put("idcliente", oferCliente.getIdCliente());
		ResultadoJSON.put("idoferta", oferCliente.getIdOferta());
		ResultadoJSON.put("nombreoferta", oferCliente.getNombreOferta());
		ResultadoJSON.put("utilizada", oferCliente.getUtilizada());
		ResultadoJSON.put("ingresooferta", oferCliente.getIngresoOferta());
		ResultadoJSON.put("usooferta", oferCliente.getUsoOferta());
		ResultadoJSON.put("observacion", oferCliente.getObservacion());
		listJSON.add(ResultadoJSON);
		return(listJSON.toJSONString());
	}

	public String actualizarUsoOferta(int idOfertaCliente)
	{
		JSONArray listJSON = new JSONArray();
		String respuesta = OfertaClienteDAO.actualizarUsoOferta(idOfertaCliente);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	//ENTIDAD OFERTA
	
	public String obtenerOfertas()
	{
		ArrayList<Oferta> ofertas = OfertaDAO.obtenerOfertas();
		JSONArray listJSON = new JSONArray();
		for (Oferta ofer : ofertas) 
		{
			JSONObject cadaOfertaJSON = new JSONObject();
			cadaOfertaJSON.put("idoferta", ofer.getIdOferta());
			cadaOfertaJSON.put("nombreoferta", ofer.getNombreOferta());
			cadaOfertaJSON.put("idexcepcion", ofer.getIdExcepcion());
			listJSON.add(cadaOfertaJSON);
		}
		return(listJSON.toJSONString());
		
	}
	
	public  String obtenerOfertasGrid()
	{
		ArrayList<Oferta> ofertas = OfertaDAO.obtenerOfertasGrid();
		JSONArray listJSON = new JSONArray();
		for (Oferta ofer : ofertas) 
		{
			JSONObject cadaOfertaJSON = new JSONObject();
			cadaOfertaJSON.put("idoferta", ofer.getIdOferta());
			cadaOfertaJSON.put("nombreoferta", ofer.getNombreOferta());
			cadaOfertaJSON.put("idexcepcion", ofer.getIdExcepcion());
			cadaOfertaJSON.put("nombreexcepcion", ofer.getNombreExcepcion());
			listJSON.add(cadaOfertaJSON);
		}
		return(listJSON.toJSONString());
	}
	
	public String insertarOferta(Oferta ofer)
	{
		JSONArray listJSON = new JSONArray();
		int respuesta = OfertaDAO.insertarOferta(ofer);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idoferta", respuesta);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}

	public String eliminarOferta(int idOferta)
	{
		JSONArray listJSON = new JSONArray();
		OfertaDAO.eliminarOferta(idOferta);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}

	public String retornarOferta(int idOferta)
	{
		JSONArray listJSON = new JSONArray();
		Oferta oferta = OfertaDAO.retornarOferta(idOferta);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("idoferta", oferta.getIdOferta());
		ResultadoJSON.put("nombreoferta", oferta.getNombreOferta());
		ResultadoJSON.put("idexcepcion", oferta.getIdExcepcion());
		listJSON.add(ResultadoJSON);
		return(listJSON.toJSONString());
	}

	public String editarOferta(Oferta ofertaEdi)
	{
		JSONArray listJSON = new JSONArray();
		String resultado = OfertaDAO.editarOferta(ofertaEdi);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", resultado);
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	

}
