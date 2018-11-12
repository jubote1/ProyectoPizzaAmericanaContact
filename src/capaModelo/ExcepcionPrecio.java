package capaModelo;

/**
 * Clase que implementa la entidad Excepción Precio.
 * @author JuanDavid
 *
 */
public class ExcepcionPrecio {
	private int idExcepcion;
	private int idProducto;
	private String nombreProducto;
	private double precio;
	private String descripcion;
	private String incluyeliquido;
	private int idtipoliquido;
	private String nombreLiquido;
	private String controlaCantidadIngredientes;
	private int cantidadIngrediantes;
	private String partiradiciones;
	private String habilitado;
	
	
	
	public String getHabilitado() {
		return habilitado;
	}




	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}




	public String getPartiradiciones() {
		return partiradiciones;
	}




	public void setPartiradiciones(String partiradiciones) {
		this.partiradiciones = partiradiciones;
	}




	
	public ExcepcionPrecio(int idExcepcion, int idProducto, double precio, String descripcion, String incluyeliquido,
			int idtipoliquido, String habilitado) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.incluyeliquido = incluyeliquido;
		this.idtipoliquido = idtipoliquido;
		this.habilitado = habilitado;
	}


//Constructor elaborado principalmente para Grid donde se muetra la informacion de las excepciones precio
	public ExcepcionPrecio(int idExcepcion, int idProducto, String nombreProducto, double precio, String descripcion,
			String incluyeliquido, int idtipoliquido, String nombreLiquido, String habilitado) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.incluyeliquido = incluyeliquido;
		this.idtipoliquido = idtipoliquido;
		this.nombreLiquido = nombreLiquido;
		this.habilitado = habilitado;
	}




	public String getControlaCantidadIngredientes() {
		return controlaCantidadIngredientes;
	}




	public void setControlaCantidadIngredientes(String controlaCantidadIngredientes) {
		this.controlaCantidadIngredientes = controlaCantidadIngredientes;
	}




	public int getCantidadIngrediantes() {
		return cantidadIngrediantes;
	}




	public void setCantidadIngrediantes(int cantidadIngrediantes) {
		this.cantidadIngrediantes = cantidadIngrediantes;
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
	public ExcepcionPrecio(int idExcepcion, int idProducto, double precio, String descripcion, String controlaCantidadIngredientes, int cantidadIngredientes,String incluyeliquido,
			int idtipoliquido, String partiradiciones ) {
		super();
		this.idExcepcion = idExcepcion;
		this.idProducto = idProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.controlaCantidadIngredientes = controlaCantidadIngredientes;
		this.cantidadIngrediantes = cantidadIngredientes;
		this.partiradiciones = partiradiciones;
	}
	
	
	
	

}
