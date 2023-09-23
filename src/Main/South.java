package Main;

import javax.swing.*;
import java.awt.*;

public class South extends JPanel {
	JButton btnCargar = new JButton("Cargar datos");
	JButton btnBorrar = new JButton("Borrar registro");
	JButton btnEditar = new JButton("Actualizar registro");
	JButton btnBorrarTodo = new JButton("BORRAR TODO");

	public South(Ventana v, North north, JTable tabla) {
		setLayout(new FlowLayout());

		add(btnCargar);
		add(btnBorrar);
		add(btnEditar);
		add(btnBorrarTodo);

		btnCargar.addActionListener(e -> {

			String[] data = new String[2];
			Conexion conn = new Conexion();

			if(north.getNombre().isEmpty() || north.getApellidos().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Alguno de los campos está vacío",
						"Campos vacíos", JOptionPane.ERROR_MESSAGE);
			} else {
				data[0] = north.getNombre();
				data[1] = north.getApellidos();

				conn.insertarDatos(data);

				v.actualizarTabla();
				north.limpiarCampos();
				conn.close();
			}
		});

		btnBorrar.addActionListener(e -> {
			int row = tabla.getSelectedRow();

			if(row != -1) {
				Object valor = tabla.getValueAt(row, 0);
				Conexion conn = new Conexion();

				int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres borrar este registro?",
						"Confirmación", JOptionPane.YES_NO_OPTION);

				if(respuesta == JOptionPane.YES_NO_OPTION) {
					conn.borrarRegistro(Integer.parseInt(valor.toString()));
					north.setNombre("");
					north.setApellidos("");

					v.actualizarTabla();
				}

				conn.close();
			}
		});

		tabla.getSelectionModel().addListSelectionListener(e -> {
			int row = tabla.getSelectedRow();

			if(row != -1) {
				String nombre = tabla.getValueAt(row, 1).toString();
				String apellido = tabla.getValueAt(row, 2).toString();

				north.setNombre(nombre);
				north.setApellidos(apellido);
			}
		});

		btnEditar.addActionListener(e -> {
			Conexion conn = new Conexion();

			int row = tabla.getSelectedRow();
			int id = Integer.parseInt(tabla.getValueAt(row, 0).toString());

			conn.actualizarRegistro(id, north.getNombre(), north.getApellidos());

			v.actualizarTabla();
			north.setNombre("");
			north.setApellidos("");
			conn.close();
		});

		btnBorrarTodo.addActionListener(e -> {
			Conexion conn = new Conexion();

			String confirmacion = JOptionPane.showInputDialog(null,
					"¿Realmente deseas borrar todos los registros? Escribe BORRAR para confirmar la operación",
					"Confirmar borrado", JOptionPane.QUESTION_MESSAGE);

			try {
				if(confirmacion.equals("BORRAR")) {
					conn.borrarTodo();
					v.actualizarTabla();
				} else {
					JOptionPane.showMessageDialog(null, "El dato es incorrecto o inexistente",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}


			conn.close();
		});
	}
}
