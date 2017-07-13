package capaControlador;

import capaModelo.Tienda;
import capaDAO.TiendaDAO;

import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.*;

public class TiendaCtrl {
	
	public String obtenerTiendas(){
		JSONArray listJSON = new JSONArray();
		ArrayList<Tienda> tiendas = TiendaDAO.obtenerTiendas();
		
		for (Tienda tienda : tiendas) {
			JSONObject cadaViajeJSON = new JSONObject();
			cadaViajeJSON.put("id", tienda.getIdTienda());
			cadaViajeJSON.put("nombre", tienda.getNombreTienda());
			cadaViajeJSON.put("destino", tienda.getDsnTienda());
			listJSON.add(cadaViajeJSON);
		}
		return listJSON.toJSONString();
	}

}
