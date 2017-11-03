package capaModelo;

/**
 * Clase que implementa la entidad Forma de Pago.
 * @author JuanDavid
 *
 */
public class FormaPago {

	
	private int idformapago;
	private String nombre;
	private String tipoforma;
	private double valortotal;
	private double valorformapago;
	
	
	
	
	public double getValortotal() {
		return valortotal;
	}
	public void setValortotal(double valortotal) {
		this.valortotal = valortotal;
	}
	public double getValorformapago() {
		return valorformapago;
	}
	public void setValorformapago(double valorformapago) {
		this.valorformapago = valorformapago;
	}
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
	
	public FormaPago(int idformapago, String nombre, String tipoforma, double valortotal, double valorformapago) {
		super();
		this.idformapago = idformapago;
		this.nombre = nombre;
		this.tipoforma = tipoforma;
		this.valortotal = valortotal;
		this.valorformapago = valorformapago;
	}
	public FormaPago() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
