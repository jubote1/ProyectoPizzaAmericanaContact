package capaModelo;

public class Pedido {
	
	private int idpedido;
	private int idtienda;
	private String nombretienda;
	private double totalbruto;
	private double impuesto;
	private double total_neto;
	private int idestadopedido;
	private String estadopedido;
	private String fechapedido;
	private int idcliente;
	private String nombrecliente;
	
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public int getIdtienda() {
		return idtienda;
	}
	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}
	public String getNombretienda() {
		return nombretienda;
	}
	public void setNombretienda(String nombretienda) {
		this.nombretienda = nombretienda;
	}
	public double getTotalbruto() {
		return totalbruto;
	}
	public void setTotalbruto(double totalbruto) {
		this.totalbruto = totalbruto;
	}
	public double getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}
	public double getTotal_neto() {
		return total_neto;
	}
	public void setTotal_neto(double total_neto) {
		this.total_neto = total_neto;
	}
	public int getIdestadopedido() {
		return idestadopedido;
	}
	public void setIdestadopedido(int idestadopedido) {
		this.idestadopedido = idestadopedido;
	}
	public String getEstadopedido() {
		return estadopedido;
	}
	public void setEstadopedido(String estadopedido) {
		this.estadopedido = estadopedido;
	}
	public String getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}
	 
	
	
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public Pedido(int idpedido, String nombretienda, double totalbruto, double impuesto, double total_neto,
			String estadopedido, String fechapedido, String nombrecliente, int idcliente) {
		super();
		this.idpedido = idpedido;
		this.nombretienda = nombretienda;
		this.totalbruto = totalbruto;
		this.impuesto = impuesto;
		this.total_neto = total_neto;
		this.estadopedido = estadopedido;
		this.fechapedido = fechapedido;
		this.nombrecliente = nombrecliente;
		this.idcliente = idcliente;
	}
	
	
	

}
