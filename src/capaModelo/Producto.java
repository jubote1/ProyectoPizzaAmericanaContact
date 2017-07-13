package capaModelo;

public class Producto {

	private int idProducto;
	private int idReceta;
	private String nombrereceta;
	private String nombre;
	private String descripcion;
	private float impuesto;
	private String tipo;
	private int productoasociaadicion;
	private double preciogeneral;
	private String incluye_liquido;
	private int idtipo_liquido;
	private String nombreliquido;
	
	
	
	
	public int getProductoasociaadicion() {
		return productoasociaadicion;
	}
	public void setProductoasociaadicion(int productoasociaadicion) {
		this.productoasociaadicion = productoasociaadicion;
	}
	public String getNombrereceta() {
		return nombrereceta;
	}
	public void setNombrereceta(String nombrereceta) {
		this.nombrereceta = nombrereceta;
	}
	public String getNombreliquido() {
		return nombreliquido;
	}
	public void setNombreliquido(String nombreliquido) {
		this.nombreliquido = nombreliquido;
	}
	public String getIncluye_liquido() {
		return incluye_liquido;
	}
	public void setIncluye_liquido(String incluye_liquido) {
		this.incluye_liquido = incluye_liquido;
	}
	public int getIdtipo_liquido() {
		return idtipo_liquido;
	}
	public void setIdtipo_liquido(int idtipo_liquido) {
		this.idtipo_liquido = idtipo_liquido;
	}
	public double getPreciogeneral() {
		return preciogeneral;
	}
	public void setPreciogeneral(double preciogeneral) {
		this.preciogeneral = preciogeneral;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdReceta() {
		return idReceta;
	}
	public void setIdReceta(int idReceta) {
		this.idReceta = idReceta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Producto(int idProducto, int idReceta, String nombre, String descripcion, float impuesto, String tipo, double preciogeneral) {
		super();
		this.idProducto = idProducto;
		this.idReceta = idReceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.impuesto = impuesto;
		this.tipo = tipo;
		this.preciogeneral = preciogeneral;
	}
	public Producto(int idProducto, int idReceta, String nombre, String descripcion, float impuesto, String tipo, int productoasociaadicion ,double preciogeneral, String incluye_liquido, int idtipo_liquido) {
		super();
		this.idProducto = idProducto;
		this.idReceta = idReceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.impuesto = impuesto;
		this.tipo = tipo;
		this.productoasociaadicion = productoasociaadicion;
		this.preciogeneral = preciogeneral;
		this.incluye_liquido = incluye_liquido;
		this.idtipo_liquido = idtipo_liquido;
	}
	
	public Producto(int idProducto, int idReceta, String nombrereceta, String nombre, String descripcion, float impuesto, String tipo,double preciogeneral, String incluye_liquido, int idtipo_liquido, String nombreliquido) {
		super();
		this.idProducto = idProducto;
		this.idReceta = idReceta;
		this.nombrereceta = nombrereceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.impuesto = impuesto;
		this.tipo = tipo;
		this.preciogeneral = preciogeneral;
		this.incluye_liquido = incluye_liquido;
		this.idtipo_liquido = idtipo_liquido;
		this.nombreliquido = nombreliquido;
	}
	
	
	
}
