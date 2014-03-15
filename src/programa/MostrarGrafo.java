package programa;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Point;

public class MostrarGrafo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MostrarGrafo(DesenharGrafo des) {
		setTitle("Grafo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(900, 100, 350, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DesenharGrafo panel = des;
		panel.setBounds(10, 11, 500, 500);
		contentPane.add(panel);
	}
}
