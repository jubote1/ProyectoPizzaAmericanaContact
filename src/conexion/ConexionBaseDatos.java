package conexion;
import java.sql.*;
public class ConexionBaseDatos {
	
	public static void main(String args[]){
		
		ConexionBaseDatos cn = new ConexionBaseDatos();
		cn.obtenerConexionBDTienda("PixelSqlbase");
	}

	public Connection obtenerConexionBDPrincipal(){
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    

		} catch (Exception e) {

		    System.out.println(e.toString());

		}
		
		Connection con = null;
		//...

		try {

			//con = DriverManager.getConnection(
		    //        "jdbc:mysql://localhost/pizzaamericana?"
		    //        + "user=root&password=naillive");
			
			con = DriverManager.getConnection(
		            "jdbc:mysql://localhost/pizzaamericana?"
		            + "user=root&password=4m32017");

		    // Otros y operaciones sobre la base de datos...

		} catch (SQLException ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con);
	}
	
	public Connection obtenerConexionBDTienda(String dsn){
		
		Connection con = null;
		try {

			 //Class.forName("sybase.jdbc.sqlanywhere.IDriver");
			 //con = DriverManager.getConnection("jdbc:sqlanywhere:dsn="+dsn+";uid=admin;pwd=xxx");//SystemPos
			
			//Cambiamos para la versión 12 del driver en teoria no es necesario registrar el driver lo comentamos
			System.out.println("pase 1");
			DriverManager.registerDriver( (Driver)
					 Class.forName( "sybase.jdbc.sqlanywhere.IDriver" ).newInstance() );
			System.out.println("pase 2");
			con = DriverManager.getConnection("jdbc:sqlanywhere:dsn="+dsn+";uid=admin;pwd=xxx");//SystemPos
			//con = DriverManager.getConnection("jdbc:sqlanywhere:uid=admin;pwd=xxx;eng=PixelSqlbase;database=PixelSqlbase;links=tcpip(host=192.168.0.37;port=2638)");//SystemPos
			System.out.println("pase 3");
		} catch (Exception ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con); 
	}
	
}
