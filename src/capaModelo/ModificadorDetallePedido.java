package capaModelo;

public class ModificadorDetallePedido {
	
	private int idmodificador;
	private int iddetallepedidopadre;
	private int idproductoespecialidad1;
	private int idproductoespecialidad2;
	private double cantidad;
	public int getIdmodificador() {
		return idmodificador;
	}
	public void setIdmodificador(int idmodificador) {
		this.idmodificador = idmodificador;
	}
	public int getIddetallepedidopadre() {
		return iddetallepedidopadre;
	}
	public void setIddetallepedidopadre(int iddetallepedidopadre) {
		this.iddetallepedidopadre = iddetallepedidopadre;
	}
	public int getIdproductoespecialidad1() {
		return idproductoespecialidad1;
	}
	public void setIdproductoespecialidad1(int idproductoespecialidad1) {
		this.idproductoespecialidad1 = idproductoespecialidad1;
	}
	public int getIdproductoespecialidad2() {
		return idproductoespecialidad2;
	}
	public void setIdproductoespecialidad2(int idproductoespecialidad2) {
		this.idproductoespecialidad2 = idproductoespecialidad2;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public ModificadorDetallePedido(int idmodificador, int iddetallepedidopadre, int idproductoespecialidad1,
			int idproductoespecialidad2, double cantidad) {
		super();
		this.idmodificador = idmodificador;
		this.iddetallepedidopadre = iddetallepedidopadre;
		this.idproductoespecialidad1 = idproductoespecialidad1;
		this.idproductoespecialidad2 = idproductoespecialidad2;
		this.cantidad = cantidad;
	}
	
	
	
	

}
