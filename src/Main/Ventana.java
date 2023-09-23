package Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Ventana {
	private final int ANCHO = 1000;
	private final int ALTO = 500;
	private JTable tabla;
	North north = new North();
	public Ventana() {
		actualizarTabla();
		South s = new South(this, north, tabla);
		JFrame frame = new JFrame("Gestor");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(s, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(tabla);

		frame.add(scroll, BorderLayout.CENTER);

		frame.setSize(ANCHO, ALTO);


		frame.add(north, BorderLayout.NORTH);



		// Esto se deja al final
		frame.setVisible(true);
	}

	public void actualizarTabla() {
		String[] columnas = {"ID", "Nombre", "Apellidos"};

		Conexion conn = new Conexion();

		ArrayList<String> datos = conn.recuperarDatos();

		String[][] matriz = new String[datos.size() / 3][3];

		for (int i = 0; i < datos.size(); i++) {
			matriz[i / 3][i % 3] = datos.get(i);
		}

		if (tabla == null) {
			tabla = new JTable(matriz, columnas);
		} else {
			tabla.setModel(new DefaultTableModel(matriz, columnas));
		}
		conn.close();
	}
}
