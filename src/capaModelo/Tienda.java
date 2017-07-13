package capaModelo;

public class Tienda {
	
	private int idTienda;
	public int getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(int idTienda) {
		this.idTienda = idTienda;
	}
	public String getNombreTienda() {
		return nombreTienda;
	}
	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}
	public String getDsnTienda() {
		return dsnTienda;
	}
	public void setDsnTienda(String dsnTienda) {
		this.dsnTienda = dsnTienda;
	}
	private String nombreTienda;
	private String dsnTienda;
	public Tienda(int idTienda, String nombreTienda, String dsnTienda) {
		super();
		this.idTienda = idTienda;
		this.nombreTienda = nombreTienda;
		this.dsnTienda = dsnTienda;
	}
	
	public Tienda()
	{
		
	}
	
	

}
