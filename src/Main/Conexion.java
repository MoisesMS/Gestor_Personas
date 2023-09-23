package Main;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Conexion {
	Connection conn;
	public Conexion() {
		try {
			String url = "jdbc:mysql://localhost:3306/clientes";
			String user = "root";
			String pass = "root";

			conn = DriverManager.getConnection(url, user, pass);

		} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Error al conectarse",
						"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void insertarDatos(String[] data) {
		String sql = "INSERT INTO clientes (ID, Nombre, Apelidos) VALUES(?, ?, ?);";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, null);
			pstmt.setString(2, data[0]);
			pstmt.setString(3, data[1]);

			int filasAfectadas = pstmt.executeUpdate();

		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ArrayList<String> recuperarDatos() {
		String sql = "SELECT * FROM clientes;";
		ArrayList<String> datos = new ArrayList<>();
		int fila = 0;
		try(PreparedStatement declaracion = conn.prepareStatement(sql)) {
			try (ResultSet resultado = declaracion.executeQuery()) {
				while(resultado.next()) {
					datos.add(resultado.getString("ID"));
					datos.add(resultado.getString("Nombre"));
					datos.add(resultado.getString("Apelidos"));

					fila++;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener los datos",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		return datos;
	}

	public void borrarRegistro(int id) {
		String sql = "DELETE FROM clientes WHERE id = ?;";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al borrar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actualizarRegistro(int id, String nombre, String apellido) {
		String sql = "UPDATE clientes SET nombre = ?, apelidos = ? WHERE id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellido);
			pstmt.setInt(3, id);

			pstmt.executeUpdate();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "No se ha podido actualizar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void borrarTodo() {
		String sql = "DELETE FROM clientes";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "No se han podido borrar los registros",
					"Error al borrar", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cerrar la conexión",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
