package programa;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MostrarModelo extends JFrame {

	private JPanel contentPane;

		/**
	 * Create the frame.
	 */
	public MostrarModelo(DesenharModelo des) {
			setTitle("Modelo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 400,450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DesenharModelo panel = des;
		panel.setBounds(0, 0, 500,500);
		contentPane.add(panel);
	}

}
