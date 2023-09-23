package Main;

import javax.swing.*;
import java.awt.*;

public class North extends JPanel{
	JTextField nombre;
	JTextField apellidos;
	public North() {
		JPanel panel = new JPanel();
		this.setLayout(new FlowLayout());

		nombre = new JTextField(20);
		apellidos = new JTextField(20);

		this.add(nombre);
		this.add(apellidos);
	}

	public void setNombre(String txt) {
		nombre.setText(txt);
	}

	public String getNombre() {
		return nombre.getText();
	}

	public void setApellidos(String txt) {
		apellidos.setText(txt);
	}

	public String getApellidos() {
		return apellidos.getText();
	}

	public void limpiarCampos() {
		nombre.setText("");
		apellidos.setText("");
	}
}
