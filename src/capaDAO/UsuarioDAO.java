package capaDAO;
import capaModelo.Usuario;
import conexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
public class UsuarioDAO {

	public static boolean validarUsuario(Usuario usuario)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select count(*) from usuario where nombre = '" + usuario.getNombreUsuario() + "' and password = '" + usuario.getContrasena()+"'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int cantidad=0;
				try{
					cantidad = Integer.parseInt(rs.getString(1));
					if (cantidad > 0){
						return(true);
					}
				}catch(Exception e){
					logger.error(e.toString());
					return(false);
				}
				con1.close();
			}
		}catch (Exception e){
			
		}
		return(false);
		
	}
	
	public static boolean validarAutenticacion(Usuario usuario)
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select count(*) from usuario where nombre = '" + usuario.getNombreUsuario() + "'";
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int cantidad=0;
				try{
					cantidad = Integer.parseInt(rs.getString(1));
					if (cantidad > 0){
						return(true);
					}
				}catch(Exception e){
					
					return(false);
				}
				con1.close();
			}
		}catch (Exception e){
			
		}
		return(false);
	}
	
}
