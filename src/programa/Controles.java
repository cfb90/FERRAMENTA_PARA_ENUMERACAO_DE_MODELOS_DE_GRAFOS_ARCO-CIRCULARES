package programa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controles extends JFrame {
	JLabel[] lblPonto;
	private JPanel contentPane;
	private JButton[] colorButton;
	JCheckBox chckbxNewCheckBox;
	JLabel lblNewLabel;
	Color colors[];
	int i=0;
	DesenharModelo panel;
	private JFrame frame;
	private JFrame frame2;
	DesenharGrafo panel_2;
	ArrayList<ArrayList> permutacoesNormais;
	ArrayList<ArrayList> permutacoesNaoNormais;
	ArrayList<ArrayList> permutacoesAtual; 
	HashMap<ArrayList<Integer>, Integer[]> complementos;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * Create the frame.
	 * @param permutacoes 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public Controles(int numPontos,HashSet<Aresta> arestas, final ArrayList<ArrayList> permutacoes) throws NumberFormatException, IOException {
		setTitle("Controle dos Modelos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 100, 319, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		permutacoesNormais = new ArrayList<ArrayList>(permutacoes.size());
		permutacoesNaoNormais = new ArrayList<ArrayList>(permutacoes.size());
		permutacoesAtual = new ArrayList<ArrayList>(permutacoes);
		complementos = new HashMap<ArrayList<Integer>, Integer[]>(permutacoes.size());

		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 435, 250, 23);
		if (permutacoes.isEmpty()) lblNewLabel_1.setText("O Grafo n\u00E3o é Arco-Circular");
		else if(!testarNormalidadeGrafo(permutacoes)) lblNewLabel_1.setText("O Grafo é Arco-Circular N\u00E3o-Normal");
		else lblNewLabel_1.setText("O Grafo é Arco-Circular Normal");
		contentPane.add(lblNewLabel_1);
		
		if(permutacoesNaoNormais.isEmpty()){
			permutacoesNaoNormais.add(new ArrayList<Integer>());
		}
		if(permutacoesNormais.isEmpty()){
			permutacoesNormais.add(new ArrayList<Integer>());
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 12, 158, 211);
		contentPane.add(scrollPane);

		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);


		
	//	if (!permutacoes.isEmpty()) {
			colorButton= new JButton[numPontos];
			lblPonto= new JLabel[numPontos];
			panel=new DesenharModelo(permutacoesAtual.get(0),arestas);
			panel.setBounds(0, 0, 387, 439);
			frame2= new MostrarModelo(panel);
			frame2.setVisible(true);
			colors = panel.getColors();

			for (int j = 0; j < numPontos; j++) {
				lblPonto[j] = new JLabel("V\u00E9rtice " + (j+1));
				lblPonto[j].setBounds(51, 11 + (25 * j), 70, 14);
				panel_1.add(lblPonto[j]);


				colorButton[j] = new JButton();
				colorButton[j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Color c=JColorChooser.showDialog(new JPanel(), "Escolha uma nova cor para o v\u00E9rtice", new Color(0, 0, 0));
						if(c!=null){
							JButton temp=(JButton)arg0.getSource();
							panel.setColors(c, (temp.getY()-11)/25);
							panel_2.setColors(c, (temp.getY()-11)/25);
							temp.setBackground(c);
							panel.repaint();
							panel_2.repaint();
							contentPane.revalidate();
						}
					}
				});
				colorButton[j].setBounds(10, 11 + (25 * j), 24, 14);
				colorButton[j].setBackground(colors[j]);
				panel_1.add(colorButton[j]);
			}
			Integer[] compl = complementos.get(permutacoesAtual.get(0));
			if(compl != null){
				lblPonto[compl[0]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
				lblPonto[compl[1]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
			}
			panel_1.setPreferredSize(new Dimension(lblPonto[0].getWidth()+colorButton[0].getWidth()+30, 25*(numPontos+1)));
		//}
		
		contentPane.setLayout(null);

		panel_2 = new DesenharGrafo(numPontos,arestas);
		//panel_2.setCoordenadas(panel.getCoordenadas());
		frame= new MostrarGrafo(panel_2);
		frame.setVisible(true);

		lblNewLabel = new JLabel("Modelo "+(i+1)+" de "+ permutacoes.size());
		lblNewLabel.setBounds(60, 327, 143, 14);
		contentPane.add(lblNewLabel);

		JButton btnAnterior = new JButton("");
		btnAnterior.setIcon(new ImageIcon(Controles.class.getResource("/programa/setas_2551_Fleche_Gauche3.gif")));
		/*btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(i>0){
					i--;
					panel.setPermutacoes(permutacoesAtual.get(i));
				}
				lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
				panel.repaint();
				contentPane.revalidate();
			}
		});*/
		int timerDelay = 100;
		final Timer timer2 = new Timer(timerDelay , new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(i>0){
					i--;
					panel.setPermutacoes(permutacoesAtual.get(i));
				}
				lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
				resetarLabels();
				Integer[] compl = complementos.get(permutacoesAtual.get(i));
				if(compl!=null){
					lblPonto[compl[0]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
					lblPonto[compl[1]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
				}
				panel.repaint();
				//panel.setCoordenadas();
				//panel_2.setCoordenadas(panel.getCoordenadas());
				//panel_2.repaint();
				contentPane.revalidate();
			}
		});
		timer2.setInitialDelay(0);
		final ButtonModel bModel2 = btnAnterior.getModel();
		bModel2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent cEvt) {
				if (bModel2.isPressed() && !timer2.isRunning()) {
					timer2.start();
				} else if (!bModel2.isPressed() && timer2.isRunning()) {
					timer2.stop();
				}
			}
		});
		btnAnterior.setBounds(12, 319, 30, 30);
		contentPane.add(btnAnterior);


		chckbxNewCheckBox = new JCheckBox("Marcar os Arcos");
		chckbxNewCheckBox.setBounds(170, 237, 126, 23);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setPie(chckbxNewCheckBox.isSelected());
				panel.repaint();
				//panel.setCoordenadas();
				//panel_2.setCoordenadas(panel.getCoordenadas());
				//panel_2.repaint();
				contentPane.revalidate();
			}
		});
		contentPane.add(chckbxNewCheckBox);

		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Controles.class.getResource("/programa/setas_2551_Fleche_Gauche2.gif")));
		/*btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(i<permutacoesAtual.size()-1){
					i++;
					panel.setPermutacoes(permutacoesAtual.get(i));
				}
				lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
				panel.repaint();
				contentPane.revalidate();
			}
		});*/
		final Timer timer = new Timer(timerDelay , new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(i<permutacoesAtual.size()-1){
					i++;
					panel.setPermutacoes(permutacoesAtual.get(i));
				}
				lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
				//System.out.println(permutacoesAtual.get(i));
				resetarLabels();
				Integer[] compl = complementos.get(permutacoesAtual.get(i));
				if(compl!=null){
					lblPonto[compl[0]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
					lblPonto[compl[1]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
				}
				panel.repaint();
				//panel.setCoordenadas();
				//panel_2.setCoordenadas(panel.getCoordenadas());
				//panel_2.repaint();
				contentPane.revalidate();
			}
		});
		timer.setInitialDelay(0);
		final ButtonModel bModel = btnNewButton.getModel();
		bModel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent cEvt) {
				if (bModel.isPressed() && !timer.isRunning()) {
					timer.start();
				} else if (!bModel.isPressed() && timer.isRunning()) {
					timer.stop();
				}
			}
		});
		btnNewButton.setBounds(211, 319, 30, 30);
		contentPane.add(btnNewButton);

		JLabel label = new JLabel("Arestas");
		label.setBounds(188, 12, 64, 14);
		contentPane.add(label);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(182, 38, 54, 162);
		contentPane.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);

		List<Aresta> tmp = new ArrayList<Aresta>(arestas);
		Collections.sort(tmp, new Comparator<Aresta>() {

			@Override
			public int compare(Aresta arg0, Aresta arg1) {
				// TODO Auto-generated method stub
				
				Integer i=arg0.vertice1;
				Integer i2=arg1.vertice1; 
				return i.compareTo(i2);
			}
		});
		Iterator<Aresta> t = tmp.iterator();
		if(t.hasNext())	textArea.setText(t.next()+"");
		while(t.hasNext()){
			textArea.setText(textArea.getText()+"\n"+t.next());
		}
		final JButton btnCorInicial = new JButton("Cor Inicial");
		btnCorInicial.setBackground(colors[0]);
		btnCorInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color c=JColorChooser.showDialog(new JPanel(), "Escolha uma nova cor para o v\u00E9rtice", new Color(0, 0, 0));
				if(c!=null){
					JButton temp=(JButton)arg0.getSource();
					temp.setBackground(c);
					contentPane.revalidate();
				}
			}
		});
		btnCorInicial.setBounds(12, 369, 98, 26);
		contentPane.add(btnCorInicial);

		final JButton btnCorFinal = new JButton("Cor Final");
		btnCorFinal.setBackground(colors[numPontos-1]);
		btnCorFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color c=JColorChooser.showDialog(new JPanel(), "Escolha uma nova cor para o v\u00E9rtice", new Color(0, 0, 0));
				if(c!=null){
					JButton temp=(JButton)arg0.getSource();
					temp.setBackground(c);
					contentPane.revalidate();
				}
			}
		});
		btnCorFinal.setBounds(122, 369, 98, 26);
		contentPane.add(btnCorFinal);

		JButton btnMudarCores = new JButton("Mudar Cores");
		btnMudarCores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setColors(btnCorInicial.getBackground(), btnCorFinal.getBackground());
				panel_2.setColors(btnCorInicial.getBackground(), btnCorFinal.getBackground());
				colors=panel.getColors();
				for (int i = 0; i < colorButton.length; i++) {
					colorButton[i].setBackground(colors[i]);
				}
				panel.repaint();
				panel_2.repaint();
				contentPane.revalidate();
			}
		});
		btnMudarCores.setBounds(68, 406, 113, 26);
		contentPane.add(btnMudarCores);

		JRadioButton rdbtnModelosNormais = new JRadioButton("Modelos Normais");
		buttonGroup.add(rdbtnModelosNormais);
		rdbtnModelosNormais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton t = (JRadioButton) arg0.getSource();
				if(t.isSelected()){
					resetarLabels();
					permutacoesAtual.clear();
					permutacoesAtual.addAll(permutacoesNormais);
					i=0;
					lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
					panel.setPermutacoes(permutacoesAtual.get(i));
					panel.repaint();
					//panel.setCoordenadas();
					//panel_2.setCoordenadas(panel.getCoordenadas());
					//panel_2.repaint();
					contentPane.revalidate();
				}
			}
		});
		rdbtnModelosNormais.setBounds(12, 236, 143, 24);
		contentPane.add(rdbtnModelosNormais);

		JRadioButton rdbtnModelosNonormais = new JRadioButton("Modelos N\u00E3o-Normais");
		buttonGroup.add(rdbtnModelosNonormais);
		rdbtnModelosNonormais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton t = (JRadioButton) arg0.getSource();
				if(t.isSelected()){
					resetarLabels();
					permutacoesAtual.clear();
					permutacoesAtual.addAll(permutacoesNaoNormais);
					i=0;
					lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
					Integer[] compl = complementos.get(permutacoesAtual.get(0));
					if(compl != null){
						lblPonto[compl[0]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
						lblPonto[compl[1]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
					}
					panel.setPermutacoes(permutacoesAtual.get(i));
					panel.repaint();
					//panel.setCoordenadas();
					//panel_2.setCoordenadas(panel.getCoordenadas());
					//panel_2.repaint();
					contentPane.revalidate();
				}
			}
		});
		rdbtnModelosNonormais.setBounds(12, 264, 150, 24);
		contentPane.add(rdbtnModelosNonormais);

		JRadioButton rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton t = (JRadioButton) arg0.getSource();
				if(t.isSelected()){
					resetarLabels();
					permutacoesAtual.clear();
					permutacoesAtual.addAll(permutacoes);
					i=0;
					lblNewLabel.setText("Modelo "+(i+1)+" de "+ permutacoesAtual.size());
					Integer[] compl = complementos.get(permutacoesAtual.get(0));
					if(compl != null){
						lblPonto[compl[0]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
						lblPonto[compl[1]-1].setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
					}
					panel.setPermutacoes(permutacoesAtual.get(i));
					panel.repaint();
					//panel.setCoordenadas();
					//panel_2.setCoordenadas(panel.getCoordenadas());
					//panel_2.repaint();
					contentPane.revalidate();
				}
			}
		});
		rdbtnTodos.setSelected(true);
		buttonGroup.add(rdbtnTodos);
		rdbtnTodos.setBounds(12, 287, 121, 24);
		contentPane.add(rdbtnTodos);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Principal novo = new Principal();
				novo.main(null);
				frame.dispose();
				frame2.dispose();
				dispose();
				
			}
		});
		btnVoltar.setBounds(170, 281, 98, 26);
		contentPane.add(btnVoltar);

	}



	public boolean testarNormalidadeGrafo(ArrayList<ArrayList> permutacoes){
		boolean teste = false;
		for(int i=0;i<permutacoes.size();i++){
			ArrayList<Integer> perTemp= permutacoes.get(i);
			if(testarNormalidadePermutacao(perTemp)){ 
				teste=true;
			}

		}
		return teste;
	}
	public boolean testarNormalidadePermutacao(ArrayList<Integer> permutacao){
		boolean normalidade=true;
		for (int j = 1; j <= permutacao.size()/2; j++) {
			for (int k = j+1; k <= permutacao.size()/2 ; k++) {
					normalidade=testarNormalidadePermutacao2(j,k,permutacao);
					if(!normalidade) {
						permutacoesNaoNormais.add(permutacao);
						Integer[] t= new Integer[2];
						t[0]=j;
						t[1]=k;
						complementos.put(permutacao, t);
						return normalidade;
				}
			}
		}
		permutacoesNormais.add(permutacao);
		return normalidade;

	}

	private void resetarLabels(){
		for (int i = 0; i < lblPonto.length; i++) {
			lblPonto[i].setFont(new Font("Dialog", Font.BOLD, 12));
		}
	}

	private boolean testarNormalidadePermutacao2(int candidato1, int candidato2,ArrayList<Integer> permutacao) {
		for (int i = 0; i < permutacao.size(); i++) {
			if(permutacao.get(i)==candidato1){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == -candidato2) {
					   	for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == candidato2){
								return false;
							}
							else if(permutacao.get(j2)== -candidato1)
								return true;
						}
						
					}
					else if(permutacao.get(j)== -candidato1 || permutacao.get(j)== candidato2) 
						return true;
				}
			}
			else if(permutacao.get(i)==-candidato1){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == candidato1) {
					   	for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == -candidato2){
								return false;
							}
							else if(permutacao.get(j2)== candidato2)
								return true;
						}
						
					}
					else if(permutacao.get(j)== -candidato2 || permutacao.get(j)== candidato2)
						return true;
				}
			}
			else if(permutacao.get(i)==candidato2){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == -candidato1) {
						for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == candidato1){
								return false;
							}
							else if(permutacao.get(j2)== -candidato2)
								return true;
						}
						
					}
					else if(permutacao.get(j)== -candidato2 || permutacao.get(j)== candidato1)
						return true;
				}
			}
			else if(permutacao.get(i)==-candidato2){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == candidato2) {
					   	for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == -candidato1){
								return false;
							}
							else if(permutacao.get(j2)== candidato1)
								return true;
						}
						
					}
					else if(permutacao.get(j)== -candidato1 || permutacao.get(j)== candidato1)
						return true;
				}
			}
		}
		return true;
	}
}