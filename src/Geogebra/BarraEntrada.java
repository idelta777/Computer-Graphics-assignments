package geogebra;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BarraEntrada extends JPanel {
	public BarraEntrada() {
		JLabel entrada = new JLabel("Entrada");
		JTextField texto = new JTextField("", 20);
		
		this.add(entrada);
		this.add(texto);
	}
}
