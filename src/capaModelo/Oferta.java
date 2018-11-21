package capaModelo;

public class Oferta {
private int idOferta;
private String nombreOferta;
private int idExcepcion;
private String nombreExcepcion;



public String getNombreExcepcion() {
	return nombreExcepcion;
}
public void setNombreExcepcion(String nombreExcepcion) {
	this.nombreExcepcion = nombreExcepcion;
}
public int getIdOferta() {
	return idOferta;
}
public void setIdOferta(int idOferta) {
	this.idOferta = idOferta;
}
public String getNombreOferta() {
	return nombreOferta;
}
public void setNombreOferta(String nombreOferta) {
	this.nombreOferta = nombreOferta;
}
public int getIdExcepcion() {
	return idExcepcion;
}
public void setIdExcepcion(int idExcepcion) {
	this.idExcepcion = idExcepcion;
}
public Oferta(int idOferta, String nombreOferta, int idExcepcion) {
	super();
	this.idOferta = idOferta;
	this.nombreOferta = nombreOferta;
	this.idExcepcion = idExcepcion;
}



	
}
