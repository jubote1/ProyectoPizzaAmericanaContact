package capaModelo;

public class MarcacionPedido {
	
	private int idPedido;
	private int idMarcacion;
	private String observacion;
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdMarcacion() {
		return idMarcacion;
	}
	public void setIdMarcacion(int idMarcacion) {
		this.idMarcacion = idMarcacion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public MarcacionPedido(int idPedido, int idMarcacion, String observacion) {
		super();
		this.idPedido = idPedido;
		this.idMarcacion = idMarcacion;
		this.observacion = observacion;
	}
	
	

}
