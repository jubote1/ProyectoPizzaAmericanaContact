package capaControlador;

import capaModelo.CoordenadaPoligono;
import capaModelo.Poligono;
import capaModelo.Tienda;
import capaDAO.CoordenadaPoligonoDAO;
import capaDAO.PoligonoDAO;
import capaDAO.TiendaDAO;

import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.*;

public class GeolocalizacionCtrl {
	
	/*
	 * Método que retorna los poligonos definidos en un JSON
	 */
	public String obtenerPoligonos(){
		JSONArray listJSON = new JSONArray();
		ArrayList<Poligono> poligonos = PoligonoDAO.obtenerPoligonos();
		
		for (Poligono poligono : poligonos) {
			JSONObject cadaPoligonoJSON = new JSONObject();
			cadaPoligonoJSON.put("idpoligono", poligono.getIdPoligono());
			cadaPoligonoJSON.put("nombrepoligono", poligono.getNombrePoligo());
			cadaPoligonoJSON.put("ubicacionmapa", poligono.getUbicacionMapa());
			listJSON.add(cadaPoligonoJSON);
		}
		return listJSON.toJSONString();
	}
	
	
	public String obtenerCoordenadasPoligono(int idPoligono)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<CoordenadaPoligono> coorPoligonos =  CoordenadaPoligonoDAO.obtenerCoordenadasPoligono(idPoligono);
		for (CoordenadaPoligono coorPoligono : coorPoligonos) {
			JSONObject cadaCoorPoligonoJSON = new JSONObject();
			cadaCoorPoligonoJSON.put("lat", coorPoligono.getLatitud());
			cadaCoorPoligonoJSON.put("lng", coorPoligono.getLongitud());
			listJSON.add(cadaCoorPoligonoJSON);
		}
		return listJSON.toJSONString();
	}
	
}
