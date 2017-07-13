package capaModelo;

public class ExcepcionPrecio {
	private int idExcepcion;
	private int idProducto;
	private String nombreProducto;
	private double precio;
	private String descripcion;
	private String incluyeliquido;
	private int idtipoliquido;
	private String nombreLiquido;
	
	
	public ExcepcionPrecio(int idProducto, double precio, String descripcion, String incluyeliquido,
			int idtipoliquido) {
		super();
		this.idProducto = idProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.incluyeliquido = incluyeliquido;
		this.idtipoliquido = idtipoliquido;
	}
	
	
	
	
	public ExcepcionPrecio(int idExcepcion, int idProducto, double precio, String descripcion, String incluyeliquido,
			int idtipoliquido) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.incluyeliquido = incluyeliquido;
		this.idtipoliquido = idtipoliquido;
	}


	
	
	public ExcepcionPrecio(int idExcepcion, int idProducto, String nombreProducto, double precio, String descripcion,
			String incluyeliquido, int idtipoliquido, String nombreLiquido) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.incluyeliquido = incluyeliquido;
		this.idtipoliquido = idtipoliquido;
		this.nombreLiquido = nombreLiquido;
	}




	public String getNombreProducto() {
		return nombreProducto;
	}




	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}




	public String getNombreLiquido() {
		return nombreLiquido;
	}




	public void setNombreLiquido(String nombreLiquido) {
		this.nombreLiquido = nombreLiquido;
	}




	public String getIncluyeliquido() {
		return incluyeliquido;
	}
	public void setIncluyeliquido(String incluyeliquido) {
		this.incluyeliquido = incluyeliquido;
	}
	public int getIdtipoliquido() {
		return idtipoliquido;
	}
	public void setIdtipoliquido(int idtipoliquido) {
		this.idtipoliquido = idtipoliquido;
	}
	public int getIdExcepcion() {
		return idExcepcion;
	}
	public void setIdExcepcion(int idExcepcion) {
		this.idExcepcion = idExcepcion;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ExcepcionPrecio(int idExcepcion, int idProducto, double precio, String descripcion) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.precio = precio;
		this.descripcion = descripcion;
	}
	
	
	
	

}
