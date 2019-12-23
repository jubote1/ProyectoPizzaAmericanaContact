package capaControlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.OfertaClienteDAO;
import capaDAO.OfertaDAO;
import capaDAO.ParametrosDAO;
import capaModelo.ExcepcionPrecio;
import capaModelo.MensajeTexto;
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
			cadaOfertaJSON.put("pqrs", ofer.getPQRS());
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
			cadaOfertaJSON.put("pqrs", ofer.getPQRS());
			listJSON.add(cadaOfertaJSON);
		}
		return(listJSON.toJSONString());
	}
	
	/**
	 * Este método debe realizar validaciones para mirar si la oferta maneja codigo promocional, en cuyo caso deberá asignar una
	 * @param ofer
	 * @return
	 */
	public String insertarOfertaCliente(OfertaCliente ofer)
	{
		//Validamos si la oferta maneja código promocional
		boolean manejaCodigo = OfertaDAO.manejaCodigoOferta(ofer.getIdOferta());
		String codigoPromocional = "";
		if(manejaCodigo)
		{
			codigoPromocional = generarCodigoPromocional();
		}
		//Fijamos el valor de codigo promocional que puede ser vacío o contener valor
		ofer.setCodigoPromocion(codigoPromocional);
		//Validamos si la oferta maneja caducidad y como la maneja
		Oferta condicionesOferta = OfertaDAO.retornarOferta(ofer.getIdOferta());
		//Validamos si la oferta tiene caducidad en caso afirmativo
		if(condicionesOferta.getDiasCaducidad() > 0)
		{
			//Validamos el tipo de caducidad de la oferta si es general o particular (por el momento la general no está implementada)
			if(condicionesOferta.getTipoCaducidad().equals(new String("P")))
			{
				//Creamos el calendario y le sumamos a la fecha actual los días de caducidad
				Calendar calendarioActual = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				calendarioActual.add(Calendar.DAY_OF_YEAR, condicionesOferta.getDiasCaducidad());
				//Extraemos la fecha caducidad
				Date datFechaCaducidad = calendarioActual.getTime();
				String fechaCaducidad = dateFormat.format(datFechaCaducidad);
				ofer.setFechaCaducidad(fechaCaducidad);
			}
		}
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
		ResultadoJSON.put("pqrs", oferCliente.getPQRS());
		listJSON.add(ResultadoJSON);
		return(listJSON.toJSONString());
	}

	public String actualizarUsoOferta(int idOfertaCliente, String usuarioUso)
	{
		JSONArray listJSON = new JSONArray();
		String respuesta = OfertaClienteDAO.actualizarUsoOferta(idOfertaCliente, usuarioUso);
		JSONObject ResultadoJSON = new JSONObject();
		ResultadoJSON.put("resultado", "exitoso");
		listJSON.add(ResultadoJSON);
		return listJSON.toJSONString();
	}
	
	public ArrayList<OfertaCliente> obtenerOfertasNuevasSemana(String fechaSuperior, String fechaInferior)
	{
		ArrayList<OfertaCliente> ofertasNuevasSemana =  OfertaClienteDAO.obtenerOfertasNuevasSemana(fechaSuperior, fechaInferior);
		return(ofertasNuevasSemana);
	}
	
	public ArrayList<OfertaCliente> obtenerOfertasRedimidasSemana(String fechaSuperior, String fechaInferior)
	{
		ArrayList<OfertaCliente> ofertasRedSemana =  OfertaClienteDAO.obtenerOfertasRedimidasSemana(fechaSuperior, fechaInferior);
		return(ofertasRedSemana );
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
	
	
/**Método que iterativamente se va a encargar de generar el código de promoción lo validará y lo retornará una vez encuentre que si puede ser generado
 * A hoy el método se genera con unas características
 * @return
 */
	public String generarCodigoPromocional()
	{
		String codigo = "";
		//variable para controlar que si hubo de generación de código único
		boolean bandera = true;
		while(bandera)
		{
			int a;
			 for (int i = 0; i < 7; i++) 
			 {
			        if (i < 4) {    // 0,1,2,3 posiciones de numeros
			            codigo = (int) (Math.random() * 9) + "" + codigo;

			        } else {       // 4,5,6 posiciones de letras
			            do {
			                a = (int) (Math.random() * 26 + 65);///
			            } while (a == 65 || a == 69 || a == 73 || a == 79 || a == 85);

			            char letra = (char) a;
			            if (i == 4) {
			                codigo = codigo  + letra;
			            } else {
			                codigo = codigo + "" + letra;
			            }

			        }
			 }
			 //Validamos si el código promocional existe, en caso de que no exista regresará un false y saldrá del ciclo while
			 bandera = OfertaClienteDAO.validarExistenciaOfertaCliente(codigo);
		}
			 
	    System.out.println(codigo);
		return(codigo);
	}
	
	/**
	 * Método que desde la capa de lógica de negocio se encarga de retornar si una oferta existe y en caso positivo devuelve
	 * la información de la oferta
	 * @param codigoPromocional
	 * @return
	 */
	public String retornarOfertaCodigoPromocional(String codigoPromocional)
	{
		//Preparamos la respuesta del JSON
		JSONObject ofertaJSON = new JSONObject();;
		OfertaCliente ofertaCliente = OfertaClienteDAO.retornarOfertaCodigoPromocional(codigoPromocional);
		//Variable en la que marcaremos si la oferta está vigente
		boolean vigente = false;
		if(ofertaCliente.getIdOfertaCliente() == 0)
		{
			ofertaJSON.put("respuesta", "NOK");
		}
		else
		{
			//Es necesario validar si la oferta esta vigente o no tiene fecha de caducidad o si esta caducada
			if(ofertaCliente.getFechaCaducidad().equals(null) || ofertaCliente.getFechaCaducidad().equals(new String("")))
			{
				vigente = true;
			}else
			{
				//Es porque hay fecha de caducidad entonces tendremos que transformarla y validarla
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//Traemos la fecha Actual
				Date fechaActual = new Date();
				Date fechaCaducidad = new Date();
				try
				{
					fechaCaducidad = dateFormat.parse(ofertaCliente.getFechaCaducidad());
				}catch(Exception e)
				{
					
				}
				if(fechaActual.compareTo(fechaCaducidad) > 0)
				{
					vigente = false;
				}else
				{
					vigente = true;
				}
			}
			if(vigente)
			{
				ofertaJSON.put("respuesta", "OK");
			}else
			{
				ofertaJSON.put("respuesta", "VEN");
			}
			ofertaJSON.put("idoferta", ofertaCliente.getIdOferta());
			ofertaJSON.put("idofertacliente", ofertaCliente.getIdOfertaCliente());
			ofertaJSON.put("idcliente", ofertaCliente.getIdCliente());
			ofertaJSON.put("utilizado", ofertaCliente.getUtilizada());
			ofertaJSON.put("ingresooferta", ofertaCliente.getIngresoOferta());
			ofertaJSON.put("usooferta", ofertaCliente.getUsoOferta());
			ofertaJSON.put("observacion", ofertaCliente.getObservacion());
			ofertaJSON.put("pqrs", ofertaCliente.getPQRS());
			ofertaJSON.put("codigopromocional", ofertaCliente.getCodigoPromocion());
			ofertaJSON.put("fechacaducidad", ofertaCliente.getFechaCaducidad());
		}
		return(ofertaJSON.toJSONString());
	}
	
	public String enviarMensajesOferta(int idOferta)
	{
		//Con el siguiente método obtenemos las ofertas que tienen que enviar mensaje de texto y no lo han enviado
		//todavía, por lo tanto se creará un arreglo para enviarlo.
		ArrayList<MensajeTexto> mensajesTexto = OfertaClienteDAO.obtenerMensajesTextoEnviar(idOferta);
		//Posteriormente se realizará un procesamiento del arreglo para el envío del mensaje de texto
		String telTemp = "";
		String telCelTemp = "";
		//Tendremos una variable que nos indicará que si se puede enviar mensaje de texto
		boolean enviarMensaje = false;
		//Varialbles Definitivas para el envío del mensaje
		//Definimos variable con el telefono sobre el que enviaremos mensaje.
		String telEnviarMensaje = "";
		String mensaje1 = "";
		String mensaje2 = "";
		//Variable donde se almacenará el resultado del envío del mensaje
		String resultado = "";
		for(MensajeTexto mensaje : mensajesTexto)
		{
			telTemp = mensaje.getTelefono();
			if(telTemp == null)
			{
				telTemp = " ";
			}
			telCelTemp = mensaje.getNumeroCelular();
			if(telCelTemp == null)
			{
				telCelTemp = " ";
			}
			//Verificamos si el número celular esta bien en cuanto a que el número
			if ((telCelTemp.substring(0, 1).equals(new String("3"))) && (telCelTemp.length() == 10))
			{
				enviarMensaje = true;
				telEnviarMensaje = telCelTemp;
			}//Sino se cumplen estas condiciones se evalua el telefono principal
			else if ((telTemp.substring(0, 1).equals(new String("3"))) && (telTemp.length() == 10))
			{
				enviarMensaje = true;
				telEnviarMensaje = telTemp;
			}//Sino se cumplio ninguna de las condiciones no se enviará mensaje
			if(enviarMensaje)
			{
				// Retornamos el mensaje para hacerle tratamiento mensaje 1
				mensaje1 = mensaje.getMensaje1();
				//Validamos el mensaje 1 que no sea nulo y que tenga longitud
				if((!mensaje1.equals(null)) && (mensaje1.trim().length() > 0))
				{
					//Buscamos dentro del caracter los comodines para reemplazarlos
					//#NOMBRECLIENTE  #APELLIDOCLIENTE #CODIGODESCUENTO
					mensaje1 = mensaje1.replace("#NOMBRECLIENTE", mensaje.getNombreCliente());
					mensaje1 = mensaje1.replace("#APELLIDOCLIENTE", mensaje.getApellidoCliente());
					mensaje1 = mensaje1.replace("#CODIGODESCUENTO", mensaje.getCodigoPromocion());
					//Validamos la longitud del mensaje y si cumple lo enviaremos
					if(mensaje1.length() <= 160)
					{
						//Realizaríamos el llamado al programa PHP
						resultado = ejecutarPHPEnvioMensaje( "57"+ telEnviarMensaje, mensaje1);
					}
				}
				
				// Retornamos el mensaje para hacerle tratamiento mensaje 2
				mensaje2 = mensaje.getMensaje2();
				//Validamos el mensaje 1 que no sea nulo y que tenga longitud
				if((!mensaje2.equals(null)) && (mensaje2.trim().length() > 0))
				{
					//Buscamos dentro del caracter los comodines para reemplazarlos
					//#NOMBRECLIENTE  #APELLIDOCLIENTE #CODIGODESCUENTO
					mensaje2 = mensaje2.replace("#NOMBRECLIENTE", mensaje.getNombreCliente());
					mensaje2 = mensaje2.replace("#APELLIDOCLIENTE", mensaje.getApellidoCliente());
					mensaje2 = mensaje2.replace("#CODIGODESCUENTO", mensaje.getCodigoPromocion());
					//Validamos la longitud del mensaje y si cumple lo enviaremos
					if(mensaje1.length() <= 160)
					{
						//Realizaríamos el llamado al programa PHP
						resultado =  ejecutarPHPEnvioMensaje( "57"+ telEnviarMensaje, mensaje1);
					}
				}
				//Validamos el resultado para dar por enviado el mensaje, cuando el mensaje es enviado debe tener un String con true
				if(resultado.contains("true"))
				{
					//Realizamos la marcación de que ya se realizo el envío del mensaje
					OfertaClienteDAO.actualizarMensajeOferta(mensaje.getIdOfertaCliente());
				}
				
			}
		}
		//Si todo logro llegar  a este punto sin error, generaremos una respuesta de exitoso en un JSON
		JSONObject respuestaJSON = new JSONObject();
		respuestaJSON.put("respuesta", "exitoso");
		return(respuestaJSON.toJSONString());
	}
	
	public String ejecutarPHPEnvioMensaje( String telefono, String mensaje) {
		StringBuilder output = new StringBuilder();
		try {
	      String line;
	      //Variable con ruta del script
	      String rutaPHPEnviarMensaje = ParametrosDAO.retornarValorAlfanumerico("RUTAPHPENVIARMENSAJE");
	      
	      Process p = Runtime.getRuntime().exec("php " + rutaPHPEnviarMensaje + " " + telefono + " \"" + mensaje + "\"");
	      BufferedReader input =
	        new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      while ((line = input.readLine()) != null) {
	          output.append(line);
	      }
	      input.close();
	    }
	    catch (Exception err) {
	      err.printStackTrace();
	    }
	    return output.toString();
	  }
	
}
