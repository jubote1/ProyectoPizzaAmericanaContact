package capaModelo;

public class FormaPago {

	private int idformapago;
	private String nombre;
	private String tipoforma;
	public int getIdformapago() {
		return idformapago;
	}
	public void setIdformapago(int idformapago) {
		this.idformapago = idformapago;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoforma() {
		return tipoforma;
	}
	public void setTipoforma(String tipoforma) {
		this.tipoforma = tipoforma;
	}
	public FormaPago(int idformapago, String nombre, String tipoforma) {
		super();
		this.idformapago = idformapago;
		this.nombre = nombre;
		this.tipoforma = tipoforma;
	}
	public FormaPago(String nombre, String tipoforma) {
		super();
		this.nombre = nombre;
		this.tipoforma = tipoforma;
	}
	
	
	
	
	
}
