package capaModelo;

/**
 * Clase de la capa modelo que define los atributos de la clase SolicitudPQRS
 * @author JuanDavid
 *
 */
public class SolicitudPQRS {
	
	private String fechaSolicitud;
	private String tipoSolicitud;
	private int idcliente;
	private int idtienda;
	private String comentario;
	private String direccion;
	private String observacion;
	private String nombres;
	private String apellidos;
	
	
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public int getIdtienda() {
		return idtienda;
	}
	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public SolicitudPQRS(String fechaSolicitud, String tipoSolicitud, int idcliente, int idtienda, String comentario,
			String direccion, String observacion, String nombres, String apellidos) {
		super();
		this.fechaSolicitud = fechaSolicitud;
		this.tipoSolicitud = tipoSolicitud;
		this.idcliente = idcliente;
		this.idtienda = idtienda;
		this.comentario = comentario;
		this.direccion = direccion;
		this.observacion = observacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
	}

	
	

	

}
